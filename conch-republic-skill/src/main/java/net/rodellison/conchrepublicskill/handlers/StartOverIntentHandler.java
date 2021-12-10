package net.rodellison.conchrepublicskill.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import net.rodellison.conchrepublicskill.models.LanguageLocalization;
import net.rodellison.conchrepublicskill.util.CommonUtils;
import net.rodellison.conchrepublicskill.util.StandardResponseUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;

// Import log4j classes.

public class StartOverIntentHandler implements RequestHandler {

    //private static final Logger log = LogManager.getLogger(StartOverIntentHandler.class);

    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(intentName("AMAZON.StartOverIntent")) || input.matches(intentName("AMAZON.NavigateHomeIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {

        System.out.println("WARN: StartOverIntentHandler called");

        LanguageLocalization locData;
        String incomingLocale = input.getRequestEnvelope().getRequest().getLocale();
        locData = CommonUtils.getLocalizationStrings(incomingLocale);
        if (locData == null) {
            System.out.println("ERROR: Failed in getting language location data");
            return null;
        }
        String layoutToUse = "Home";
        String hintString = locData.getHINT1();
        String eventImgURL = "NA";

        return StandardResponseUtil.getResponse(input,
                layoutToUse,
                hintString,
                eventImgURL,
                locData.getSTARTOVER_SPEECH1(),
                locData.getSTANDARD_RESPONSE1(),
                locData.getSTANDARD_RESPONSE2(),
                locData.getLAUNCH_TITLE(),
                locData.getHOME_DISPLAY_TEXT1(),
                locData.getHOME_DISPLAY_TEXT2(),
                locData.getHOME_DISPLAY_TEXT3(),
                locData.getAPP_TITLE());
    }
}
