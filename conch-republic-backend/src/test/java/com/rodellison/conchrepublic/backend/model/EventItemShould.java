package com.rodellison.conchrepublic.backend.model;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;


@DisplayName("The EventItem should")
class EventItemShould {

    private EventItem testEventItem = new EventItem();

    @Test
    @DisplayName("allow for the creation of an EventItem")
    void createAnEventItem()
    {
        testEventItem.setEventID("calendar-0001");
        testEventItem.setEventStartAndEndDate("20190101", "");
        testEventItem.setEventContact("Contact Name");
        testEventItem.setEventLocation(KeysLocations.KEY_WEST);
        testEventItem.setEventDescription("Test Description");
        testEventItem.setEventImgURL("http://someURL.com");
        testEventItem.setEventName("Test Event Name");
        assertTrue(testEventItem.toString().contains("20190101"), "Test to ensure successful creation of an Event Item");
        assertEquals("20190101", testEventItem.getEventEndDate(), "Test to ensure events with only a start " +
                "date ensure end date has the same value");


    }


}