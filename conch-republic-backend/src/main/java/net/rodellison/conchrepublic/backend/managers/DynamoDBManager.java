package net.rodellison.conchrepublic.backend.managers;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import com.amazonaws.services.dynamodbv2.document.Table;

import com.amazonaws.services.dynamodbv2.document.spec.DeleteItemSpec;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import net.rodellison.conchrepublic.common.model.*;
import net.rodellison.conchrepublic.common.utils.DateUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.*;

public class DynamoDBManager implements DataBaseManager {

    private static final Logger log = LogManager.getLogger(DynamoDBManager.class);
    private AmazonDynamoDB client;
    private DynamoDB dynamoDB;

    public DynamoDBManager(AmazonDynamoDB theClient, DynamoDB dynamoDB)
    {
        this.client = theClient;
        this.dynamoDB = dynamoDB;
    }

    @Override
    public ArrayList<EventItem> getEventsDataForLocation(String location) {

        String strDataBaseTableName = System.getenv("DYNAMO_DB_TABLENAME");
        try {

            ArrayList<EventItem> eventListResults = new ArrayList<>();

            log.info("Attempting to get Event Data items from DynamoDB for location: " + location);

            Map<String, AttributeValue> expressionAttributeValues = new HashMap<>();
            expressionAttributeValues.put(":el", new AttributeValue().withS(location));

            ScanRequest scanRequest = new ScanRequest()
                    .withTableName(strDataBaseTableName)
                    .withFilterExpression("EventLocation = :el")
                    .withExpressionAttributeValues(expressionAttributeValues);

            ScanResult result = client.scan(scanRequest);

            for (Map<String, AttributeValue> item : result.getItems()) {

                EventItem thisEventItem = new EventItem();
                thisEventItem.setEventID(item.get("EventID").getS());
                thisEventItem.setEventStartAndEndDate(item.get("StartDate").getS(), item.get("EndDate").getS());
                thisEventItem.setEventContact(item.get("EventContact").getS());
                thisEventItem.setEventLocation(KeysLocations.convertToEnumLocation(item.get("EventLocation").getS()));
                thisEventItem.setEventURL(item.get("EventURL").getS());
                thisEventItem.setEventImgURL(item.get("ImgURL").getS());
                thisEventItem.setEventDescription(item.get("EventDescription").getS());
                thisEventItem.setEventName(item.get("EventName").getS());
                eventListResults.add(thisEventItem);
             }

            return eventListResults;

        } catch (Exception e) {
            log.error(strDataBaseTableName + " DynamoDB creation failed: " + e.getMessage());
            return null;

        }
     }

    @Override
    public Boolean insertEventDataIntoDB(ArrayList<EventItem> theEventList) {

        String strDataBaseTableName = System.getenv("DYNAMO_DB_TABLENAME");

        try {

            Table table = dynamoDB.getTable(strDataBaseTableName);
            log.info("Attempting to insert " + theEventList.size() + " Event Data items Into DynamoDB " + strDataBaseTableName);
            theEventList.forEach(eventItem -> {
                try {

                    Item item = new Item().withPrimaryKey("EventID", eventItem.getEventID())
                            .withString("StartDate", eventItem.getEventStartDate())
                            .withString("EndDate", eventItem.getEventEndDate())
                            .withString("EventName", eventItem.getEventName())
                            .withString("EventLocation", KeysLocations.getLocation(eventItem.getEventLocation()))
                            .withString("EventContact", eventItem.getEventContact())
                            .withString("EventDescription", eventItem.getEventDescription())
                            .withString("EventURL", eventItem.getEventURL())
                            .withString("ImgURL", eventItem.getEventImgURL());
                    table.putItem(item);

                } catch (Exception e) {
                    log.error(strDataBaseTableName + " PUT Item failed: " + e.getMessage());
                }
            });


        } catch (Exception e) {
            log.error(strDataBaseTableName + " DynamoDB creation failed: " + e.getMessage());
            return false;
        }
        return true;
    }

    private List<String> getEventIDsFromOldEvents(String endDate)
    {
        String strDataBaseTableName = System.getenv("DYNAMO_DB_TABLENAME");
        try {

            List<String> eventListResults = new ArrayList<>();

            log.info("Attempting to get EventIDs for items from DynamoDB that are no longer active.");

            Map<String, AttributeValue> expressionAttributeValues = new HashMap<>();
            expressionAttributeValues.put(":ed", new AttributeValue().withS(endDate));

            ScanRequest scanRequest = new ScanRequest()
                    .withTableName(strDataBaseTableName)
                    .withFilterExpression("EndDate < :ed")
                    .withExpressionAttributeValues(expressionAttributeValues);

            ScanResult result = client.scan(scanRequest);

            for (Map<String, AttributeValue> item : result.getItems()) {
                eventListResults.add(item.get("EventID").getS());
             }

            return eventListResults;

        } catch (Exception e) {
            log.error(strDataBaseTableName + " DynamoDB creation failed: " + e.getMessage());
            return null;

        }

    }

    public Boolean purgeOldEventDataIntoDB() {

        String strDataBaseTableName = System.getenv("DYNAMO_DB_TABLENAME");

        String strFormattedTodayDate = DateUtils.getFormattedTodayDate();

        List<String> eventsToPurge = getEventIDsFromOldEvents(strFormattedTodayDate);

        int itemsDeleted = 0;
        if (eventsToPurge.size() > 0)
        {
            try {
                Table table = dynamoDB.getTable(strDataBaseTableName);
                log.info("Attempting to delete Events that have ended from  " +  strDataBaseTableName);
                for (String eventID: eventsToPurge) {
                    DeleteItemSpec deleteItemSpec = new DeleteItemSpec()
                            .withPrimaryKey(new PrimaryKey("EventID", eventID));
                    try {

                        log.debug("Attempting to delete for item: " + eventID);
                        table.deleteItem(deleteItemSpec);
                        itemsDeleted++;

                    } catch (Exception e) {
                        log.error(strDataBaseTableName + " delete item failed: " + e.getMessage());
                    }

                }

            } catch (Exception e) {
                log.error(strDataBaseTableName + " DynamoDB delete item process failed: " + e.getMessage());
            }
        }
        log.info("purgeOldEventDataIntoDB item count deleted: " + itemsDeleted);
        return true;
    }


}
