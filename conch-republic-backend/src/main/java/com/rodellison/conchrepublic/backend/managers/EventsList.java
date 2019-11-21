package com.rodellison.conchrepublic.backend.managers;

import com.rodellison.conchrepublic.backend.model.EventItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class EventsList implements Iterable<EventItem> {

    private List<EventItem> listOfEvents = new ArrayList<>();

    public List<EventItem> getListOfEventsSortedByStartDate()
    {
        Collections.sort(listOfEvents, EventItem.BY_START_DATE);
        return listOfEvents;
    }

    public void addEventItem (EventItem thisItem)
    {
        listOfEvents.add(thisItem);
    }

    @Override
    public Iterator<EventItem> iterator() {
        return listOfEvents.iterator();

    }

    public void clearEvents()
    {
        listOfEvents.clear();
    }

}
