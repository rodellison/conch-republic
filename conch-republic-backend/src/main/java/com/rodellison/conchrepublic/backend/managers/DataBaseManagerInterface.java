package com.rodellison.conchrepublic.backend.managers;

import com.rodellison.conchrepublic.backend.model.EventItem;
import java.util.ArrayList;


public interface DataBaseManagerInterface {

    ArrayList<EventItem> getEventsDataForLocation(String location);
    Boolean insertEventDataIntoDB(ArrayList<EventItem> theEventList);
}
