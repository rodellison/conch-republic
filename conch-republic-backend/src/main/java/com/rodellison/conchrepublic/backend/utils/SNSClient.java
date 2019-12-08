package com.rodellison.conchrepublic.backend.utils;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;

public class SNSClient {

    public static AmazonSNS getSNSClient() {

        return AmazonSNSClientBuilder.standard()
                .withRegion(Regions.US_EAST_1)
                .build();

    }
}
