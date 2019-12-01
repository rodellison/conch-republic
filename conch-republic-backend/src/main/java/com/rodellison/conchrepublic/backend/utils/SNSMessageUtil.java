package com.rodellison.conchrepublic.backend.utils;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class SNSMessageUtil {

    private static final String CLASS_NAME = "SNSMessageUtil";
    private static final Logger log = LogManager.getLogger(SNSMessageUtil.class);

    public static void SendSMSMessage(AmazonSNS theClient, String pubMessage) {

        try {
            String pubTopic = System.getenv("SNS_TOPIC");
            log.warn("Creating SNS Message");
            //publish to an SNS topic
            PublishRequest pubReq = new PublishRequest(pubTopic, pubMessage);
            PublishResult pubResl = theClient.publish(pubReq);
            if (pubResl != null)
                log.info("MessageId - " + pubResl.getMessageId());

        } catch (Exception e) {

            log.error("SNS Message exception error: " + e.getMessage());
        }

        return;

    }
}
