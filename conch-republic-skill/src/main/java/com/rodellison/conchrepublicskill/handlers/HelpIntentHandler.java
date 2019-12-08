package com.rodellison.conchrepublicskill.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import com.rodellison.conchrepublicskill.util.StandardResponseUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

import static com.amazon.ask.request.Predicates.intentName;


public class HelpIntentHandler implements RequestHandler {

    private static final String INTENT_NAME = "HelpIntent";
    private static final Logger log = LogManager.getLogger(HelpIntentHandler.class);

    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(intentName("AMAZON.HelpIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {

        log.warn("HelpIntentHandler called");
        String layoutToUse = "Help";
        String hintString = "What is happening in Key West in October?";
        String eventImgURL = "NA";
        String primaryTextDisplay = "The Conch Republic Help";

        String speechText = "There's various ways to ask for the Florida Keys event information. " +
                "<p>The Keys locations that you can ask about are: Key Largo, Islamorada, Marathon, The Lower Keys, and Key West. " +
                "You can ask about the events at a location for a certain month. Try asking a question similar to one of these:</p>" +
                " What's happening in Key West in October, or What events are happening near Islamorada";

        String repromptSpeechText1 = "<p>Ask a question similar to one of these:</p>";
        String repromptSpeechText2 = "What's happening in Key West, or What events are happening in Marathon in May?";

        String Text1Display = "Alexa, Ask The Conch Republic:";
        String Text2Display = "What is happening in {Key Largo, Islamorada, Marathon, the Lower Keys, Key West}<br/>";
        String Text3Display = "";

        return StandardResponseUtil.getResponse(input, layoutToUse, hintString, eventImgURL, speechText, repromptSpeechText1, repromptSpeechText2, primaryTextDisplay, Text1Display, Text2Display, Text3Display);


    }

}
