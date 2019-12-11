package net.rodellison.conchrepublicskill.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;
import net.rodellison.conchrepublicskill.util.StandardResponseUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

import static com.amazon.ask.request.Predicates.intentName;

public class FallBackIntentHandler implements IntentRequestHandler {

    private static final Logger log = LogManager.getLogger(FallBackIntentHandler.class);

    @Override
    public boolean canHandle(HandlerInput handlerInput, IntentRequest intentRequest) {
        return handlerInput.matches(intentName("AMAZON.FallbackIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput handlerInput, IntentRequest intentRequest) {
        log.warn("FallBackIntentHandler called");

        String speechText = "I couldn't understand the question you asked. <p>Please try asking a question similar to one of these:</p>" +
                " What's happening in Key West in October, or What's happening around Key Largo in May";

        return StandardResponseUtil.getResponse(handlerInput,
                "Help",
                "What is happening in Key West in October?",
                "NA",
                speechText,
                "<p>Please ask a question similar to one of these:</p>",
                "What's happening in Key West in October, or What's happening around Key Largo in May",
                "The Conch Republic Help",
                "Alexa, Ask The Conch Republic:",
                "What is happening in {Key Largo, Islamorada, Marathon, the Lower Keys, Key West}<br/>",
                "");
    }
}
