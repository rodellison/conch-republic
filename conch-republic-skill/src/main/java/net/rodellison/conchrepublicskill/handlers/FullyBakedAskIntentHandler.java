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
import net.rodellison.conchrepublicskill.util.DynamoDBManager;
import net.rodellison.conchrepublicskill.util.ListEventsResponseUtil;
import net.rodellison.conchrepublicskill.util.StandardResponseUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import net.rodellison.conchrepublicskill.util.DynamoDBClient;

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

        AttributesManager attributesManager = handlerInput.getAttributesManager();
        // Get the slots from the intent.
        Map<String, Object> attributes = attributesManager.getSessionAttributes();
        Optional<String> slotLocation = requestHelper.getSlotValue(LOCATION_SLOT);
        Optional<String> slotMonth = requestHelper.getSlotValue(MONTH_SLOT);

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
                return getFailResponse(handlerInput);
            }
        }
        if (!strTheMonth.equals(""))
        {
            if (!validMonthsList.contains(strTheMonth.toLowerCase()))
            {
                return getFailResponse(handlerInput);
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
            int maxEventsToKeep = 30;
            if (myFilteredEventList.size() > maxEventsToKeep)
                myFilteredEventList = myFilteredEventList.subList(0, maxEventsToKeep);

            return ListEventsResponseUtil.getResponse(handlerInput, 0, strTheMonth, strTheLocation, myFilteredEventList);

        } else
        {
            return getFailResponse(handlerInput);
        }
    }

    public Optional<Response> getFailResponse(HandlerInput handlerInput) {
        String speechText = "I had trouble understanding the location or month that you provided. Can you please try again. ";
        return StandardResponseUtil.getResponse(handlerInput,
                "Help",
                "What is happening in Key West in October?",
                "NA",
                speechText,
                "<p>Please try again by asking a question similar to one of these:</p>",
                "What's happening in Key West in October, or What's happening around Key Largo in May",
                "The Conch Republic Help",
                "Alexa, Ask The Conch Republic:",
                "What is happening in {Key Largo, Islamorada, Marathon, the Lower Keys, Key West}<br/>",
                "");
    }
}
