package com.rodellison.conchrepublic.backend.utils;

import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.regions.Regions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DynamoDBClient {

    private static final Logger logger = LogManager.getLogger(DynamoDBClient.class);

    public AmazonDynamoDB getDynamoDBClient() {

        logger.info("Attempting to get AmazonDynamoDB Client");
        String strDynamoDBEnvironment = System.getenv("DYNAMO_DB_ENV"); //values to be: Prod or Dev
        AmazonDynamoDB client;
        if (strDynamoDBEnvironment.toLowerCase().equals("prod"))
        {
            client = AmazonDynamoDBClientBuilder.standard()
                    .withCredentials(new EnvironmentVariableCredentialsProvider())
                    .withRegion(Regions.US_EAST_1)
                    .build();
        }
        else
        {
            client = AmazonDynamoDBClientBuilder.standard()
                    .withCredentials(new ProfileCredentialsProvider())
                    .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://kubernetes.docker.internal:8000", "us-east-1"))
                    .build();
        }

        logger.info("Finished getting AmazonDynamoDB Client");
        return client;
    }

}
