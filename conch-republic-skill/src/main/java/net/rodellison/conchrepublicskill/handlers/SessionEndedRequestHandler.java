package net.rodellison.conchrepublicskill.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.RequestEnvelope;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.SessionEndedRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

import static com.amazon.ask.request.Predicates.requestType;

// Import log4j classes.

public class SessionEndedRequestHandler implements RequestHandler {

    //private static final Logger log = LogManager.getLogger(SessionEndedRequestHandler.class);

    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(requestType(SessionEndedRequest.class));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        RequestEnvelope envelope = input.getRequestEnvelope();
        System.out.println("WARN: onSessionEnded requestId=" + envelope.getRequest().getRequestId() + ", sessionId=" + envelope.getSession().getSessionId());

        // any cleanup logic goes here

        return input.getResponseBuilder().build();
    }

}
