package com.rodellison.conchrepublic.backend.utils;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;

public interface DynamoDBClientInterface {
     AmazonDynamoDB getDynamoDBClient();
}
