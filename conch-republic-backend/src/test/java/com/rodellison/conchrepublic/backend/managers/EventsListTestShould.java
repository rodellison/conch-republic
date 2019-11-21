package com.rodellison.conchrepublic.backend.managers;

import com.rodellison.conchrepublic.backend.model.EventItem;
import com.rodellison.conchrepublic.backend.model.KeysLocations;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
// Import log4j classes.
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EventsListTestShould {

    private static final Logger logger = LogManager.getLogger(EventsListTestShould.class);
    EventsList testEventsList = new EventsList();


    @BeforeEach
    void setUp() {

        testEventsList.clearEvents();

        EventItem event1 = new EventItem();
        event1.setEventID("calendar-0001");
        event1.setEventStartDate("20191101");
        event1.setEventStartDate("20191102");
        event1.setEventName("Name of Event");
        event1.setEventLocation(KeysLocations.ISLAMORADA);
        event1.setEventContact("Contact for Event");
        event1.setEventDescription("Long test description for event");
        testEventsList.addEventItem(event1);

        EventItem event2 = new EventItem();
        event2.setEventID("calendar-9876");
        event2.setEventStartDate("20180101");
        event2.setEventStartDate("20180102");
        event2.setEventName("Name of Event");
        event2.setEventLocation(KeysLocations.KEY_LARGO);
        event2.setEventContact("Contact for Event");
        event2.setEventDescription("Long test description for event");
        testEventsList.addEventItem(event2);

        EventItem event3 = new EventItem();
        event3.setEventID("calendar-5555");
        event3.setEventStartDate("20181201");
        event3.setEventStartDate("20190102");
        event3.setEventName("Name of Event");
        event3.setEventLocation(KeysLocations.KEY_WEST);
        event3.setEventContact("Contact for Event");
        event3.setEventDescription("Long test description for event");
        testEventsList.addEventItem(event3);

        EventItem event4 = new EventItem();
        event4.setEventID("calendar-6676");
        event4.setEventStartDate("20180201");
        event4.setEventStartDate("20180202");
        event4.setEventName("Name of Event");
        event4.setEventLocation(KeysLocations.THE_LOWER_KEYS);
        event4.setEventContact("Contact for Event");
        event4.setEventDescription("Long test description for event");
        testEventsList.addEventItem(event4);

        EventItem event5 = new EventItem();
        event5.setEventID("calendar-8888");
        event5.setEventStartDate("20191001");
        event5.setEventStartDate("20191102");
        event5.setEventName("Name of Event");
        event5.setEventLocation(KeysLocations.MARATHON);
        event5.setEventContact("Contact for Event");
        event5.setEventDescription("Long test description for event");
        testEventsList.addEventItem(event5);


    }

    @AfterEach
    void tearDown() {

    }

    @Test
    void getListOfEventsSortedByStartDate() {

        List<EventItem> theSortedList = testEventsList.getListOfEventsSortedByStartDate();
        assertEquals(theSortedList.get(0).getEventID(), "calendar-9876");
        theSortedList.forEach((eventItem -> {
            logger.info(eventItem);
        }));

    }

    @Test
    void getEventsListIterator() {
        Iterator<EventItem> eventItemIterator = testEventsList.iterator();
        assertTrue(eventItemIterator.hasNext());
        while (eventItemIterator.hasNext())
        {
            EventItem thisItem = eventItemIterator.next();
            logger.info(thisItem);
        }

    }
}