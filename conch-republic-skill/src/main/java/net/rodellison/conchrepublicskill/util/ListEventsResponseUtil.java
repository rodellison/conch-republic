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
import net.rodellison.conchrepublicskill.models.LanguageLocalization;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import net.rodellison.conchrepublic.common.model.*;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class ListEventsResponseUtil {

    private static Image myStandardCardImage;
    private static final Logger log = LogManager.getLogger(ListEventsResponseUtil.class);

    public static Optional<Response> getResponse(HandlerInput input, int eventListStartItem, String strTheMonth, String strTheLocation, List<EventItem> eventItemsList, LanguageLocalization locData) {

        Map<String, Object> attributes = input.getAttributesManager().getSessionAttributes();

        String layoutToUse = "Events";
        String hintString = locData.getEVENTLIST_HINT();
        String eventImgURL = "NA";
        String primaryTextDisplay = "";
        StringBuilder speechOutputBuilder = new StringBuilder();

        String speechText = "";
        String repromptSpeechText1 = "";
        String repromptSpeechText2 = "";
        Boolean addLocationToEvent = false;

        String strFirst = "";
        String strSecond = "";
        String strThird = "";

        if (strTheMonth.equals("") && strTheLocation.equals("")) {
            primaryTextDisplay = locData.getEVENTLIST_PRIMARY_TEXT();
            addLocationToEvent = true;
        }
        if (!strTheMonth.equals("") && !strTheLocation.equals("")) {

            primaryTextDisplay = CommonUtils.savedLocale == "es-US" ? "Eventos en " + CommonUtils.toTitleCase(strTheLocation) + " en " + CommonUtils.toTitleCase(strTheMonth)
                    : "Events for " + CommonUtils.toTitleCase(strTheLocation) + " in " + CommonUtils.toTitleCase(strTheMonth);
        }
        if (strTheMonth.equals("") && !strTheLocation.equals("")) {
            primaryTextDisplay = CommonUtils.savedLocale == "es-US" ? "Eventos en " + CommonUtils.toTitleCase(strTheLocation)
                    : "Events in " + CommonUtils.toTitleCase(strTheLocation);
        }
        if (!strTheMonth.equals("") && strTheLocation.equals("")) {
            primaryTextDisplay = CommonUtils.savedLocale == "es-US" ? "Eventos en " + CommonUtils.toTitleCase(strTheMonth)
                    : "Events in " + CommonUtils.toTitleCase(strTheMonth);

            addLocationToEvent = true;
        }

        int startItem = eventListStartItem;
        log.debug("This loop starting item will be: " + startItem);
        List<String> thisIterationList = new ArrayList<>();
        if (eventItemsList.size() > 0) {
            log.debug("This loop recognizes: " + eventItemsList.size() + " items");
            if (eventListStartItem == 0)
                speechOutputBuilder.append(locData.getEVENTLIST_SPEECH_INTRO() + "<break time=\"1s\"/>");

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

                        eventShortDescription += thisItem.getEventName().replace("&nbsp;", " ");
                        String strSpokenEvent = thisItem.getEventName();
                        strSpokenEvent = strSpokenEvent.replace("presents", "<phoneme alphabet='ipa' ph='pɹizɛnts'>presents</phoneme> <break time=\"0.25s\"/>");
                        if (addLocationToEvent) {
                            String titleLocation = CommonUtils.toTitleCase(KeysLocations.getLocation(thisItem.getEventLocation()));
                            eventShortDescription += ", in " + CommonUtils.toTitleCase(titleLocation).replace("-", " ");
                            strSpokenEvent += ", in " + CommonUtils.toTitleCase(titleLocation).replace("-", " ");
                        }

                        speechOutputBuilder.append(strSpokenEvent + "<break time=\"1s\"/>");
                        thisIterationList.add(eventShortDescription);

                    } else {
                        log.debug("eventItemsList.get(startItem++) was null");
                    }

                    //load up the first, second and third variables that will be stored in session vars for later use
                    //if user asks for details on an event
                    switch (i) {
                        case 0:
                            strFirst = thisItem.getEventID();
                            break;
                        case 1:
                            strSecond = thisItem.getEventID();
                            break;
                        case 2:
                            strThird = thisItem.getEventID();
                            break;
                    }

                } catch (Exception ex) {
                    atEnd = true;
                    log.debug("atEnd = true");
                    break;
                }
            }

            if (thisIterationList.size() == 3) {
                repromptSpeechText1 = locData.getEVENTLIST_RESPONSE1_3ITEMS();
            }
            if (thisIterationList.size() == 2) {
                thisIterationList.add("");
                repromptSpeechText1 = locData.getEVENTLIST_RESPONSE1_2ITEMS();
            }
            if (thisIterationList.size() == 1) {
                thisIterationList.add("");
                thisIterationList.add("");
                repromptSpeechText1 = locData.getEVENTLIST_RESPONSE1_1ITEM();
            }
            if (thisIterationList.size() == 0) {
                thisIterationList.add(locData.getEVENTLIST_DISPLAY_TEXT1_NOMORE());
                thisIterationList.add("");
                thisIterationList.add("");
                atEnd = true;
            }

            if (!atEnd)
                repromptSpeechText2 = locData.getEVENTLIST_RESPONSE2_MORE();
            else
                repromptSpeechText2 = locData.getEVENTLIST_RESPONSE2_NOMORE();

        } else {

            thisIterationList.add(locData.getEVENTLIST_DISPLAY_TEXT1_NOMORE());
            thisIterationList.add("");
            thisIterationList.add("");
            repromptSpeechText1 = "";
            repromptSpeechText2 = locData.getEVENTLIST_RESPONSE2_NOMORE();

        }

        attributes.put("STR_MONTH", strTheMonth);
        attributes.put("STR_LOCATION", strTheLocation);
        attributes.put("INT_STARTITEM_VAL", startItem);
        attributes.put("FIRST", strFirst);
        attributes.put("SECOND", strSecond);
        attributes.put("THIRD", strThird);

        Gson gson = new Gson();
        attributes.put("EVENT_ITEMS", gson.toJson(eventItemsList));

        //Check if we're at the end of the events for this search
        if (startItem >= eventItemsList.size())
            speechOutputBuilder.append(locData.getEVENTLIST_DISPLAY_TEXT1_NOMORE() + "<break time=\"1s\"/>");

        speechOutputBuilder.append(repromptSpeechText1);
        speechOutputBuilder.append(repromptSpeechText2);

        speechText = speechOutputBuilder.toString();
        speechText = CommonUtils.prepForSSMLSpeech(speechText);
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
                    .withStandardCard(System.getenv("APP_TITLE"), CommonUtils.prepForSimpleStandardCardText(repromptSpeechText1 + repromptSpeechText2), myStandardCardImage)
                    .build();

        }
    }
}