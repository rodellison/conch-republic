package net.rodellison.conchrepublicskill.util;

import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.translate.AmazonTranslate;
import com.amazonaws.services.translate.AmazonTranslateClientBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TranslateClient {

    private static final Logger log = LogManager.getLogger(TranslateClient.class);

    public static AmazonTranslate getTranslateClient() {

        log.info("Getting DynamoDBClient");
        AmazonTranslate client = AmazonTranslateClientBuilder.standard()
                .withCredentials(new EnvironmentVariableCredentialsProvider())
                .withRegion(Regions.US_EAST_1)
                .build();

        return client;
    }

}
