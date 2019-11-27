package com.rodellison.conchrepublic.backend;

import com.amazonaws.services.lambda.runtime.Context;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Test EventHandler should ")
class TestEventHandler {

    private final Logger logger = LogManager.getLogger(TestEventHandler.class);
    private static ServiceLauncher sl = new ServiceLauncher();
    private static Context testContext = null;


    @Test
    @DisplayName(" ensure /loaddata call returns with success")
    public void testLoadDataReturnsSuccess() throws Throwable {

        Map<String, Object> map = new HashMap<>();
        map.put("httpMethod", "GET");
        map.put("resource", "/loaddata/{segment}");
        map.put("path", "/loaddata/1");
        map.put("pathParameters", "{\"segment\":\"1\"}");
        logger.info("Test EventHubVerticle responds for GET:/loaddata");

        //running two concurrent requests
        CompletableFuture<ApiGatewayResponse> cf1 = new CompletableFuture<>();
        CompletableFuture.runAsync(() -> {
            ApiGatewayResponse api1 = sl.handleRequest(map, testContext);
            cf1.complete(api1);
        });

        logger.info(cf1.get().getBody());
        assertTrue(cf1.get().getBody().contains("Processed GET:/loaddata"));

    }

    @Test
    @DisplayName(" ensure /getdata/{location} call returns with success")
    public void testGetDataReturnsSuccess() throws Throwable {

        Map<String, Object> map = new HashMap<>();
        map.put("httpMethod", "GET");
        map.put("resource", "/getdata/{location}");
        map.put("path", "/getdata/islamorada");
        map.put("pathParameters", "{\"location\":\"islamorada\"}");
        logger.info("Test EventHubVerticle responds for GET:/getdata");

        CompletableFuture<ApiGatewayResponse> cf1 = new CompletableFuture<>();
        CompletableFuture.runAsync(() -> {
            ApiGatewayResponse api1 = sl.handleRequest(map, testContext);
            cf1.complete(api1);
        });

        String result = cf1.get().getBody();
        assertTrue(result.contains("eventID"));

    }


}
