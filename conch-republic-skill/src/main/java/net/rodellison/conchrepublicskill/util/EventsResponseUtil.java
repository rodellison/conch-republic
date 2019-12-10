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
import net.rodellison.conchrepublic.common.utils.DateUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import net.rodellison.conchrepublic.common.model.*;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class EventsResponseUtil {

    private static Image myStandardCardImage;
    private static final Logger log = LogManager.getLogger(EventsResponseUtil.class);

    public static Optional<Response> getResponse(HandlerInput input, int eventListStartItem, String strTheMonth, String strTheLocation, List<EventItem> eventItemsList) {

        Map<String, Object> attributes = input.getAttributesManager().getSessionAttributes();

        String layoutToUse = "Events";
        String hintString = "What is happening in Key West in October?";
        String eventImgURL = "NA";
        String primaryTextDisplay = "";
        StringBuilder speechOutputBuilder = new StringBuilder();

        String speechText = "";
        String repromptSpeechText1 = "";
        String repromptSpeechText2 = "You can say New Search to start over, or I'm done to exit.";


        if (!strTheMonth.equals("") && !strTheLocation.equals("")) {
            primaryTextDisplay = "Events for " + CommonUtils.toTitleCase(strTheLocation) + " in " + CommonUtils.toTitleCase(strTheMonth);
        }
        if (strTheMonth.equals("") && !strTheLocation.equals("")) {
            primaryTextDisplay = "Events for " + CommonUtils.toTitleCase(strTheLocation);
        }
        if (!strTheMonth.equals("") && strTheLocation.equals("")) {
            primaryTextDisplay = "Events in " + CommonUtils.toTitleCase(strTheMonth);
        }


        int startItem = eventListStartItem;
        log.debug("This loop starting item will be: " + startItem);
        List<String> thisIterationList = new ArrayList<>();
        if (eventItemsList.size() > 0) {
            log.debug("This loop recognizes: " + eventItemsList.size() + " items");
            if (eventListStartItem == 0)
                speechOutputBuilder.append("<p>Here's what I found: </p>");

            Boolean atEnd = false;
            for (int i = 0; i < 3; i++) {
                String eventShortDescription = "";
                try {
                    EventItem thisItem = eventItemsList.get(startItem++);
                    if (thisItem != null) {
                        String startDate = DateUtils.convertNumericDateToFormatted(thisItem.getEventStartDate());
                        String endDate = DateUtils.convertNumericDateToFormatted(thisItem.getEventEndDate());
                        if (startDate.equals(endDate)) {
                            eventShortDescription += startDate + ": ";
                            speechOutputBuilder.append("On " + startDate + ", the ");
                        } else {
                            eventShortDescription += startDate + " - " + endDate + ": ";
                            speechOutputBuilder.append("From " + startDate + " til " + endDate + ", the ");
                        }

                        eventShortDescription += thisItem.getEventName();
                        String strSpokenEvent = thisItem.getEventName();
                        strSpokenEvent = strSpokenEvent.replace("presents", "<phoneme alphabet='ipa' ph='pɹizɛnts'>presents</phoneme> <break time=\"0.25s\"/>");
                        speechOutputBuilder.append(strSpokenEvent + "<break time=\"1s\"/>");
                        thisIterationList.add(eventShortDescription);
                    } else {
                        log.debug("eventItemsList.get(startItem++) was null");
                    }

                } catch (Exception ex) {
                    atEnd = true;
                    log.debug("atEnd = true");
                }
            }

            if (thisIterationList.size() == 3) {
                repromptSpeechText1 = "<p>To hear details about any of these events, just say First, Second or Third.</p>";
            }
            if (thisIterationList.size() == 2) {
                thisIterationList.add("");
                repromptSpeechText1 = "<p>To hear details about any of these events, just say First or Second.</p>";
            }
            if (thisIterationList.size() == 1) {
                thisIterationList.add("");
                thisIterationList.add("");
                repromptSpeechText1 = "<p>To hear details about this event, just say First.</p>";
            }
            if (thisIterationList.size() == 0) {
                thisIterationList.add("There are no additional events");
                thisIterationList.add("");
                thisIterationList.add("");
                atEnd = true;
            }

            if (!atEnd)
                repromptSpeechText2 = "You can say NEXT to hear more events, say New Search to start over, or I'm done to exit.";
            else
                repromptSpeechText2 = "You can say New Search to start over, or I'm done to exit.";

        } else {

            thisIterationList.add("There are no additional events.");
            thisIterationList.add("");
            thisIterationList.add("");
            repromptSpeechText1 = "";
            repromptSpeechText2 = "You can say New Search to start over, or I'm done to exit.";

        }

        attributes.put("STR_MONTH", strTheMonth);
        attributes.put("STR_LOCATION", strTheLocation);
        attributes.put("INT_STARTITEM_VAL", startItem);
        Gson gson = new Gson();
        attributes.put("EVENT_ITEMS", gson.toJson(eventItemsList));

        //Check if we're at the end of the events for this search
        if (startItem >= eventItemsList.size())
            speechOutputBuilder.append("Theres no additional events. " + "<break time=\"1s\"/>");

        speechOutputBuilder.append(repromptSpeechText1);
        speechOutputBuilder.append(repromptSpeechText2);

        speechText = speechOutputBuilder.toString();
        speechText = speechText.replace("(", "");
        speechText = speechText.replace(")", "");
        speechText = speechText.replace("&", " and ");
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
                ObjectNode ConchRepublicTemplateProperties = (ObjectNode) dataSources.get("ConchRepublicTemplateData").get("properties");

                log.info("EventsResponseUtil called, setting properties");
                if (thisIterationList.size() > 0) {
                    ArrayNode theArray = mapper.valueToTree(thisIterationList);
                    ConchRepublicTemplateProperties.putArray("EventText").addAll(theArray);
                }

                ConchRepublicTemplateProperties.put("LayoutToUse", layoutToUse);
                ConchRepublicTemplateProperties.put("HeadingText", primaryTextDisplay);
                ConchRepublicTemplateProperties.put("EventImageUrl", eventImgURL);
                ConchRepublicTemplateProperties.put("HintString", hintString);

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
                    .withStandardCard(System.getenv("APP_TITLE"), CommonUtils.prepForSimpleStandardCardText(repromptSpeechText1 + repromptSpeechText2), myStandardCardImage)
                    .build();

        }
    }
}