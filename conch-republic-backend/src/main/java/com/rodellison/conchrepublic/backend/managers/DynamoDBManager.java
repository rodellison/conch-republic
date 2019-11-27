package com.rodellison.conchrepublic.backend.managers;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;

import com.rodellison.conchrepublic.backend.model.EventItem;

import com.rodellison.conchrepublic.backend.model.KeysLocations;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.*;

public class DynamoDBManager implements DataBaseManagerInterface {

    private static final Logger log = LogManager.getLogger(DynamoDBManager.class);
    private AmazonDynamoDB client;

    public DynamoDBManager(AmazonDynamoDB theClient) {
        this.client = theClient;
    }


    @Override
    public Boolean insertEventDataIntoDB(ArrayList<EventItem> theEventList) {

        String strDataBaseTableName = System.getenv("DYNAMO_DB_TABLENAME");

        try {

            DynamoDB dynamoDB = new DynamoDB(client);

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

        }
        return true;
    }

}
