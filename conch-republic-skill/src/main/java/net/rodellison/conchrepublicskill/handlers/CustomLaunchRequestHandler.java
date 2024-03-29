package net.rodellison.conchrepublicskill.handlers;


import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.impl.LaunchRequestHandler;
import com.amazon.ask.model.LaunchRequest;
import com.amazon.ask.model.Response;
import net.rodellison.conchrepublicskill.models.LanguageLocalization;
import net.rodellison.conchrepublicskill.util.CommonUtils;
import net.rodellison.conchrepublicskill.util.StandardResponseUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

import static com.amazon.ask.request.Predicates.requestType;

public class CustomLaunchRequestHandler implements LaunchRequestHandler {

    //private static final Logger log = LogManager.getLogger(CustomLaunchRequestHandler.class);

    @Override
    public boolean canHandle(HandlerInput handlerInput, LaunchRequest launchRequest) {
        return handlerInput.matches(requestType(LaunchRequest.class));
    }

    @Override
    public Optional<Response> handle(HandlerInput handlerInput, LaunchRequest launchRequest) {
        System.out.println("WARN: LaunchRequestHandler called");


        LanguageLocalization locData;
        String incomingLocale = launchRequest.getLocale();
        locData = CommonUtils.getLocalizationStrings(incomingLocale);
        if (locData == null) {
            System.out.println("ERROR: Failed in getting language location data");
            return null;
        }

        String speechText = "<audio src='" + System.getenv("INTRO_AUDIO") + "'/>";
        speechText += locData.getLAUNCH_GREET1() +
                locData.getLAUNCH_GREET2() +
                locData.getLAUNCH_GREET3();

        return StandardResponseUtil.getResponse(handlerInput,
                "Home",
                locData.getHINT1(),
                "NA",
                speechText,
                locData.getSTANDARD_RESPONSE1(),
                locData.getSTANDARD_RESPONSE2(),
                locData.getLAUNCH_TITLE(),
                locData.getHOME_DISPLAY_TEXT1(),
                locData.getHOME_DISPLAY_TEXT2(),
                locData.getHOME_DISPLAY_TEXT3(),
                locData.getAPP_TITLE());

    }
}
