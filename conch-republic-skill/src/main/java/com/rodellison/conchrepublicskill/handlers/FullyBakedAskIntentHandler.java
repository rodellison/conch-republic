package com.rodellison.conchrepublicskill.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.Slot;
import com.rodellison.conchrepublicskill.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;


public class FullyBakedAskIntentHandler implements RequestHandler {

    private static final Logger log = LogManager.getLogger(FullyBakedAskIntentHandler.class);
    private static final String INTENT_NAME = "FullyBakedAskIntent";
    private static final String LOCATION_SLOT = "location";
    private static final String MONTH_SLOT = "month";
    private String strTheLocation, strTheMonth;

    @Override
    public boolean canHandle(HandlerInput input) {

        return input.matches(intentName("FullyBakedAskIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {

        IntentRequest thisRequest = (IntentRequest) input.getRequestEnvelope().getRequest();

        // Get the slots from the intent.
        Map<String, Object> attributes = input.getAttributesManager().getSessionAttributes();
        Map<String, Slot> slots = thisRequest.getIntent().getSlots();
        Slot slotLocation, slotMonth;
        ArrayList<String> events;
        int intIterator = 0;

        log.warn("FullyBakedAskIntent called");

        try
        {
            slotLocation = slots.get(LOCATION_SLOT);
            strTheLocation = slotLocation.getValue().toLowerCase();
        }
        catch (Exception ex)
        {
            strTheLocation = "all";
        }
        try
        {
            slotMonth = slots.get(MONTH_SLOT);
            strTheMonth = slotMonth.getValue().toLowerCase();
        }
        catch (Exception ex)
        {
            strTheMonth = "all";
        }


        log.info("FullyBakedAskIntent values received.. Location=" + strTheLocation + " with month: " + strTheMonth);

        String layoutToUse = "Events";
        String hintString = "What is happening in Key West in October?";
        String eventImgURL = "NA";
        String primaryTextDisplay = "OK, here are the events I found: ";
        String speechText = "OK, here are the events I found: ";

        String repromptSpeechText1 = "<p>To hear details about any of these events, just say First, Second or Third. :</p>";

//        (If there are more events available)
//        Add… OR, you can say NEXT to hear more events, or say one of the following: "New Search" or "I'm done".
//        (Else)
//                There aren't any more events after these.. You say one of the following: "New Search" or "I'm done".

        String repromptSpeechText2 = "You can say NEXT to hear more events, say New Search to start over, or I'm done to exit.";
        String Text1Display = "Event 1";
        String Text2Display = "Event 2";
        String Text3Display = "Event 3";

        attributes.put("STR-MONTH", strTheLocation);
        attributes.put("STR_LOCATION", strTheMonth);
        attributes.put("INT-ITERATOR-VAL", intIterator);

        return StandardResponseUtil.getResponse(input, layoutToUse, hintString, eventImgURL, speechText, repromptSpeechText1, repromptSpeechText2, primaryTextDisplay, Text1Display, Text2Display, Text3Display);


    }
}
