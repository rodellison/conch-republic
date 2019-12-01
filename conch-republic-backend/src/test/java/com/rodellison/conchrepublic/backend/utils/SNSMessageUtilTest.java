package com.rodellison.conchrepublic.backend.utils;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@DisplayName("SNS Message Util should")
public class SNSMessageUtilTest {

    @Test
    @DisplayName(" simulate sending an SMS message")
    void sendSMSMessage() {

        String strTopic = System.getenv("SNS_TOPIC");
        if (strTopic.contains("SNS TOPIC"))
            //means the arn hasn't been provided and the default is being passed..in that case, just don't send SNS
            return;

        AmazonSNS mockSNSClient = mock(AmazonSNS.class);
  //      PublishResult mockSNSPubResult = mock(PublishResult.class);

        String strSendMessage = "message to send";
   //     PublishRequest pubRequest = new PublishRequest(strTopic, "dummy message");

   //     when(mockSNSClient.publish(pubRequest))
   //             .thenReturn(mockSNSPubResult);

        SNSMessageUtil.SendSMSMessage(mockSNSClient, strSendMessage);
        verify(mockSNSClient).publish(any(PublishRequest.class));


    }
}