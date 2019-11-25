package com.rodellison.conchrepublic.backend.managers;

import com.rodellison.conchrepublic.backend.model.EventsList;

public interface DataBaseManagerInterface {
    Boolean insertEventDataIntoDB(EventsList theEventList);
}
