package com.rodellison.conchrepublic.backend.managers;

import com.rodellison.conchrepublic.backend.model.EventsList;
import com.rodellison.conchrepublic.backend.model.EventItem;
import com.rodellison.conchrepublic.backend.model.KeysLocations;
import com.rodellison.conchrepublic.backend.utils.DynamoDBClient;
import com.rodellison.conchrepublic.backend.utils.DynamoDBClientTestDouble;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("DataBase Manager should")
public class DataBaseManagerShould {

    private static final Logger log = LogManager.getLogger(DataBaseManagerShould.class);

    @Test
    @DisplayName("insert an EventItem into the DataBase Table")
    void insertEventItemIntoDynamoDB() {

        DynamoDBClient myDynamoClient = new DynamoDBClient();
        DataBaseManager myDataBaseManager = new DataBaseManager(new DynamoDBManager(myDynamoClient.getDynamoDBClient()));

        ArrayList<EventItem> myEvents = new ArrayList<>();

        EventItem event1 = new EventItem();
        event1.setEventID("calendar-3333");
        event1.setEventStartAndEndDate("20200801", "20200820");
        event1.setEventName("Name of Event 1");
        event1.setEventLocation(KeysLocations.ALL_FLORIDA_KEYS);
        event1.setEventContact("Contact for Event");
        event1.setEventURL("http://someURL.com");
        event1.setEventImgURL("http://someImgURL.com");
        event1.setEventContact("Contact for Event");
        event1.setEventDescription("Long test description for event 1");
        myEvents.add(event1);

        EventItem event2 = new EventItem();
        event2.setEventID("calendar-4444");
        event2.setEventStartAndEndDate("20200601", "20200620");
        event2.setEventName("Name of Event 2");
        event2.setEventLocation(KeysLocations.KEY_WEST);
        event2.setEventContact("Contact for Event");
        event2.setEventURL("http://someURL.com");
        event2.setEventImgURL("http://someImgURL.com");
        event2.setEventDescription("Long test description for event 2");
        myEvents.add(event2);

        Boolean result = myDataBaseManager.insertEventDataIntoDB(myEvents);

        assertTrue(result);

    }


}
