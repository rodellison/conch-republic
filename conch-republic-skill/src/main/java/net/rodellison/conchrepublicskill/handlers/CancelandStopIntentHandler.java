package net.rodellison.conchrepublicskill.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import net.rodellison.conchrepublicskill.models.LanguageLocalization;
import net.rodellison.conchrepublicskill.util.CommonUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;
import java.util.Random;

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
        LanguageLocalization locData;
        String incomingLocale = input.getRequestEnvelope().getRequest().getLocale();
        locData = CommonUtils.getLocalizationStrings(incomingLocale);
        if (locData == null) {
            log.error("Failed in getting language location data");
            return null;
        }

        int whichClose = getRandomNumberInRange(0,2);
        String speechText = whichClose == 0 ? locData.getCANCEL_SPEECH1() : locData.getCANCEL_SPEECH2();
        speechText += "<audio src='" + System.getenv("INTRO_AUDIO") + "'/>";

        return input.getResponseBuilder()
                .withSpeech(speechText)
                .withShouldEndSession(true)
                .build();
    }

    private static int getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }
}
