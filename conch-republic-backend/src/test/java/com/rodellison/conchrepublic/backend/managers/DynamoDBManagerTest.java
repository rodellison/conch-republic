package com.rodellison.conchrepublic.backend.managers;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.PutItemResult;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.rodellison.conchrepublic.backend.model.EventItem;
import com.rodellison.conchrepublic.backend.model.KeysLocations;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DisplayName("Dynamo DB Manager should ")
class DynamoDBManagerTest {

    @Test
    @DisplayName(" get an EventItem from the Dynamo DB")
    void getEventsDataForLocation() {

        AmazonDynamoDB mockADDB = mock(AmazonDynamoDB.class);
        DynamoDB mockDynamoDB = mock(DynamoDB.class);
        DynamoDBManager myDynamoDBManager = new DynamoDBManager(mockADDB, mockDynamoDB);

        ScanResult mockScanResult = mock(ScanResult.class);

        Map<String, AttributeValue> TestScanResults = new HashMap<>();
        TestScanResults.put("EventID", new AttributeValue().withS("calendar-0001"));
        TestScanResults.put("StartDate", new AttributeValue().withS("20200101"));
        TestScanResults.put("EndDate", new AttributeValue().withS("20200102"));
        TestScanResults.put("EventContact", new AttributeValue().withS(" "));
        TestScanResults.put("EventDescription", new AttributeValue().withS("Some Description"));
        TestScanResults.put("EventURL", new AttributeValue().withS(" "));
        TestScanResults.put("ImgURL", new AttributeValue().withS(" "));
        TestScanResults.put("EventName", new AttributeValue().withS("Some Name of the Event"));
        TestScanResults.put("EventLocation", new AttributeValue().withS("islamorada"));

        when(mockADDB.scan(any(ScanRequest.class)))
                .thenReturn(mockScanResult);

        List<Map<String, AttributeValue>> myScanResults = new ArrayList<>();
        myScanResults.addAll(Arrays.asList(TestScanResults));

        when(mockScanResult.getItems())
                .thenReturn(myScanResults);

        ArrayList<EventItem> myEventItems = myDynamoDBManager.getEventsDataForLocation("islamorada");
        assertEquals("calendar-0001", myEventItems.get(0).getEventID());


    }

    @Test
    @DisplayName(" get an EventItem from the Dynamo DB")
    void insertDataIntoDynamoDB() {

        AmazonDynamoDB mockADDB = mock(AmazonDynamoDB.class);
        DynamoDB mockDynamoDB = mock(DynamoDB.class);
        DynamoDBManager myDynamoDBManager = new DynamoDBManager(mockADDB, mockDynamoDB);

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

        Table mockTable = mock(Table.class);
        when (mockDynamoDB.getTable(any(String.class)))
                .thenReturn(mockTable);

        when (mockTable.putItem(any(Item.class)))
                .thenReturn(new PutItemOutcome(new PutItemResult()));

        Boolean result = myDynamoDBManager.insertEventDataIntoDB(myEvents);
        assertTrue(result);

    }
}