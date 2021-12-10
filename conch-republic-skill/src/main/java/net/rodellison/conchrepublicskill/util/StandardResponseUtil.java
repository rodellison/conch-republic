package net.rodellison.conchrepublicskill.util;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.exception.AskSdkException;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.interfaces.alexa.presentation.apl.RenderDocumentDirective;
import com.amazon.ask.model.ui.Image;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;
import java.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StandardResponseUtil {

    private static Image myStandardCardImage;
    //private static final Logger log = LogManager.getLogger(StandardResponseUtil.class);

    public static Optional<Response> getResponse(HandlerInput input, String layoutToUse, String hintString, String eventImgURL, String speechText, String repromptSpeechText1, String repromptSpeechText2, String primaryTextDisplay, String text1Display, String text2Display, String text3Display, String appTitle) {
        if (CommonUtils.supportsApl(input)) {
            //  ViewportProfile viewportProfile = ViewportUtils.getViewportProfile(input.getRequestEnvelope());

            try {
                ObjectMapper mapper = new ObjectMapper();
                JsonNode node = mapper.readTree(new File("ConchRepublic.json"));
                JsonNode documentNode = node.get("document");
                JsonNode dataSourcesNode = node.get("datasources");

                TypeReference<HashMap<String, Object>> ConchRepublicMapType = new TypeReference<HashMap<String, Object>>() {
                };

                System.out.println("INFO: LaunchRequestHandler called, reading documentNode value");
                Map<String, Object> document = mapper.readValue(documentNode.toString(), ConchRepublicMapType);

                System.out.println("INFO: LaunchRequestHandler called, reading dataSources node");
                JsonNode dataSources = mapper.readTree(dataSourcesNode.toString());

                System.out.println("INFO: LaunchRequestHandler called, getting props node");
                ObjectNode ConchRepublicTemplateProperties = (ObjectNode) dataSources.get("TemplateData").get("properties");

                System.out.println("INFO: LaunchRequestHandler called, setting props");

                ConchRepublicTemplateProperties.put("LayoutToUse", layoutToUse);
                ConchRepublicTemplateProperties.put("HeadingText", primaryTextDisplay);
                ConchRepublicTemplateProperties.put("EventImageUrl", eventImgURL);
                ConchRepublicTemplateProperties.put("HintString", hintString);
                ConchRepublicTemplateProperties.put("Locale", CommonUtils.savedLocale);

                List<String> textEvents = new ArrayList<String>();
                textEvents.add(text1Display);
                textEvents.add(text2Display);
                textEvents.add(text3Display);
                ArrayNode theArray = mapper.valueToTree(textEvents);
                ConchRepublicTemplateProperties.putArray("EventText").addAll(theArray);

                System.out.println("INFO: LaunchRequestHandler called, building Render Document");

                RenderDocumentDirective documentDirective = RenderDocumentDirective.builder()
                        .withDocument(document)
                        .withDatasources(mapper.convertValue(dataSources, new TypeReference<Map<String, Object>>() {
                        }))
                        .build();

                System.out.println("DEBUG: " + documentDirective);

                System.out.println("INFO: LaunchRequestHandler called, calling responseBuilder");

                return input.getResponseBuilder()
                        .withSpeech(speechText)
                        .withReprompt(repromptSpeechText1 + repromptSpeechText2)
                        .addDirective(documentDirective)
                        .build();

            } catch (IOException e) {
                throw new AskSdkException("Unable to read or deserialize device data", e);
            }
        } else {

            myStandardCardImage = Image.builder()
                    .withLargeImageUrl(System.getenv("LARGE_IMG_URL"))
                    .withSmallImageUrl(System.getenv("SMALL_IMG_URL"))
                    .build();

            return input.getResponseBuilder()
                    .withSpeech(speechText)
                    .withReprompt(repromptSpeechText1 + repromptSpeechText2)
                    .withStandardCard(appTitle, CommonUtils.prepForSimpleStandardCardText(repromptSpeechText1 + repromptSpeechText2), myStandardCardImage)
                    .build();

        }
    }
}