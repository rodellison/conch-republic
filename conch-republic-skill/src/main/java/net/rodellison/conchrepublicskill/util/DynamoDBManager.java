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

    //private static final Logger log = LogManager.getLogger(DynamoDBManager.class);
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

            System.out.println("INFO: Attempting to get Event Data items from DynamoDB for location: " + strLocation + " and month: " + strMonth);

            Map<String, AttributeValue> expressionAttributeValues = new HashMap<>();

            String filterToUse = ALL_LOCATIONS;
            String todaysDate = DateUtils.getFormattedTodayDate();
            System.out.println("INFO: Getting today's formatted date for EndDate comparison: " + todaysDate);
            expressionAttributeValues.put(":ed", new AttributeValue().withS(todaysDate));

            //Normalize the location input
            KeysLocations myKeysLocation = KeysLocations.convertToEnumLocation(strLocation);
            String theLocationToGet = KeysLocations.getLocation(myKeysLocation);
            System.out.println("INFO: theLocationToGet value: " + theLocationToGet);

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

            Boolean limitMaxResults = false;
            int intMaxResults = 64;

            //If BOTH month and location are blank, this is an open ended query so limit the actual DynamoDB items to be scanned
            //Initiated by user asking alexa, what's on the calendar
            if (strMonth.equals("") && strLocation.equals(""))
            {
                scanRequest.withLimit(intMaxResults);
                limitMaxResults = true;
            }


            try
            {
                do {

                    if (result != null) {
                        scanRequest.setExclusiveStartKey(result.getLastEvaluatedKey());
                    }

                    result = client.scan(scanRequest);
                    System.out.println("INFO: Scan result scanned count: " + result.getScannedCount());
                    System.out.println("INFO: Scan result completed, total items found: " + result.getCount());
                    System.out.println("INFO: Scan result token info: " + result.getLastEvaluatedKey());

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

                    if (limitMaxResults && (totalCountOfItems < intMaxResults))
                        break;

                } while (result.getLastEvaluatedKey() != null );

            } catch (AmazonClientException ace)
            {
                System.out.println("ERROR: DynamoDB Scan Exception: " + ace.getMessage());
            }


            if (totalCountOfItems > 0) {

               System.out.println("DEBUG: " + eventListResults.toString());

                if (strMonth.equals("") && strLocation.equals(""))
                {
                    System.out.println("INFO: Returning EventList data for all months and locations (open ended scan) ");
                    return eventListResults.getListOfEventsSortedByStartDate();
                }
               if (!strMonth.equals("") && !strLocation.equals(""))
                {
                    System.out.println("INFO: Returning EventList data for specific month and location: " + DateUtils.convertMonth(strMonth) + ", " + strLocation);
                    return eventListResults.getListOfActiveEventsInMonthInLocation(DateUtils.convertMonth(strMonth), KeysLocations.convertToEnumLocation(strLocation));
                }
                if (strMonth.equals("") && !strLocation.equals(""))
                {
                    System.out.println("INFO: Returning EventList data for all dates at a specific location");
                    return eventListResults.getListOfActiveEventsInLocation(KeysLocations.convertToEnumLocation(strLocation));
                }
                if (!strMonth.equals("") && strLocation.equals(""))
                {
                    System.out.println("INFO: Returning EventList data for specific month for all locations");
                    return eventListResults.getListOfActiveEventsInMonth(DateUtils.convertMonth(strMonth));
                }

                System.out.println("INFO: Returning EventList data for all future events for all locations");
                return eventListResults.getListOfEventsSortedByStartDate();
            }
            else {
                return null;
            }


        } catch (Exception e) {
            System.out.println("ERROR: " + strDataBaseTableName + " DynamoDB creation failed: " + e.getMessage());
            return null;

        }
    }

    public EventItem getEventItemInfo(String strEventID) {

        final String EVENT_ID_EXPRESSION = "EventID = :eID";

        String strDataBaseTableName = System.getenv("DYNAMO_DB_TABLENAME");

        System.out.println("INFO: Attempting to get Event Data item from DynamoDB for EventID: " + strEventID);

        Map<String, AttributeValue> expressionAttributeValues = new HashMap<>();
        expressionAttributeValues.put(":eID", new AttributeValue().withS(strEventID));

        QueryRequest queryRequest = new QueryRequest()
                .withTableName(strDataBaseTableName)
                .withKeyConditionExpression(EVENT_ID_EXPRESSION)
                .withExpressionAttributeValues(expressionAttributeValues)
                .withConsistentRead(false)
                .withSelect(Select.ALL_ATTRIBUTES)
                .withReturnConsumedCapacity(ReturnConsumedCapacity.NONE);
        QueryResult queryResult = null;

        try {
            queryResult = client.query(queryRequest);
            if (queryResult != null) {
                System.out.println("INFO: Query item result count: " + queryResult.getCount());
                List<Map<String, AttributeValue>> items = queryResult.getItems();
                //There SHOULD only be one resulting item fetched.. if more, then just use first..

                EventItem thisEventItem = new EventItem();
                thisEventItem.setEventID(items.get(0).get("EventID").getS());
                thisEventItem.setEventStartAndEndDate(items.get(0).get("StartDate").getS(), items.get(0).get("EndDate").getS());
                thisEventItem.setEventLocation(KeysLocations.convertToEnumLocation(items.get(0).get("EventLocation").getS()));
                thisEventItem.setEventName(items.get(0).get("EventName").getS());
                thisEventItem.setEventURL(items.get(0).get("EventURL").getS());
                thisEventItem.setEventImgURL(items.get(0).get("ImgURL").getS());
                thisEventItem.setEventDescription(items.get(0).get("EventDescription").getS());

                return thisEventItem;

            }

        } catch (AmazonClientException ace) {
            System.out.println("ERROR: DynamoDB Query Exception: " + ace.getMessage());

        }
        return null;
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


var params = {
    TableName: 'FloridaKeysEvents',
//    IndexName: 'index_name', // optional (if querying an index)
    KeyConditionExpression: 'EventID = :eID', // a string representing a constraint on the attribute
//    FilterExpression: 'attr_name = :val', // a string representing a constraint on the attribute
//    ExpressionAttributeNames: { // a map of substitutions for attribute names with special characters
        //'#name': 'attribute name'
//    },
    ExpressionAttributeValues: { // a map of substitutions for all attribute values
      ':eID': 'calendar-2722'
    },
    ScanIndexForward: true, // optional (true | false) defines direction of Query in the index
//    Limit: 0, // optional (limit the number of items to evaluate)
    ConsistentRead: false, // optional (true | false)
    Select: 'ALL_ATTRIBUTES', // optional (ALL_ATTRIBUTES | ALL_PROJECTED_ATTRIBUTES |
                              //           SPECIFIC_ATTRIBUTES | COUNT)
//    AttributesToGet: [ // optional (list of specific attribute names to return)
//        'EventURL',
//        'EventDescription',
//        'ImgURL',
        // ... more attributes ...
//    ],
//    ExclusiveStartKey: { // optional (for pagination, returned by prior calls as LastEvaluatedKey)
//        attribute_name: attribute_value,
        // attribute_value (string | number | boolean | null | Binary | DynamoDBSet | Array | Object)
        // anotherKey: ...

//    },
    ReturnConsumedCapacity: 'NONE', // optional (NONE | TOTAL | INDEXES)
};
docClient.query(params, function(err, data) {
    if (err) ppJson(err); // an error occurred
    else ppJson(data); // successful response
});

 */
