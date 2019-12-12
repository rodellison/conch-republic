package net.rodellison.conchrepublic.backend.handlers;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.rodellison.conchrepublic.backend.Services;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class EventHubVerticle extends AbstractVerticle {

    private static final Logger logger = LogManager.getLogger(EventHubVerticle.class);

    @Override
    public void start(Promise<Void> startPromise) {
        final EventBus eventBus = vertx.eventBus();

        eventBus.consumer("GET:/loaddata/{segment}", message -> {

            String theMessage = message.body().toString();
            JsonObject messageJson = new JsonObject(theMessage);

       //   logger.info("GET:/loaddata function invoked with body: " + theMessage);
            //This send gets the entire process kicked off
            //Calling out to an external web page to get data could take time, trying executeBlocking here
            vertx.<String>executeBlocking(execBlockFuture -> {
                final CompletableFuture<String> webCollectorFuture = new CompletableFuture<>();

                eventBus.request(Services.COLLECTWEBDATA.toString(), messageJson, rs -> {
                    if (rs.succeeded()) {
                        logger.info("COLLECTWEBDATA: SUCCESS");
                        webCollectorFuture.complete(rs.result().body().toString());
                    } else {
                        logger.info("COLLECTWEBDATA: FAILED");
                        webCollectorFuture.complete(rs.cause().getMessage());
                    }
                });

                String collectWebDataResult;
                try {
                    collectWebDataResult = webCollectorFuture.get();
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                    collectWebDataResult = e.getMessage();
                }

                execBlockFuture.complete(collectWebDataResult);

            }, resCollector -> {

                JsonObject collectorResult = new JsonObject(resCollector.result());

                //Parsing entirety of fetched data could take some time
                vertx.<String>executeBlocking(execBlockFuture -> {
                    final CompletableFuture<String> webFormatterFuture = new CompletableFuture<>();

                    eventBus.request(Services.FORMATWEBDATA.toString(), collectorResult, rs -> {
                        if (rs.succeeded()) {
                            logger.info("FORMATWEBDATA: SUCCESS");
                            webFormatterFuture.complete(rs.result().body().toString());
                        } else {
                            logger.info("FORMATWEBDATA: FAILED");
                            webFormatterFuture.complete(rs.cause().getMessage());
                        }
                    });

                    String DataExtractHandlerResult = "";
                    try {
                        DataExtractHandlerResult = webFormatterFuture.get();
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                        DataExtractHandlerResult = e.getMessage();
                    }

                    execBlockFuture.complete(DataExtractHandlerResult);

                }, resFormatter -> {

                     JsonObject formatterResult = new JsonObject(resFormatter.result());
                    //Inserting to Database could be time consuming
                    vertx.<String>executeBlocking(execBlockFuture -> {
                        final CompletableFuture<String> databaseFuture = new CompletableFuture<>();

                        eventBus.request(Services.INSERTDBDATA.toString(), formatterResult, rs -> {
                            if (rs.succeeded()) {
                                logger.info("DataBase: SUCCESS");
                                databaseFuture.complete(rs.result().body().toString());
                            } else {
                                logger.info("DataBase: FAILED");
                                databaseFuture.complete(rs.cause().getMessage());
                            }
                        });

                        String databaseResult = "";
                        try {
                            databaseResult = databaseFuture.get();
                        } catch (InterruptedException | ExecutionException e) {
                            e.printStackTrace();
                            databaseResult = e.getMessage();
                        }

                        execBlockFuture.complete(databaseResult);

                    }, resDB -> {

                    //    final Map<String, Object> response = new HashMap<>();
                        JsonObject replyObj = new JsonObject(resDB.result());
                        replyObj.put("statusCode", 200);
                        replyObj.put("body", "Processed GET:/loaddata for segment: " + replyObj.getValue("pathParameters"));
                        message.reply(replyObj.encode());
                    });
                });
            });
        });

        eventBus.consumer("GET:/getdata/{location}", message -> {

            String theMessage = message.body().toString();
            JsonObject messageJson = new JsonObject(theMessage);

            logger.info("GET:/getdata/{location} function invoked with body: " + messageJson);

            //Connecting to, and getting data could take time...
            vertx.<String>executeBlocking(execBlockFuture -> {
                final CompletableFuture<String> databaseFuture = new CompletableFuture<>();

                eventBus.request(Services.GETDBDATA.toString(), messageJson, rs -> {
                    if (rs.succeeded()) {
                        logger.info("DataBase: SUCCESS");
                        databaseFuture.complete(rs.result().body().toString());
                    } else {
                        logger.info("DataBase: FAILED");
                        databaseFuture.complete(rs.cause().getMessage());
                    }
                });

                String DBHandlerResult = "";
                try {
                    DBHandlerResult = databaseFuture.get();
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                    DBHandlerResult = e.getMessage();
                }

                execBlockFuture.complete(DBHandlerResult);

            }, res -> message.reply(new JsonObject(res.result())));
        });

        eventBus.consumer("GET:/cleanupdata", message -> {

            String theMessage = message.body().toString();

            logger.info("GET:/cleanupdata function invoked" );

            //Connecting to, and getting data could take time...
            vertx.<String>executeBlocking(execBlockFuture -> {
                final CompletableFuture<String> databaseFuture = new CompletableFuture<>();

                eventBus.request(Services.PURGEDBDATA.toString(), message.body(), rs -> {
                    if (rs.succeeded()) {
                        logger.info("DataBase: SUCCESS");
                        databaseFuture.complete(rs.result().body().toString());
                    } else {
                        logger.info("DataBase: FAILED");
                        databaseFuture.complete(rs.cause().getMessage());
                    }
                });

                String DBHandlerResult = "";
                try {
                    DBHandlerResult = databaseFuture.get();
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                    DBHandlerResult = e.getMessage();
                }

                execBlockFuture.complete(DBHandlerResult);

            }, res -> message.reply(new JsonObject(res.result())));
        });

        startPromise.complete();
    }



}

