package com.rodellison.conchrepublic.backend.utils;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.regions.Regions;

public class DynamoDBClient implements DynamoDBClientInterface {

    private AmazonDynamoDB client;

    @Override
    public AmazonDynamoDB getDynamoDBClient() {

        String strDynamoDBEnvironment = System.getenv("DYNAMO_DB_ENV"); //values to be: Prod or Dev
        if (strDynamoDBEnvironment.toLowerCase().equals("prod"))
        {
            client = AmazonDynamoDBClientBuilder.standard()
                    .withRegion(Regions.US_EAST_1)
                    .build();
        }
        else
        {
            client = AmazonDynamoDBClientBuilder.standard()
                    .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://kubernetes.docker.internal:8000", "us-east-1"))
                    .build();
        }



        return client;
    }

}
