package net.rodellison.conchrepublic.backend.managers;

import net.rodellison.conchrepublic.common.model.EventItem;
import java.util.ArrayList;

//Creating this with the idea that may want to go with AWS RDB instead of Dynamo at some point.. depends which is cheaper..

public interface DataBaseManager {

    ArrayList<EventItem> getEventsDataForLocation(String location);
    Boolean insertEventDataIntoDB(ArrayList<EventItem> theEventList);
}
