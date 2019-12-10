package net.rodellison.conchrepublicskill.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import net.rodellison.conchrepublicskill.util.StandardResponseUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

import static com.amazon.ask.request.Predicates.intentName;

public class FallBackIntentHandler implements RequestHandler{

    private static final String INTENT_NAME = "FallBackIntent";
    private static final Logger log = LogManager.getLogger(FallBackIntentHandler.class);

    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(intentName("AMAZON.FallbackIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {

        log.warn("FallBackIntentHandler called");

        String layoutToUse = "Help";
        String hintString = "What is happening in Key West in October?";
        String eventImgURL = "NA";
        String primaryTextDisplay = "The Conch Republic Help";

        String speechText = "Sorry!, I couldn't understand the question you asked. <p>Please try asking a question similar to one of these:</p>" +
                " What's happening in Key West in October, or What's happening around Key Largo in May";

        String repromptSpeechText1 = "<p>Please ask a question similar to one of these:</p>";
        String repromptSpeechText2 = "What's happening in Key West in October, or What's happening around Key Largo in May";

        String Text1Display = "Alexa, Ask The Conch Republic:";
        String Text2Display = "What is happening in {Key Largo, Islamorada, Marathon, the Lower Keys, Key West}<br/>" ;
        String Text3Display = "";

        return StandardResponseUtil.getResponse(input,
                layoutToUse,
                hintString,
                eventImgURL,
                speechText,
                repromptSpeechText1,
                repromptSpeechText2,
                primaryTextDisplay,
                Text1Display,
                Text2Display,
                Text3Display);


    }

}
