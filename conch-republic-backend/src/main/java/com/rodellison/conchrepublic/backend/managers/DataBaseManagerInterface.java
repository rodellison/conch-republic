package com.rodellison.conchrepublic.backend.managers;

import com.rodellison.conchrepublic.backend.model.EventItem;
import java.util.ArrayList;


public interface DataBaseManagerInterface {
    Boolean insertEventDataIntoDB(ArrayList<EventItem> theEventList);
}
