package com.rodellison.conchrepublic.backend.handlers;


import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.rodellison.conchrepublic.backend.managers.DynamoDBManager;
import com.rodellison.conchrepublic.backend.model.EventItem;

import com.rodellison.conchrepublic.backend.utils.DynamoDBClient;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.rodellison.conchrepublic.backend.Services;

import java.io.IOException;
import java.util.*;

// Import log4j classes.

public class DataBaseVerticle extends AbstractVerticle {
    private String thisContext;

    private static final Logger logger = LogManager.getLogger(DataBaseVerticle.class);

    public void insertData(ArrayList<EventItem> itemsToInsert) {

        DynamoDBClient myClient = new DynamoDBClient();
        DynamoDB myDynamoDB = new DynamoDB(myClient.getDynamoDBClient());
        DynamoDBManager myDynamoDBManager = new DynamoDBManager(myClient.getDynamoDBClient(), myDynamoDB);

        myDynamoDBManager.insertEventDataIntoDB(itemsToInsert);

    }
    public ArrayList<EventItem> getData(String location) {

        DynamoDBClient myClient = new DynamoDBClient();
        DynamoDB myDynamoDB = new DynamoDB(myClient.getDynamoDBClient());
        DynamoDBManager myDynamoDBManager = new DynamoDBManager(myClient.getDynamoDBClient(), myDynamoDB);

        return myDynamoDBManager.getEventsDataForLocation(location);

    }

    @Override
    public void start(Promise<Void> startPromise) {
        final EventBus eventBus = vertx.eventBus();


        thisContext = context.toString();
        thisContext = thisContext.substring(thisContext.lastIndexOf("@") + 1);

        eventBus.consumer(Services.GETDBDATA.toString(), message -> {
            // Do something with Vert.x async, reactive APIs

            ArrayList<EventItem> result = new ArrayList<>();
            JsonObject fetchMessage = JsonObject.mapFrom(message.body());
            String theMessagePathParm = fetchMessage.getValue("pathParameters").toString();
            JsonObject segmentObject = new JsonObject(theMessagePathParm);
            theMessagePathParm = segmentObject.getValue("location").toString();

            logger.info("DBHandlerVerticle " + thisContext +  " received Get request for location: " + theMessagePathParm);
            result = getData(theMessagePathParm);
            String jsonResult = new Gson().toJson(result);
            logger.info("DBHandlerVerticle " + thisContext +  " processed Get request for location: " + theMessagePathParm);

            final Map<String, Object> response = new HashMap<>();
            response.put("statusCode", 200);
            response.put("body", jsonResult);
            message.reply(new JsonObject(response).encode());
        });

        eventBus.consumer(Services.INSERTDBDATA.toString(), message -> {
            // Do something with Vert.x async, reactive APIs

            JsonObject messageJson = JsonObject.mapFrom(message.body());
            String theMessagePathParm = messageJson.getValue("pathParameters").toString();
            JsonObject formattedResultsJson = JsonObject.mapFrom(messageJson.getValue("formattedData"));

            //----- This section to convert from LinkedHashMap entries that are riding through the event bus
            //back into an actual ArrayList<EventItem>
            ArrayList<EventItem> convertedEventItems = new ArrayList<>();
            Set<String> myKeySet = formattedResultsJson.getMap().keySet();

            Iterator<String> itr = myKeySet.iterator();
            while (itr.hasNext())
            {
                String thisItem = formattedResultsJson.getValue(itr.next()).toString();
                //create ObjectMapper instance
                ObjectMapper objectMapper = new ObjectMapper();
                //convert json string to object
                try {
                    EventItem eventItem = objectMapper.readValue(thisItem, EventItem.class);
                    convertedEventItems.add(eventItem);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //----- This section to convert from LinkedHashMap entries that are riding through the event bus

            logger.info("DataBaseVerticle " + thisContext + " received insert request");
            insertData(convertedEventItems);
            logger.info("DBHandlerVerticle "  + thisContext +  " completed insert request ");

            final Map<String, Object> response = new HashMap<>();
            response.put("pathParameters", theMessagePathParm);
            response.put("body", "Database insert completed.");
            message.reply(new JsonObject(response));

        });

        startPromise.complete();
    }

}

