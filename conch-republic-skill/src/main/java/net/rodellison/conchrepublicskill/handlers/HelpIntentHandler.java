package net.rodellison.conchrepublicskill.handlers;

import com.amazon.ask.attributes.AttributesManager;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;
import net.rodellison.conchrepublicskill.models.LanguageLocalization;
import net.rodellison.conchrepublicskill.util.CommonUtils;
import net.rodellison.conchrepublicskill.util.StandardResponseUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

import static com.amazon.ask.request.Predicates.intentName;


public class HelpIntentHandler implements IntentRequestHandler {

    //private static final Logger log = LogManager.getLogger(HelpIntentHandler.class);

    @Override
    public boolean canHandle(HandlerInput handlerInput, IntentRequest intentRequest) {

        return handlerInput.matches(intentName("AMAZON.HelpIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput handlerInput, IntentRequest intentRequest) {
        System.out.println("WARN: HelpIntentHandler called");

        LanguageLocalization locData;
        String incomingLocale = intentRequest.getLocale();
        System.out.println("INFO: Incoming intent locale: " + incomingLocale);
        locData = CommonUtils.getLocalizationStrings(incomingLocale);
        if (locData == null) {
            System.out.println("ERROR: Failed in getting language location data");
            return null;
        }

        String speechText = locData.getHELP1()  +
                locData.getHELP2() +
                locData.getHELP3() +
                locData.getHELP4();

        return StandardResponseUtil.getResponse(handlerInput,
                "Help",
                locData.getHINT1(),
                "NA",
                speechText,
                locData.getSTANDARD_RESPONSE1(),
                locData.getSTANDARD_RESPONSE2(),
                locData.getHELP_PRIMARY_TEXT(),
                locData.getHELP_DISPLAY_TEXT1(),
                locData.getHELP_DISPLAY_TEXT2(),
                "",
                locData.getAPP_TITLE());
    }

}
