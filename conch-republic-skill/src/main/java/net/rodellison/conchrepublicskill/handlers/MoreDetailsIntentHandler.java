package net.rodellison.conchrepublicskill.handlers;

import com.amazon.ask.attributes.AttributesManager;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.Slot;
import com.amazon.ask.request.RequestHelper;
import com.sun.org.apache.xpath.internal.operations.Bool;
import net.rodellison.conchrepublic.common.model.EventItem;
import net.rodellison.conchrepublicskill.models.LanguageLocalization;
import net.rodellison.conchrepublicskill.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;


public class MoreDetailsIntentHandler implements IntentRequestHandler {

    private static final Logger log = LogManager.getLogger(MoreDetailsIntentHandler.class);
    private static final String ITEM_ORDINAL_SLOT = "ItemOrdinal";

    @Override
    public boolean canHandle(HandlerInput handlerInput, IntentRequest intentRequest) {
        return handlerInput.matches(intentName("MoreDetailsIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput handlerInput, IntentRequest intentRequest) {
        RequestHelper requestHelper = RequestHelper.forHandlerInput(handlerInput);

        AttributesManager attributesManager = handlerInput.getAttributesManager();
        // Get the slots from the intent.
        Map <String,Object> attributes = attributesManager.getSessionAttributes();

        Optional<String> slotOrdinal = requestHelper.getSlotValue(ITEM_ORDINAL_SLOT);
        String strOrdinal = "";
        if (slotOrdinal.isPresent())
            strOrdinal = slotOrdinal.get();

        LanguageLocalization locData;
        String incomingLocale = intentRequest.getLocale();
        locData = CommonUtils.getLocalizationStrings(incomingLocale);
        if (locData == null) {
            log.error("Failed in getting language location data");
            return null;
        }

        log.info("MoreDetailsIntent values received.. Ordinal Slot value: " + slotOrdinal.get());
        String strEventID = "";
        Boolean ordinalInvalid = false;

        switch (slotOrdinal.get())
        {
            case "1":
                strEventID = (String) attributes.get("FIRST");
                break;
            case "2":
                strEventID = (String) attributes.get("SECOND");
                break;
            case "3":
                strEventID = (String) attributes.get("THIRD");
                break;
            default:
                ordinalInvalid = true;
        }

        log.info("Ordinal voiced: " + strOrdinal + ", value for matching attribute: " + strEventID);

         if (ordinalInvalid)
        {
            //We don't have a good id to use, need to throw an error message back to user
            return getResponse(handlerInput, locData);
        }
        else
        {

            DynamoDBManager myDynamoDBManager;
            myDynamoDBManager = new DynamoDBManager(DynamoDBClient.getDynamoDBClient());

            EventItem theEventItem = myDynamoDBManager.getEventItemInfo(strEventID);
            if (theEventItem != null)
            {
                log.debug(theEventItem);
                return EventItemResponseUtil.getResponse(handlerInput, theEventItem, locData);
            }
            else
            {
                return getResponse(handlerInput, locData);
            }
       }
    }

    public Optional<Response> getResponse(HandlerInput handlerInput, LanguageLocalization locData) {
        //Setting these items in case of error..
        String layoutToUse = "Help";
        String hintString = locData.getHINT1();
        String eventImgURL = "NA";
        String primaryTextDisplay = locData.getHELP_PRIMARY_TEXT();
        String speechText = locData.getEVENTDETAIL_ERROR_SPEECH1();
        String repromptSpeechText1 = locData.getEVENTDETAIL_ERROR_RESPONSE1();
        String repromptSpeechText2 = "";
        String Text1Display = locData.getEVENTDETAIL_ERROR_TEXT1();
        String Text2Display = "" ;
        String Text3Display = "";

        return StandardResponseUtil.getResponse(handlerInput,
                layoutToUse,
                hintString,
                eventImgURL,
                speechText,
                repromptSpeechText1,
                repromptSpeechText2,
                primaryTextDisplay,
                Text1Display,
                Text2Display,
                Text3Display,
                locData.getAPP_TITLE());
    }
}
