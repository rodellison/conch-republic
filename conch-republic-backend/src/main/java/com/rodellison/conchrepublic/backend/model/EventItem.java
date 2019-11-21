package com.rodellison.conchrepublic.backend.model;

import java.util.Comparator;

public class EventItem {

    private  String eventID;
    private  String eventStartDate;
    private  String eventEndDate;
    private  String eventName;
    private  String eventContact;
    private  KeysLocations eventLocation;
    private  String eventImgURL;
    private  String eventDescription;

    public static final Comparator<EventItem> BY_START_DATE =
            Comparator.comparing(EventItem::getEventStartDate);

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public void setEventStartDate(String eventStartDate) {
        this.eventStartDate = eventStartDate;
    }

    public String getEventStartDate() {
        return eventStartDate;
    }


    public void setEventEndDate(String eventEndDate) {
        this.eventEndDate = eventEndDate;
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

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    //for testing
    public EventItem ()
    {

    }

    //May not have Contact or Image
    public EventItem(String eventID, String eventStartDate, String eventEndDate, String eventName, KeysLocations eventLocation, String eventDescription) {
        this.eventID = eventID;
        this.eventStartDate = eventStartDate;
        this.eventEndDate = eventEndDate;
        this.eventName = eventName;
        this.eventLocation = eventLocation;
        this.eventDescription = eventDescription;

        this.eventImgURL = "";
        this.eventContact = "";
    }

    public EventItem(String eventID, String eventStartDate, String eventEndDate, String eventName, String eventContact, KeysLocations eventLocation, String eventImgURL, String eventDescription) {
        this.eventID = eventID;
        this.eventStartDate = eventStartDate;
        this.eventEndDate = eventEndDate;
        this.eventName = eventName;
        this.eventContact = eventContact;
        this.eventLocation = eventLocation;
        this.eventImgURL = eventImgURL;
        this.eventDescription = eventDescription;
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
                ", eventDescription='" + eventDescription + '\'' +
                '}';
    }



}
