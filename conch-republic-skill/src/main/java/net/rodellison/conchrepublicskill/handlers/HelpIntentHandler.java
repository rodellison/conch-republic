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


public class HelpIntentHandler implements IntentRequestHandler {

    private static final Logger log = LogManager.getLogger(HelpIntentHandler.class);

    @Override
    public boolean canHandle(HandlerInput handlerInput, IntentRequest intentRequest) {

        return handlerInput.matches(intentName("AMAZON.HelpIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput handlerInput, IntentRequest intentRequest) {
        log.warn("HelpIntentHandler called");

        String speechText = "There's a few ways to ask for the Florida Keys event information. " +
                "<p>The locations you can ask about are: Key Largo, Islamorada, Marathon, The Lower Keys, and Key West. " +
                "You can ask about events at a location for a certain month. Try asking a question similar to one of these:</p>" +
                " What's happening in Key West in October, or What events are happening near Islamorada";

        return StandardResponseUtil.getResponse(handlerInput,
                "Help",
                "What is happening in Key West in October?",
                "NA",
                speechText,
                "<p>Ask a question similar to one of these:</p>",
                "What's happening in Key West, or What events are happening in Marathon in May?",
                "The Conch Republic Help",
                "Alexa, Ask The Conch Republic:",
                "What is happening in {Key Largo, Islamorada, Marathon, the Lower Keys, Key West}<br/>",
                "");
    }

}
