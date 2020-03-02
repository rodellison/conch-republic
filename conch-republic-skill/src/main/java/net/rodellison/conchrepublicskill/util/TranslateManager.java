package net.rodellison.conchrepublicskill.util;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.translate.AmazonTranslate;
import com.amazonaws.services.translate.model.TranslateTextRequest;
import com.amazonaws.services.translate.model.TranslateTextResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TranslateManager {

    private static final Logger log = LogManager.getLogger(TranslateManager.class);
    private static AmazonTranslate client;

    public TranslateManager(AmazonTranslate theClient) {
        this.client = theClient;
    }

    //Translate the English content into appropirate language output
    public String TranslateOutput(String inputText, String outputLanguage) {

        try {

            TranslateTextRequest request = new TranslateTextRequest()
                    .withText(inputText)
                    .withSourceLanguageCode("en")
                    .withTargetLanguageCode(outputLanguage);

            TranslateTextResult result = client.translateText(request);
            return result.getTranslatedText();

        } catch (Exception e) {
            log.error(e.getMessage());
            return null;

        }
    }
}


