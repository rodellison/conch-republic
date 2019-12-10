package net.rodellison.conchrepublicskill.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.Slot;
import net.rodellison.conchrepublic.common.model.EventItem;
import net.rodellison.conchrepublicskill.util.DynamoDBManager;
import net.rodellison.conchrepublicskill.util.EventsResponseUtil;
import net.rodellison.conchrepublicskill.util.StandardResponseUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import net.rodellison.conchrepublicskill.util.DynamoDBClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;


public class FullyBakedAskIntentHandler implements RequestHandler {

    private static final Logger log = LogManager.getLogger(FullyBakedAskIntentHandler.class);
    private static final String INTENT_NAME = "FullyBakedAskIntent";
    private static final String LOCATION_SLOT = "location";
    private static final String MONTH_SLOT = "month";


    @Override
    public boolean canHandle(HandlerInput input) {

        return input.matches(intentName("FullyBakedAskIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {

        IntentRequest thisRequest = (IntentRequest) input.getRequestEnvelope().getRequest();

        // Get the slots from the intent.
        Map<String, Object> attributes = input.getAttributesManager().getSessionAttributes();
        Map<String, Slot> slots = thisRequest.getIntent().getSlots();
        Slot slotLocation, slotMonth;
        ArrayList<String> events;
        int startingItem = 0;
        String strTheLocation, strTheMonth;

        log.warn("FullyBakedAskIntent called");

        try
        {
            slotLocation = slots.get(LOCATION_SLOT);
            strTheLocation = slotLocation.getValue().toLowerCase();
        }
        catch (Exception ex)
        {
            strTheLocation = "";
        }
        try
        {
            slotMonth = slots.get(MONTH_SLOT);
            strTheMonth = slotMonth.getValue().toLowerCase();
        }
        catch (Exception ex)
        {
            strTheMonth = "";
        }

        log.info("FullyBakedAskIntent values received.. Location Slot value: " + strTheLocation + ", Month Slot value: " + strTheMonth);

        DynamoDBManager myDynamoDBManager;
        myDynamoDBManager = new DynamoDBManager(DynamoDBClient.getDynamoDBClient());

        List<EventItem> myFilteredEventList = myDynamoDBManager.getCoreEventInfo(strTheLocation, strTheMonth);
        if (myFilteredEventList != null)
        {
            log.info("Filtered event count = " + myFilteredEventList.size());
            myFilteredEventList.forEach(eventItem -> {
                log.debug(eventItem.toString());
            });
        }

        //need to cap the max size of EventItems we're going to hang on to, so as to not blow out the session attributes limit
        int maxEventsToKeep = 30;
         if (myFilteredEventList.size() > maxEventsToKeep)
            myFilteredEventList = myFilteredEventList.subList(0,maxEventsToKeep);

        return EventsResponseUtil.getResponse(input, startingItem, strTheMonth, strTheLocation, myFilteredEventList);


    }
}
