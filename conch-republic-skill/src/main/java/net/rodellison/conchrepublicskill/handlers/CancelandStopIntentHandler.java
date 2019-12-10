package net.rodellison.conchrepublicskill.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;


public class CancelandStopIntentHandler implements RequestHandler {

    private static final Logger log = LogManager.getLogger(CancelandStopIntentHandler.class);

    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(intentName("AMAZON.StopIntent").or(intentName("AMAZON.CancelIntent")));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {

        log.warn("CancelandStopIntentHandler called");
        String speechText = "<p>Thanks for visiting the Conch Republic. Ready for a Margarita?</p>";
        speechText += "<audio src='" + System.getenv("INTRO_AUDIO") + "'/>";
//        speechText += "<audio src='soundbank://soundlibrary/musical/amzn_sfx_musical_drone_intro_02'/>";

        return input.getResponseBuilder()
                .withSpeech(speechText)
                .withShouldEndSession(true)
                .build();
    }
}
