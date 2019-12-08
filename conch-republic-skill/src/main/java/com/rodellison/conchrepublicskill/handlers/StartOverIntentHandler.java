package com.rodellison.conchrepublicskill.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.exception.AskSdkException;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.interfaces.alexa.presentation.apl.RenderDocumentDirective;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.rodellison.conchrepublicskill.util.StandardResponseUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;

// Import log4j classes.

public class StartOverIntentHandler implements RequestHandler {

    private static final Logger log = LogManager.getLogger(StartOverIntentHandler.class);

    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(intentName("AMAZON.StartOverIntent")) || input.matches(intentName("AMAZON.NavigateHomeIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {

        log.warn("StartOverIntentHandler called");

        String layoutToUse = "Home";
        String hintString = "What is happening in Key West in October?";
        String eventImgURL = "NA";
        String primaryTextDisplay = "Welcome to the Conch Republic";

        String speechText = "Ok, <p>let's start over. Try asking a question similar to one of these:</p>" +
                " What's happening in Key West in October, or What's happening around Key Largo in May";

        String repromptSpeechText1 = "<p>Please ask a question similar to one of these:</p>";
        String repromptSpeechText2 = "What's happening in Key West in October, or What's happening around Key Largo in May";
        String Text1Display = "";
        String Text2Display = "";
        String Text3Display = "";

        return StandardResponseUtil.getResponse(input, layoutToUse, hintString, eventImgURL, speechText, repromptSpeechText1, repromptSpeechText2, primaryTextDisplay, Text1Display, Text2Display, Text3Display);

    }
}
