package net.rodellison.conchrepublicskill.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.impl.LaunchRequestHandler;
import com.amazon.ask.model.LaunchRequest;
import com.amazon.ask.model.Response;
import net.rodellison.conchrepublicskill.util.StandardResponseUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

import static com.amazon.ask.request.Predicates.requestType;

public class CustomLaunchRequestHandler implements LaunchRequestHandler  {

    private static final Logger log = LogManager.getLogger(CustomLaunchRequestHandler.class);

    @Override
    public boolean canHandle(HandlerInput handlerInput, LaunchRequest launchRequest) {
        return handlerInput.matches(requestType(LaunchRequest.class));
    }

    @Override
    public Optional<Response> handle(HandlerInput handlerInput, LaunchRequest launchRequest) {
        log.warn("LaunchRequestHandler called");

        String speechText = "<audio src='" + System.getenv("INTRO_AUDIO") + "'/>";
        speechText += "Hello!, and Welcome to the Conch Republic, where you can find out about all the great events happening" +
                " in the Florida Keys. <p>Try asking a question similar to one of these</p>" +
                " What's happening in Key West in October, or What's happening around Key Largo in May";

        return StandardResponseUtil.getResponse(handlerInput,
                "Home",
                "What is happening in Key West in October?",
                "NA",
                speechText,
                "<p>Please ask a question similar to one of these:</p>",
                "What's happening in Key West in October, or What's happening around Key Largo in May",
                "Welcome to the Conch Republic",
                "", "", "");
    }
}
