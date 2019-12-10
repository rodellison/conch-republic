package net.rodellison.conchrepublicskill.util;

import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DynamoDBClient {

    private static final Logger log = LogManager.getLogger(DynamoDBClient.class);

    public static AmazonDynamoDB getDynamoDBClient() {

        log.info("Getting DynamoDBClient");
         AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
                .withCredentials(new EnvironmentVariableCredentialsProvider())
                .withRegion(Regions.US_EAST_1)
                .build();

        return client;
    }

}
