package net.rodellison.conchrepublicskill.util;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.ScanFilter;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.ScanSpec;
import com.amazonaws.services.dynamodbv2.model.*;
import net.rodellison.conchrepublic.common.model.*;
import net.rodellison.conchrepublic.common.utils.DateUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class DynamoDBManager {

    private static final Logger log = LogManager.getLogger(DynamoDBManager.class);
    private static AmazonDynamoDB client;

    public DynamoDBManager(AmazonDynamoDB theClient) {
        this.client = theClient;
    }

    //Get events for a specific location
    public List<EventItem> getCoreEventInfo(String strLocation, String strMonth) {

        final String LOCATION_AND_MONTH = "EventLocation = :el and EndDate >= :ed";
        final String ALL_LOCATIONS = "EndDate >= :ed";

        String strDataBaseTableName = System.getenv("DYNAMO_DB_TABLENAME");
        try {

            EventsList eventListResults = new EventsList();

            log.info("Attempting to get Event Data items from DynamoDB for location: " + strLocation + " and month: " + strMonth);

            Map<String, AttributeValue> expressionAttributeValues = new HashMap<>();

            String filterToUse = ALL_LOCATIONS;
            String todaysDate = DateUtils.getFormattedTodayDate();
            log.info("Getting today's formatted date for EndDate comparison: " + todaysDate);
            expressionAttributeValues.put(":ed", new AttributeValue().withS(todaysDate));

            //Normalize the location input
            KeysLocations myKeysLocation = KeysLocations.convertToEnumLocation(strLocation);
            String theLocationToGet = KeysLocations.getLocation(myKeysLocation);
            log.info("theLocationToGet value: " + theLocationToGet);

            if (!strLocation.equals("")) {
                filterToUse = LOCATION_AND_MONTH;
                expressionAttributeValues.put(":el", new AttributeValue().withS(theLocationToGet));
            }

            int totalCountOfItems = 0;
            ScanRequest scanRequest = new ScanRequest()
                    .withTableName(strDataBaseTableName)
                    .withFilterExpression(filterToUse)
                    .withExpressionAttributeValues(expressionAttributeValues)
                    .withConsistentRead(false)
                    .withSelect(Select.ALL_ATTRIBUTES)
                    .withReturnConsumedCapacity(ReturnConsumedCapacity.NONE);
            ScanResult result = null;

            try
            {
                do {

                    if (result != null) {
                        scanRequest.setExclusiveStartKey(result.getLastEvaluatedKey());
                    }

                    result = client.scan(scanRequest);
                    log.info("Scan result scanned count: " + result.getScannedCount());
                    log.info("Scan result completed, total items found: " + result.getCount());
                    log.info("Scan result token info: " + result.getLastEvaluatedKey());

                    for (Map<String, AttributeValue> item : result.getItems()) {

                        EventItem thisEventItem = new EventItem();
                        thisEventItem.setEventID(item.get("EventID").getS());
                        thisEventItem.setEventStartAndEndDate(item.get("StartDate").getS(), item.get("EndDate").getS());
                        thisEventItem.setEventLocation(KeysLocations.convertToEnumLocation(item.get("EventLocation").getS()));
                        thisEventItem.setEventName(item.get("EventName").getS());

//These particular items won't be loaded now, as only the smaller core eventItem info will end up getting put into the session attributes for
//storing between sessions.  We'll fetch these items only when the detail for a specific event is requested..
//                thisEventItem.setEventURL(item.get("EventURL").getS());
//                thisEventItem.setEventImgURL(item.get("ImgURL").getS());
//                thisEventItem.setEventDescription(item.get("EventDescription").getS());

                        eventListResults.addEventItem(thisEventItem);
                        totalCountOfItems++;
                    }

                } while (result.getLastEvaluatedKey() != null);

            } catch (AmazonClientException ace)
            {
                log.error("DynamoDB Scan Exception: " + ace.getMessage());
            }


            if (totalCountOfItems > 0) {

               log.debug(eventListResults.toString());

               if (!strMonth.equals("") && !strLocation.equals(""))
                {
                    log.info("Returning EventList data for specific month and location: " + DateUtils.convertMonth(strMonth) + ", " + strLocation);
                    return eventListResults.getListOfActiveEventsInMonthInLocation(DateUtils.convertMonth(strMonth), KeysLocations.convertToEnumLocation(strLocation));
                }
                if (strMonth.equals("") && !strLocation.equals(""))
                {
                    log.info("Returning EventList data for all dates at a specific location");
                    return eventListResults.getListOfActiveEventsInLocation(KeysLocations.convertToEnumLocation(strLocation));
                }
                if (!strMonth.equals("") && strLocation.equals(""))
                {
                    log.info("Returning EventList data for specific month for all locations");
                    return eventListResults.getListOfActiveEventsInMonth(DateUtils.convertMonth(strMonth));
                }

                log.info("Returning EventList data for all future events for all locations");
                return eventListResults.getListOfEventsSortedByStartDate();
            }
            else {
                return null;
            }


        } catch (Exception e) {
            log.error(strDataBaseTableName + " DynamoDB creation failed: " + e.getMessage());
            return null;

        }
    }

}

/*
var params = {
    TableName: 'FloridaKeysEvents',
//    Limit: 0, // optional (limit the number of items to evaluate)
    FilterExpression: 'EndDate >= :ed', // a string representing a constraint on the attribute
//    ExpressionAttributeNames: { // a map of substitutions for attribute names with special characters
//        ":ed": {"S":"20191122"}
//    },
    ExpressionAttributeValues: { // a map of substitutions for all attribute values
        ':ed': {"S":"20191122"}
    },
    Select: 'ALL_ATTRIBUTES', // optional (ALL_ATTRIBUTES | ALL_PROJECTED_ATTRIBUTES |
                              //           SPECIFIC_ATTRIBUTES | COUNT)
//    AttributesToGet: [ // optional (list of specific attribute names to return)
//        'attribute_name',
        // ... more attributes ...
//    ],
    ConsistentRead: false, // optional (true | false)
//    Segment: 0, // optional (for parallel scan)
//    TotalSegments: 0, // optional (for parallel scan)
//    ExclusiveStartKey: { // optional (for pagination, returned by prior calls as LastEvaluatedKey)
//        attribute_name: attribute_value,
        // attribute_value (string | number | boolean | null | Binary | DynamoDBSet | Array | Object)
        // anotherKey: ...
//    },
    ReturnConsumedCapacity: 'NONE', // optional (NONE | TOTAL | INDEXES)
};
dynamodb.scan(params, function(err, data) {
    if (err) ppJson(err); // an error occurred
    else ppJson(data); // successful response
});

 */
