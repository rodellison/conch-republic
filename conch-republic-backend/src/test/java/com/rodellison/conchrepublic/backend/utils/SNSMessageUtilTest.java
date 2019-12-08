package com.rodellison.conchrepublic.backend.utils;


import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.PublishRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DisplayName("SNS Message Util should")
@ExtendWith(MockitoExtension.class)
public class SNSMessageUtilTest {

    @Mock
    AmazonSNS mockSNSClient;

    @Test
    @DisplayName(" simulate sending an SMS message")
    void sendSMSMessage() {

        String strTopic = System.getenv("SNS_TOPIC");
        if (strTopic.contains("SNS TOPIC"))
            //means the arn hasn't been provided and the default is being passed..in that case, just don't send SNS
            return;

        String strSendMessage = "message to send";

        SNSMessageUtil.SendSMSMessage(mockSNSClient, strSendMessage);
        verify(mockSNSClient).publish(any(PublishRequest.class));


    }
}