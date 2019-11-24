package com.rodellison.conchrepublic.backend.managers;

import com.rodellison.conchrepublic.backend.model.EventItem;
import com.rodellison.conchrepublic.backend.model.KeysLocations;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("The Events List should")
class EventsListShould {

    EventsList testEventsList = new EventsList();

    @BeforeEach
    void setUp() {
        testEventsList.clearEvents();
    }

    @Test
    @DisplayName("return the list of Events that are currently active for a specific location for a specific month")
    void getListOfActiveEventsInMonthInLocation()
    {
        EventItem event1 = new EventItem();
        event1.setEventID("calendar-3333");
        event1.setEventStartAndEndDate("20200801", "20200820");
        event1.setEventName("Name of Event");
        event1.setEventLocation(KeysLocations.ALL_FLORIDA_KEYS);
        event1.setEventContact("Contact for Event");
        event1.setEventDescription("Long test description for event");
        testEventsList.addEventItem(event1);

        EventItem event2 = new EventItem();
        event2.setEventID("calendar-4444");
        event2.setEventStartAndEndDate("20200601", "20200620");
        event2.setEventName("Name of Event");
        event2.setEventLocation(KeysLocations.KEY_WEST);
        event2.setEventContact("Contact for Event");
        event2.setEventDescription("Long test description for event");
        testEventsList.addEventItem(event2);

        EventItem event3 = new EventItem();
        event3.setEventID("calendar-5555");
        event3.setEventStartAndEndDate("20200201", "20200201");
        event3.setEventName("Name of Event");
        event3.setEventLocation(KeysLocations.KEY_WEST);
        event3.setEventContact("Contact for Event");
        event3.setEventDescription("Long test description for event");
        testEventsList.addEventItem(event3);

        EventItem event4 = new EventItem();
        event4.setEventID("calendar-6676");
        event4.setEventStartAndEndDate("20200301", "20200301");
        event4.setEventName("Name of Event");
        event4.setEventLocation(KeysLocations.THE_LOWER_KEYS);
        event4.setEventContact("Contact for Event");
        event4.setEventDescription("Long test description for event");
        testEventsList.addEventItem(event4);

        EventItem event5 = new EventItem();
        event5.setEventID("calendar-8888");
        event5.setEventStartAndEndDate("20191001", "20200502");
        event5.setEventName("Ultra Long test Event");
        event5.setEventLocation(KeysLocations.MARATHON);
        event5.setEventContact("Contact for Event");
        event5.setEventDescription("Long test description for event");
        testEventsList.addEventItem(event5);

        assertAll(
                () -> {
                    final String strMM = "06";
                    final List<EventItem> theList = testEventsList.getListOfActiveEventsInMonthInLocation(strMM, KeysLocations.KEY_WEST);
                    assertNotNull(theList);
                    assertEquals(1, theList.size(), "Test 1 to validate active Events in a specific " +
                            "month for a specific location are returned (= 1)");
                },
                () -> {
                    final String strMM = "06";
                    final List<EventItem> theList = testEventsList.getListOfActiveEventsInMonthInLocation(strMM, KeysLocations.MARATHON);
                    assertNotNull(theList);
                    assertEquals(0, theList.size(), "Test 2 to ensure active Events for a month, but not for the specific" +
                            "location requested, return no items (= 0)");
                },
                () -> {
                    final String strMM = "08";
                    final List<EventItem> theList = testEventsList.getListOfActiveEventsInMonthInLocation(strMM, KeysLocations.MARATHON);
                    assertNotNull(theList);
                    assertEquals(1, theList.size(), "Test 3 to ensure active Events for a month, and specific location" +
                            "still returns an item that was listed as ALL Florida Keys no items (= 1)");
                }
        );

    }

    @Test
    @DisplayName("return the list of Events that are currently active for all locations for a specific month")
    void getListOfActiveEventsInMonth()
    {

        EventItem event2 = new EventItem();
        event2.setEventID("calendar-4444");
        event2.setEventStartAndEndDate("20200601", "20200620");
        event2.setEventName("Name of Event");
        event2.setEventLocation(KeysLocations.KEY_WEST);
        event2.setEventContact("Contact for Event");
        event2.setEventDescription("Long test description for event");
        testEventsList.addEventItem(event2);

        EventItem event3 = new EventItem();
        event3.setEventID("calendar-5555");
        event3.setEventStartAndEndDate("20200201", "20200201");
        event3.setEventName("Name of Event");
        event3.setEventLocation(KeysLocations.KEY_WEST);
        event3.setEventContact("Contact for Event");
        event3.setEventDescription("Long test description for event");
        testEventsList.addEventItem(event3);

        EventItem event4 = new EventItem();
        event4.setEventID("calendar-6676");
        event4.setEventStartAndEndDate("20200301", "20200301");
        event4.setEventName("Name of Event");
        event4.setEventLocation(KeysLocations.THE_LOWER_KEYS);
        event4.setEventContact("Contact for Event");
        event4.setEventDescription("Long test description for event");
        testEventsList.addEventItem(event4);

        EventItem event5 = new EventItem();
        event5.setEventID("calendar-8888");
        event5.setEventStartAndEndDate("20191001", "20200502");
        event5.setEventName("Ultra Long test Event");
        event5.setEventLocation(KeysLocations.MARATHON);
        event5.setEventContact("Contact for Event");
        event5.setEventDescription("Long test description for event");
        testEventsList.addEventItem(event5);

        assertAll(
                () -> {
                    final String strMM = "07";
                    final List<EventItem> theList = testEventsList.getListOfActiveEventsInMonth(strMM);
                    assertEquals(0, theList.size(), "Test 1 to validate empty list 0 items returned when no events for a month");
                },
                () -> {
                    final String strMM = "02";
                    final List<EventItem> theList = testEventsList.getListOfActiveEventsInMonth(strMM);
                    assertNotNull(theList);
                    assertEquals(2, theList.size(), "Test 2 to validate active Events in a specific month are returned (= 2)");
                },
                () -> {
                    final String strMM = "03";
                    final List<EventItem> theList = testEventsList.getListOfActiveEventsInMonth(strMM);
                    assertNotNull(theList);
                    assertEquals(2, theList.size(), "Test 3 to validate active Events in a specific month are returned (= 2)");
                },
                () -> {
                    final String strMM = "06";
                    final List<EventItem> theList = testEventsList.getListOfActiveEventsInMonth(strMM);
                    assertNotNull(theList);
                    assertEquals(1, theList.size(), "Test 4 to validate active Events in a specific month are returned (= 1)");
                }
        );

    }

    @Test
    @DisplayName("return the list of Events that are currently active for all of the Florida Keys")
    void getListOfActiveEvents() {

        EventItem event1 = new EventItem();
        event1.setEventID("calendar-0001");
        event1.setEventStartAndEndDate("20191101", "");
        event1.setEventName("Name of Event");
        event1.setEventLocation(KeysLocations.ISLAMORADA);
        event1.setEventContact("Contact for Event");
        event1.setEventDescription("Long test description for event");
        testEventsList.addEventItem(event1);

        EventItem event2 = new EventItem();
        event2.setEventID("calendar-9876");
        event2.setEventStartAndEndDate("20180101", "20180102");
        event2.setEventName("Name of Event");
        event2.setEventLocation(KeysLocations.KEY_LARGO);
        event2.setEventContact("Contact for Event");
        event2.setEventDescription("Long test description for event");
        testEventsList.addEventItem(event2);

        EventItem event3 = new EventItem();
        event3.setEventID("calendar-5555");
        event3.setEventStartAndEndDate("20181201", "20190102");
        event3.setEventName("Name of Event");
        event3.setEventLocation(KeysLocations.KEY_WEST);
        event3.setEventContact("Contact for Event");
        event3.setEventDescription("Long test description for event");
        testEventsList.addEventItem(event3);

        EventItem event4 = new EventItem();
        event4.setEventID("calendar-6676");
        event4.setEventStartAndEndDate("20180201", "20250202");
        event4.setEventName("Name of Event");
        event4.setEventLocation(KeysLocations.THE_LOWER_KEYS);
        event4.setEventContact("Contact for Event");
        event4.setEventDescription("Long test description for event");
        testEventsList.addEventItem(event4);

        EventItem event5 = new EventItem();
        event5.setEventID("calendar-8888");
        event5.setEventStartAndEndDate("20191001", "20251102");
        event5.setEventName("Ultra Long test Event");
        event5.setEventLocation(KeysLocations.MARATHON);
        event5.setEventContact("Contact for Event");
        event5.setEventDescription("Long test description for event");
        testEventsList.addEventItem(event5);

        List<EventItem> theList = testEventsList.getListOfActiveEvents();
        assertNotNull(theList);
        assertEquals(2, theList.size(), "Test to validate active Events are returned (= 2)");

    }

    @Test
    @DisplayName("return the list of Events that are currently active for a specific location")
    void getListOfActiveEventsInLocation() {

        EventItem event4 = new EventItem();
        event4.setEventID("calendar-6676");
        event4.setEventStartAndEndDate("20180201", "20250202");
        event4.setEventName("Name of Event");
        event4.setEventLocation(KeysLocations.THE_LOWER_KEYS);
        event4.setEventContact("Contact for Event");
        event4.setEventDescription("Long test description for event");
        testEventsList.addEventItem(event4);

        EventItem event5 = new EventItem();
        event5.setEventID("calendar-8888");
        event5.setEventStartAndEndDate("20191001", "20251102");
        event5.setEventName("Ultra Long test Event");
        event5.setEventLocation(KeysLocations.MARATHON);
        event5.setEventContact("Contact for Event");
        event5.setEventDescription("Long test description for event");
        testEventsList.addEventItem(event5);

        List<EventItem> theList = testEventsList.getListOfActiveEventsInLocation(KeysLocations.MARATHON);
        assertNotNull(theList);
        assertEquals(1, theList.size(), "Test to validate active Events for Marathon is returned (= 1)");

    }

    @Test
    @DisplayName("return the list of Events sorted Oldest to Newest")
    void getListOfEventsSortedByStartDate() {

        EventItem event1 = new EventItem();
        event1.setEventID("calendar-0001");
        event1.setEventStartAndEndDate("20191101", "");
        event1.setEventName("Name of Event");
        event1.setEventLocation(KeysLocations.ISLAMORADA);
        event1.setEventContact("Contact for Event");
        event1.setEventDescription("Long test description for event");
        testEventsList.addEventItem(event1);

        EventItem event2 = new EventItem();
        event2.setEventID("calendar-9876");
        event2.setEventStartAndEndDate("20180101", "20180102");
        event2.setEventName("Name of Event");
        event2.setEventLocation(KeysLocations.KEY_LARGO);
        event2.setEventContact("Contact for Event");
        event2.setEventDescription("Long test description for event");
        testEventsList.addEventItem(event2);

        EventItem event3 = new EventItem();
        event3.setEventID("calendar-5555");
        event3.setEventStartAndEndDate("20181201", "20190102");
        event3.setEventName("Name of Event");
        event3.setEventLocation(KeysLocations.KEY_WEST);
        event3.setEventContact("Contact for Event");
        event3.setEventDescription("Long test description for event");
        testEventsList.addEventItem(event3);

        EventItem event4 = new EventItem();
        event4.setEventID("calendar-6676");
        event4.setEventStartAndEndDate("20180201", "20250202");
        event4.setEventName("Name of Event");
        event4.setEventLocation(KeysLocations.THE_LOWER_KEYS);
        event4.setEventContact("Contact for Event");
        event4.setEventDescription("Long test description for event");
        testEventsList.addEventItem(event4);

        EventItem event5 = new EventItem();
        event5.setEventID("calendar-8888");
        event5.setEventStartAndEndDate("20191001", "20251102");
        event5.setEventName("Ultra Long test Event");
        event5.setEventLocation(KeysLocations.MARATHON);
        event5.setEventContact("Contact for Event");
        event5.setEventDescription("Long test description for event");
        testEventsList.addEventItem(event5);

        List<EventItem> theSortedList = testEventsList.getListOfEventsSortedByStartDate();
        assertNotNull(theSortedList);
        assertEquals("calendar-9876", theSortedList.get(0).getEventID(), "Test to validate that Events are sorted from oldest Start date to newest");

    }

    @Test
    @DisplayName("return an iterator for the List of Events")
    void getEventsListIterator() {

        EventItem event1 = new EventItem();
        event1.setEventID("calendar-0001");
        event1.setEventStartAndEndDate("20191101", "");
        event1.setEventName("Name of Event");
        event1.setEventLocation(KeysLocations.ISLAMORADA);
        event1.setEventContact("Contact for Event");
        event1.setEventDescription("Long test description for event");
        testEventsList.addEventItem(event1);

        EventItem event2 = new EventItem();
        event2.setEventID("calendar-9876");
        event2.setEventStartAndEndDate("20180101", "20180102");
        event2.setEventName("Name of Event");
        event2.setEventLocation(KeysLocations.KEY_LARGO);
        event2.setEventContact("Contact for Event");
        event2.setEventDescription("Long test description for event");
        testEventsList.addEventItem(event2);


        Iterator<EventItem> eventItemIterator = testEventsList.iterator();
        assertNotNull(eventItemIterator);
        assertTrue(eventItemIterator.hasNext(), "Test to ensure the EventsList returns an Iterator");


    }
}