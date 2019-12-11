package net.rodellison.conchrepublicskill.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import net.rodellison.conchrepublicskill.models.LanguageLocalization;
import net.rodellison.conchrepublicskill.util.CommonUtils;
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
        LanguageLocalization locData;
        String incomingLocale = input.getRequestEnvelope().getRequest().getLocale();
        locData = CommonUtils.getLocalizationStrings(incomingLocale);
        if (locData == null) {
            log.error("Failed in getting language location data");
            return null;
        }

        String speechText = locData.getCANCEL_SPEECH1();
        speechText += "<audio src='" + System.getenv("INTRO_AUDIO") + "'/>";

        return input.getResponseBuilder()
                .withSpeech(speechText)
                .withShouldEndSession(true)
                .build();
    }
}
