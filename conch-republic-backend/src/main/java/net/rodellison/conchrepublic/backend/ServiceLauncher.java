package net.rodellison.conchrepublic.backend;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import io.vertx.core.*;
import io.vertx.core.json.JsonObject;
// Import log4j classes.
import net.rodellison.conchrepublic.backend.handlers.DataBaseVerticle;
import net.rodellison.conchrepublic.backend.handlers.EventHubVerticle;
import net.rodellison.conchrepublic.backend.handlers.WebCollectorVerticle;
import net.rodellison.conchrepublic.backend.handlers.WebFormatterVerticle;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.util.*;
import java.util.concurrent.*;

public class ServiceLauncher implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {

    private static final Logger logger = LogManager.getLogger(ServiceLauncher.class);
    public Vertx vertx;
    private static List<Boolean> verticleResult = new ArrayList<>();

    {
        VertxOptions vertxOptions = new VertxOptions()
                .setMaxEventLoopExecuteTime(20)
                .setMaxEventLoopExecuteTimeUnit(TimeUnit.SECONDS)
                .setWarningExceptionTime(20)
                .setWarningExceptionTimeUnit(TimeUnit.SECONDS)
                .setBlockedThreadCheckInterval(20)
                .setBlockedThreadCheckIntervalUnit(TimeUnit.SECONDS);
        vertx = Vertx.vertx(vertxOptions);

        // log the stack trace if an event loop or worker handler took more than 20s to execute
        vertxOptions.setWarningExceptionTime(20);
        vertxOptions.setWarningExceptionTimeUnit(TimeUnit.SECONDS);

        vertx = Vertx.vertx(vertxOptions);

        final int instanceCount = Runtime.getRuntime().availableProcessors();
        logger.info("Starting Service launcher and setting instances to: " + instanceCount);

        DeploymentOptions standardDeploymentOptions = new DeploymentOptions()
                .setInstances(instanceCount);
        DeploymentOptions workerDeploymentOptions = new DeploymentOptions()
                .setWorkerPoolSize(instanceCount)
                .setMaxWorkerExecuteTime(25)
                .setMaxWorkerExecuteTimeUnit(TimeUnit.SECONDS)
                .setInstances(instanceCount);

        CompletableFuture.allOf(

                deploy(WebCollectorVerticle.class.getName(), workerDeploymentOptions),
                deploy(DataBaseVerticle.class.getName(), workerDeploymentOptions),
                deploy(WebFormatterVerticle.class.getName(), workerDeploymentOptions),
                deploy(EventHubVerticle.class.getName(), standardDeploymentOptions)

        ).whenComplete((res, err) -> {
            logger.info("Deploy all verticles complete.");
            for (Boolean result: verticleResult)
            {
                if (result == false)
                {
                    logger.info("One or more verticles did NOT deploy successfully.");
                    //take action if necessary
                }
            }
        });
    }

    private CompletableFuture<Boolean> deploy(String name, DeploymentOptions opts) {
        CompletableFuture<Boolean> cf = new CompletableFuture<Boolean>();

        vertx.deployVerticle(name, opts, res -> {
            if (res.failed()) {
                logger.error("Failed to deploy verticle " + name);
                verticleResult.add(false);
                cf.complete(false);
            } else {
                logger.info("Deployed verticle " + name);
                verticleResult.add(true);
                cf.complete(true);
            }
        });
        return cf;
    }

    @Override
    public ApiGatewayResponse handleRequest(Map<String, Object> map, Context context) {

        final CompletableFuture<String> future = new CompletableFuture<String>();
        logger.info(map);
        vertx.eventBus().request(map.get("httpMethod").toString() + ":" + map.get("resource"), new JsonObject(map).encode(), rs -> {
            if (rs.succeeded()) {
                logger.info("ServiceLauncher::handleRequest: SUCCESS");
                logger.debug(rs.result().body());
                future.complete(rs.result().body().toString());
            } else {
                logger.info("ServiceLauncher::handleRequest: FAILED");
                logger.error(rs.cause().getMessage());
                future.complete(rs.cause().getMessage());
            }
        });
        try {
            Map<String, String> contentHeader = new HashMap<>();
            contentHeader.put("Content-type", "application/json");

            //different seconds value to account for different values - if calling loaddata, then it could take longer to process
            int seconds = 0;
            seconds = map.get("resource").toString().contains("loaddata") ? 20 : 10;

            return ApiGatewayResponse.builder()
                    .setRawBody(future.get(seconds, TimeUnit.SECONDS))
                    //        .setObjectBody(new Response("Sent by : " + this.toString() + " - " + future.get(seconds, TimeUnit.SECONDS)))
                    .setHeaders(contentHeader)
                    .build();
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
        }
        return ApiGatewayResponse.builder().
                setObjectBody(new Response("TIMEOUT"))
                .build();

    }
}