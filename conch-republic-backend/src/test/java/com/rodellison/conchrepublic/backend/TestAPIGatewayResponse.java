package com.rodellison.conchrepublic.backend;


import io.vertx.core.json.JsonObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;

import java.util.Collections;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

// Import log4j classes.

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("API Gateway Response should")
public class TestAPIGatewayResponse {

    private final Logger logger = LogManager.getLogger(TestAPIGatewayResponse.class);

    @Test
    @DisplayName(" be able to send a raw body response")
    @Order(1)
    public void testAPIGateWayResponseRawBody() throws Throwable {

        Map<String, String> testHeaders = Collections.emptyMap();
        logger.info("Test testAPIGateWayResponseRawBody");

        ApiGatewayResponse testAPIGatewayResponse = ApiGatewayResponse.builder()
                .setHeaders(testHeaders)
                .setStatusCode(200)
                .setBase64Encoded(false)
                .setRawBody("test")
                .build();

        assertTrue(testAPIGatewayResponse.getBody().equals("test"));
        assertTrue(testAPIGatewayResponse.getStatusCode() == 200);
        assertTrue(testAPIGatewayResponse.getHeaders().isEmpty() == true);
        assertTrue(testAPIGatewayResponse.isIsBase64Encoded() == false);

    }

    @Test
    @DisplayName(" be able to send an object body response")
    @Order(2)
    public void testAPIGateWayObjectBody() throws Throwable {

        JsonObject testObjectBody = new JsonObject();
        testObjectBody.put("test1", "value1");

        logger.info("Test testAPIGateWayResponseObjectBody");

        ApiGatewayResponse testAPIGatewayResponse = ApiGatewayResponse.builder()
                .setObjectBody(testObjectBody)
                .build();

        assertTrue(testAPIGatewayResponse.getBody().contains("test1"));
    }


}
