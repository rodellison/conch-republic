package net.rodellison.conchrepublic.backend.utils;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Dynamo DB Client should ")
class DynamoDBClientTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    @DisplayName("return a non-null AmazonDynamoDB object")
    void getDynamoDBClient() {

        DynamoDBClient testDynamoClient = new DynamoDBClient();
        AmazonDynamoDB testAmazonDynamoDB = testDynamoClient.getDynamoDBClient();
        assertNotNull(testAmazonDynamoDB);

    }
}