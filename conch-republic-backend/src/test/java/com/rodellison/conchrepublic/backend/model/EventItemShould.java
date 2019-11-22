package com.rodellison.conchrepublic.backend.model;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
// Import log4j classes.
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

@DisplayName("The EventItem should")
class EventItemShould {

    private static final Logger logger = LogManager.getLogger(EventItemShould.class);
    private EventItem testEventItem = new EventItem();;


    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {

    }

    @Test
    @DisplayName("allow for the creation of an EventItem")
    void createAnEventItem()
    {
        testEventItem.setEventID("calendar-0001");
        testEventItem.setEventStartDate("20190101");
        testEventItem.setEventEndDate("20190102");
        testEventItem.setEventContact("Contact Name");
        testEventItem.setEventLocation(KeysLocations.KEY_WEST);
        testEventItem.setEventDescription("Test Description");
        testEventItem.setEventImgURL("http://someURL.com");
        testEventItem.setEventName("Test Event Name");
        assertTrue(testEventItem.toString().contains("20190101"), "Test to ensure successful creation of an Event Item");


    }


}