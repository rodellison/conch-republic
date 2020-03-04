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
import com.google.gson.Gson;
import net.rodellison.conchrepublic.common.model.EventItem;
import net.rodellison.conchrepublic.common.model.KeysLocations;
import net.rodellison.conchrepublic.common.utils.DateUtils;
import net.rodellison.conchrepublicskill.models.LanguageLocalization;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class EventItemResponseUtil {

    private static Image myStandardCardImage;
    private static final Logger log = LogManager.getLogger(EventItemResponseUtil.class);

    public static Optional<Response> getResponse(HandlerInput input, EventItem theItem, LanguageLocalization locData) {

        Map<String, Object> attributes = input.getAttributesManager().getSessionAttributes();
        final String domainURL = "https://fla-keys.com";

        String layoutToUse = "EventItem";
        String hintString = locData.getEVENTITEM_HINT();
        String primaryTextDisplay = "";
        StringBuilder speechOutputBuilder = new StringBuilder();

        String speechText = "";
        String cardText = "";
        String repromptSpeechText1 = "";
        String repromptSpeechText2 = hintString;

        String eventImgURL = "NA";
        String eventTextToDisplay;

        List<String> thisIterationList = new ArrayList<>();

        primaryTextDisplay = theItem.getEventName().replace("&nbsp;", " ");
        cardText = primaryTextDisplay + "<br/>";

        speechOutputBuilder.append("The ");
        String tempEventName = theItem.getEventName().replace("presents", "<phoneme alphabet='ipa' ph='pɹizɛnts'>presents</phoneme>");
        tempEventName = tempEventName.replace("&nbsp;", " ");
        speechOutputBuilder.append(tempEventName +  "<break time=\"0.5s\"/>");
        eventTextToDisplay = theItem.getEventDescription().replace("&nbsp;", " ");

        log.debug(eventTextToDisplay);

        //Convert the Event Item text into language appropriate output..
        if (CommonUtils.savedLocale.equals("es-US"))
        {
            TranslateManager myTranslateManager;
            myTranslateManager = new TranslateManager(TranslateClient.getTranslateClient());
            eventTextToDisplay = myTranslateManager.TranslateOutput(eventTextToDisplay, "es");
        }

        speechOutputBuilder.append(eventTextToDisplay);

        String dateText = "";
        String startDate = DateUtils.convertNumericDateToFormatted(theItem.getEventStartDate(), CommonUtils.savedLocale);
        String endDate = DateUtils.convertNumericDateToFormatted(theItem.getEventEndDate(), CommonUtils.savedLocale);
        if (startDate.equals(endDate)) {
            dateText += startDate;
        } else {
            dateText += startDate + " - " + endDate ;
        }
        cardText += dateText + "<br/>";
        cardText += eventTextToDisplay + "<br/>";

        thisIterationList.add(dateText);
        thisIterationList.add(eventTextToDisplay);
        thisIterationList.add("");

        if (!theItem.getEventImgURL().trim().equals(""))
            eventImgURL = domainURL + theItem.getEventImgURL();

        speechOutputBuilder.append("<break time=\"0.5s\"/>");
        speechOutputBuilder.append(repromptSpeechText1);
        speechOutputBuilder.append(repromptSpeechText2);

        speechText = speechOutputBuilder.toString();
        speechText = CommonUtils.prepForSSMLSpeech(speechText);
        if (CommonUtils.savedLocale.equals("es-US"))  //a few extra things to clean up if english to spanish text conversion happened..
        {
            speechText = CommonUtils.prepForSSMLSpeechForSpanish(speechText);
        }
        log.debug("Heres whats being said: " + speechText);

        if (CommonUtils.supportsApl(input)) {
            //  ViewportProfile viewportProfile = ViewportUtils.getViewportProfile(input.getRequestEnvelope());

            try {
                ObjectMapper mapper = new ObjectMapper();
                JsonNode node = mapper.readTree(new File("ConchRepublic.json"));
                JsonNode documentNode = node.get("document");
                JsonNode dataSourcesNode = node.get("datasources");

                TypeReference<HashMap<String, Object>> ConchRepublicMapType = new TypeReference<HashMap<String, Object>>() {
                };

                log.info("EventsResponseUtil called, reading documentNode and dataSources values");
                Map<String, Object> document = mapper.readValue(documentNode.toString(), ConchRepublicMapType);
                JsonNode dataSources = mapper.readTree(dataSourcesNode.toString());
                ObjectNode ConchRepublicTemplateProperties = (ObjectNode) dataSources.get("TemplateData").get("properties");

                log.info("EventsResponseUtil called, setting props");
                ArrayNode theArray = mapper.valueToTree(thisIterationList);
                ConchRepublicTemplateProperties.putArray("EventText").addAll(theArray);

                ConchRepublicTemplateProperties.put("LayoutToUse", layoutToUse);
                ConchRepublicTemplateProperties.put("HeadingText", primaryTextDisplay);
                ConchRepublicTemplateProperties.put("EventImageUrl", eventImgURL);
                ConchRepublicTemplateProperties.put("HintString", hintString);
                ConchRepublicTemplateProperties.put("Locale", CommonUtils.savedLocale);

                log.info("LaunchRequestHandler called, building Render Document");
                RenderDocumentDirective documentDirective = RenderDocumentDirective.builder()
                        .withDocument(document)
                        .withDatasources(mapper.convertValue(dataSources, new TypeReference<Map<String, Object>>() {
                        }))
                        .build();

                log.debug(documentDirective);

                log.info("LaunchRequestHandler called, calling responseBuilder");

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
                    .withStandardCard(locData.getAPP_TITLE(), CommonUtils.prepForSimpleStandardCardText(cardText), myStandardCardImage)
                    .build();

    //                 .withStandardCard(System.getenv("APP_TITLE"), CommonUtils.prepForSimpleStandardCardText(cardText + repromptSpeechText1 + repromptSpeechText2), myStandardCardImage)

        }
    }
}