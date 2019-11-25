package com.rodellison.conchrepublic.backend.model;

import java.util.Comparator;

public class EventItem {

    private String eventID;
    private String eventStartDate;
    private String eventEndDate;
    private String eventName;
    private String eventContact;
    private KeysLocations eventLocation;
    private String eventImgURL;
    private String eventURL;
    private String eventDescription;

    public static final Comparator<EventItem> BY_START_DATE =
            Comparator.comparing(EventItem::getEventStartDate);

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public void setEventStartAndEndDate(String strStartDate, String strEndDate) {
        this.eventStartDate = strStartDate;
        this.eventEndDate = strEndDate.isEmpty() ? strStartDate : strEndDate;
    }

    public String getEventStartDate() {
        return eventStartDate;
    }

    public String getEventEndDate() {
        return eventEndDate;
    }

    public String getEventName() {
        return eventName;
    }

    public String getEventContact() {
        return eventContact;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public String getEventURL() {
        return eventURL;
    }
    public String getEventImgURL() {
        return eventImgURL;
    }

    public KeysLocations getEventLocation() {
        return eventLocation;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public void setEventContact(String eventContact) {
        this.eventContact = eventContact;
    }

    public void setEventLocation(KeysLocations eventLocation) {
        this.eventLocation = eventLocation;
    }

    public void setEventImgURL(String eventImgURL) {
        this.eventImgURL = eventImgURL;
    }

    public void setEventURL(String eventURL) {
        this.eventURL = eventURL;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    //for testing
    public EventItem() {

    }

    @Override
    public String toString() {
        return "com.rodellison.alexa.backend.model.EventItem{" +
                "eventID=" + eventID +
                ", eventStartDate='" + eventStartDate + '\'' +
                ", eventEndDate='" + eventEndDate + '\'' +
                ", eventName='" + eventName + '\'' +
                ", eventContact='" + eventContact + '\'' +
                ", eventLocation='" + eventLocation + '\'' +
                ", eventImgURL='" + eventImgURL + '\'' +
                ", eventURL='" + eventURL + '\'' +
                ", eventDescription='" + eventDescription + '\'' +
                '}';
    }

}
