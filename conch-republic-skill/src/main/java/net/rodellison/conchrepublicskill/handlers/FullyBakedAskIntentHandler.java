package net.rodellison.conchrepublicskill.handlers;

import com.amazon.ask.attributes.AttributesManager;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.Slot;
import com.amazon.ask.request.RequestHelper;
import net.rodellison.conchrepublic.common.model.EventItem;
import net.rodellison.conchrepublic.common.model.KeysLocations;
import net.rodellison.conchrepublicskill.models.LanguageLocalization;
import net.rodellison.conchrepublicskill.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;


public class FullyBakedAskIntentHandler implements IntentRequestHandler {

    private static final Logger log = LogManager.getLogger(FullyBakedAskIntentHandler.class);
    private static final String INTENT_NAME = "FullyBakedAskIntent";
    private static final String LOCATION_SLOT = "location";
    private static final String MONTH_SLOT = "month";
    private static final String[] validlocations = {"key largo", "islamorada", "marathon", "lower keys", "the lower keys", "key west"};
    private static final String[] validMonths = {"january", "enero", "february", "febrero", "march", "marzo", "april", "abril", "may", "mayo",
            "june", "junio", "july", "julio", "august", "agosto", "september", "septiembre", "october", "octubre", "november", "noviembre", "december", "diciembre"};

    private static final List<String> validLocationsList = Arrays.asList(validlocations);
    private static final List<String> validMonthsList = Arrays.asList(validMonths);

    @Override
    public boolean canHandle(HandlerInput handlerInput, IntentRequest intentRequest) {
        return handlerInput.matches(intentName(INTENT_NAME));
    }

    @Override
    public Optional<Response> handle(HandlerInput handlerInput, IntentRequest intentRequest) {

        RequestHelper requestHelper = RequestHelper.forHandlerInput(handlerInput);

        Optional<String> slotLocation = requestHelper.getSlotValue(LOCATION_SLOT);
        Optional<String> slotMonth = requestHelper.getSlotValue(MONTH_SLOT);

        LanguageLocalization locData;
        String incomingLocale = intentRequest.getLocale();
        locData = CommonUtils.getLocalizationStrings(incomingLocale);
        if (locData == null) {
            log.error("Failed in getting language location data");
            return null;
        }


        String strTheLocation = "";
        String strTheMonth = "";

        if (slotLocation.isPresent())
            strTheLocation = slotLocation.get();
        if (slotMonth.isPresent())
            strTheMonth = slotMonth.get();

        log.info("FullyBakedAskIntent values received.. Location Slot value: " + strTheLocation + ", Month Slot value: " + strTheMonth);

        if (!strTheLocation.equals(""))
        {
             if (!validLocationsList.contains(strTheLocation.toLowerCase()))
            {
                return getFailResponse(handlerInput, locData);
            }
        }
        if (!strTheMonth.equals(""))
        {
            if (!validMonthsList.contains(strTheMonth.toLowerCase()))
            {
                return getFailResponse(handlerInput, locData);
            }
        }

        DynamoDBManager myDynamoDBManager;
        myDynamoDBManager = new DynamoDBManager(DynamoDBClient.getDynamoDBClient());

        List<EventItem> myFilteredEventList = myDynamoDBManager.getCoreEventInfo(strTheLocation, strTheMonth);
        if (myFilteredEventList != null) {
            log.info("Filtered event count = " + myFilteredEventList.size());
            myFilteredEventList.forEach(eventItem -> {
                log.debug(eventItem.toString());
            });

            //need to cap the max size of EventItems we're going to hang on to, so as to not blow out the session attributes limit
            int maxEventsToKeep = 27;
            if (myFilteredEventList.size() > maxEventsToKeep)
                myFilteredEventList = myFilteredEventList.subList(0, maxEventsToKeep);

            return ListEventsResponseUtil.getResponse(handlerInput, 0, strTheMonth, strTheLocation, myFilteredEventList, locData);

        } else
        {
            return getFailResponse(handlerInput, locData);
        }
    }

    public Optional<Response> getFailResponse(HandlerInput handlerInput, LanguageLocalization locData) {

        return StandardResponseUtil.getResponse(handlerInput,
                "Help",
                locData.getHINT1(),
                "NA",
                locData.getFALLBACK_SPEECH1(),
                locData.getSTANDARD_RESPONSE1(),
                locData.getSTANDARD_RESPONSE2(),
                locData.getHELP_PRIMARY_TEXT(),
                locData.getHELP_DISPLAY_TEXT1(),
                locData.getHELP_DISPLAY_TEXT2(),
                "",
                locData.getAPP_TITLE());
    }
}
