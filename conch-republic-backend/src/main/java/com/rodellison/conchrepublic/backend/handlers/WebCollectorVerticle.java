package com.rodellison.conchrepublic.backend.handlers;

import com.rodellison.conchrepublic.backend.managers.WebEventsCollectionManager;
import com.rodellison.conchrepublic.backend.utils.DataFetchUtil;
import com.rodellison.conchrepublic.backend.utils.ExternalAPIFetchUtil;
import com.rodellison.conchrepublic.backend.utils.SearchDateUtil;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rodellison.conchrepublic.backend.Services;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class WebCollectorVerticle extends AbstractVerticle {
    private String thisContext;

    private static final Logger logger = LogManager.getLogger(WebCollectorVerticle.class);


    public LinkedHashMap<String, String> handleCollectionOfWebData() {

        logger.debug("\tWebCollectorVerticle " + thisContext + " getting search parms needed for WebEventsCollectionManager");
        ArrayList<String> theSearchDateParms;
        String monthsToSearch = System.getenv("MONTHS_TO_FETCH");
        logger.info("WebCollectorVerticle intending to process " + monthsToSearch + " months worth of data");
        theSearchDateParms = SearchDateUtil.getSearchDates(Integer.valueOf(monthsToSearch));

        ExternalAPIFetchUtil theFetchUtility = new DataFetchUtil();
        WebEventsCollectionManager theWebEventCollectionsManager;
        theWebEventCollectionsManager = new WebEventsCollectionManager(theFetchUtility);

        //This call will take a while
        logger.debug("\tWebCollectorVerticle " + thisContext + " performing long running collectEventsForSearchDates");
        ArrayList<String> rawHTMLData;
        rawHTMLData = theWebEventCollectionsManager.collectEventsForSeachDates(theSearchDateParms);
        LinkedHashMap<String, String> lhmRawCollectedData = new LinkedHashMap<>();

        AtomicInteger counter = new AtomicInteger();
        rawHTMLData.forEach(item ->
        {
            lhmRawCollectedData.put(String.valueOf(counter.getAndIncrement()), item);
        });
        return lhmRawCollectedData;

    }

    @Override
    public void start(Promise<Void> startPromise) {
        final EventBus eventBus = vertx.eventBus();
        thisContext = context.toString();
        thisContext = thisContext.substring(thisContext.lastIndexOf("@") + 1);

        eventBus.consumer(Services.COLLECTWEBDATA.toString(), message -> {
            // Do something with Vert.x async, reactive APIs

            logger.info("\tWebCollectorVerticle " + thisContext + " handling request to collect Web Data");

            LinkedHashMap<String, String> rawWebCollectionResults = handleCollectionOfWebData();

            logger.info("\tWebCollectorVerticle " + thisContext + " finished collecting Web data");

            JsonObject messageToSend;
            messageToSend = JsonObject.mapFrom(rawWebCollectionResults);

            message.reply(messageToSend.encode());

        });

        startPromise.complete();
    }

}

