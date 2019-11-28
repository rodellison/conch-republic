package com.rodellison.conchrepublic.backend.handlers;

import com.google.gson.Gson;
import com.rodellison.conchrepublic.backend.Services;
import com.rodellison.conchrepublic.backend.managers.WebEventsFormattingManager;
import com.rodellison.conchrepublic.backend.model.EventItem;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;


public class WebFormatterVerticle extends AbstractVerticle {
    private String thisContext;

    private static final Logger logger = LogManager.getLogger(WebFormatterVerticle.class);


    public LinkedHashMap<String, EventItem> handleReformatData(ArrayList<String> rawData) {

        logger.debug("\tWebFormatterVerticle " + thisContext + " getting search parms needed for WebEventsCollectionManager");

        WebEventsFormattingManager theWebEventsFormattingManager = new WebEventsFormattingManager();
        List<EventItem> theEventItemList = theWebEventsFormattingManager.convertRawHTMLToEventList(rawData);

        LinkedHashMap<String, EventItem> lhmRawCollectedData = new LinkedHashMap<>();

        theEventItemList.forEach(item ->
        {
            lhmRawCollectedData.put(item.getEventID(), item);
        });
        return lhmRawCollectedData;

    }

    @Override
    public void start(Promise<Void> startPromise) {
        final EventBus eventBus = vertx.eventBus();
        thisContext = context.toString();
        thisContext = thisContext.substring(thisContext.lastIndexOf("@") + 1);

        eventBus.consumer(Services.FORMATWEBDATA.toString(), message -> {
            // Do something with Vert.x async, reactive APIs

            JsonObject messageJson = new JsonObject(message.body().toString());
            String theMessagePathParm = messageJson.getValue("pathParameters").toString();

            LinkedHashMap<String, String> lhmResult = new Gson().fromJson(messageJson.getValue("collectedData").toString(), LinkedHashMap.class);
            ArrayList<String> theRawDataList = new ArrayList<>(lhmResult.values());

            logger.info("\tWebFormatterVerticle " + thisContext + " handling request to format raw Web Data");

            LinkedHashMap<String, EventItem> rawWebFormattingResults = handleReformatData(theRawDataList);

            logger.info("\tWebFormatterVerticle " + thisContext + " finished reformatting raw Web data");

            JsonObject results = new JsonObject();
            results.put("formattedData", JsonObject.mapFrom(rawWebFormattingResults));
            results.put("pathParameters", theMessagePathParm);
            message.reply(results.encode());

        });

        startPromise.complete();
    }

}

