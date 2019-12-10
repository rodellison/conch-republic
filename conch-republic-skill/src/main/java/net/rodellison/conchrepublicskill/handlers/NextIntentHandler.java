package net.rodellison.conchrepublicskill.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.rodellison.conchrepublic.common.model.EventItem;
import net.rodellison.conchrepublicskill.util.EventsResponseUtil;
import net.rodellison.conchrepublicskill.util.StandardResponseUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;

public class NextIntentHandler implements RequestHandler {

    private static final String INTENT_NAME = "NextIntent";
    private static final Logger log = LogManager.getLogger(NextIntentHandler.class);

    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(intentName("AMAZON.NextIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {

        log.warn("NextIntentHandler called");
        Map<String, Object> attributes = input.getAttributesManager().getSessionAttributes();

        String strTheLocation = (String) attributes.get("STR_LOCATION");
        if (strTheLocation == null || strTheLocation.isEmpty())
            strTheLocation = "";
        String strTheMonth = (String) attributes.get("STR_MONTH");
        if (strTheMonth == null || strTheMonth.isEmpty())
            strTheMonth = "";
        int startItem = (int) attributes.get("INT_STARTITEM_VAL");
        ObjectMapper mapper = new ObjectMapper();
        try {
            List<EventItem> eventItemsList;
            Gson gson = new Gson();
            Type listEventItemsType = new TypeToken<List<EventItem>>() {
            }.getType();
            eventItemsList = gson.fromJson(attributes.get("EVENT_ITEMS").toString(), listEventItemsType);
            log.debug("In NextIntent handler, eventItemsList= " + eventItemsList);
            return EventsResponseUtil.getResponse(input, startItem, strTheMonth, strTheLocation, eventItemsList);

        } catch (Exception e) {
            //This is for graceful exit if tripped up trying to get attribute data for loop
            e.printStackTrace();
            String layoutToUse = "Home";
            String hintString = "What is happening in Key West in October?";
            String eventImgURL = "NA";
            String primaryTextDisplay = "Welcome to the Conch Republic";

            String speechText = "Sorry!, I ran into a problem, so starting over. Try asking a question like these:" +
                    " What's happening in Key West in October, or What's happening around Key Largo in May";

            String repromptSpeechText1 = "<p>Please ask a question similar to one of these:</p>";
            String repromptSpeechText2 = "What's happening in Key West in October, or What's happening around Key Largo in May";
            String Text1Display = "";
            String Text2Display = "";
            String Text3Display = "";

            return StandardResponseUtil.getResponse(input, layoutToUse, hintString, eventImgURL, speechText, repromptSpeechText1, repromptSpeechText2, primaryTextDisplay, Text1Display, Text2Display, Text3Display);
        }

    }

}
