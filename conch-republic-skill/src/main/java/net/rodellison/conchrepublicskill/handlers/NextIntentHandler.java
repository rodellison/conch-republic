package net.rodellison.conchrepublicskill.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.rodellison.conchrepublic.common.model.EventItem;
import net.rodellison.conchrepublicskill.models.LanguageLocalization;
import net.rodellison.conchrepublicskill.util.CommonUtils;
import net.rodellison.conchrepublicskill.util.ListEventsResponseUtil;
import net.rodellison.conchrepublicskill.util.StandardResponseUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;

public class NextIntentHandler implements RequestHandler {

    private static final String INTENT_NAME = "NextIntent";
    //private static final Logger log = LogManager.getLogger(NextIntentHandler.class);

    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(intentName("AMAZON.NextIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {

        System.out.println("WARN: NextIntentHandler called");
        Map<String, Object> attributes = input.getAttributesManager().getSessionAttributes();

        LanguageLocalization locData;
        String incomingLocale = input.getRequestEnvelope().getRequest().getLocale();
        locData = CommonUtils.getLocalizationStrings(incomingLocale);
        if (locData == null) {
            System.out.println("ERROR: Failed in getting language location data");
            return null;
        }

        String strTheLocation = (String) attributes.get("STR_LOCATION");
        if (strTheLocation == null || strTheLocation.isEmpty())
            strTheLocation = "";
        String strTheMonth = (String) attributes.get("STR_MONTH");
        if (strTheMonth == null || strTheMonth.isEmpty())
            strTheMonth = "";
        int startItem = (int) attributes.get("INT_STARTITEM_VAL");

        try {
            List<EventItem> eventItemsList;
            Gson gson = new Gson();
            Type listEventItemsType = new TypeToken<List<EventItem>>() {
            }.getType();
            eventItemsList = gson.fromJson(attributes.get("EVENT_ITEMS").toString(), listEventItemsType);
            System.out.println("DEBUG: In NextIntent handler, eventItemsList= " + eventItemsList);
            return ListEventsResponseUtil.getResponse(input, startItem, strTheMonth, strTheLocation, eventItemsList, locData);

        } catch (Exception e) {
            //This is for graceful exit if tripped up trying to get attribute data for loop
            e.printStackTrace();
            String layoutToUse = "Home";
            String hintString = locData.getHINT1();
            String eventImgURL = "NA";
            String primaryTextDisplay = locData.getLAUNCH_TITLE();

            String speechText = locData.getNEXT_ERROR_SPEECH1();
            String repromptSpeechText1 = locData.getSTANDARD_RESPONSE1();
            String repromptSpeechText2 = locData.getSTANDARD_RESPONSE2();
            String Text1Display = "";
            String Text2Display = "";
            String Text3Display = "";

            return StandardResponseUtil.getResponse(input, layoutToUse, hintString, eventImgURL, speechText, repromptSpeechText1, repromptSpeechText2,
                    primaryTextDisplay, Text1Display, Text2Display, Text3Display, locData.getAPP_TITLE());
        }

    }

}
