package com.rodellison.conchrepublic.backend.managers;

import com.rodellison.conchrepublic.backend.model.EventItem;
import com.rodellison.conchrepublic.backend.model.KeysLocations;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class EventsList implements Iterable<EventItem> {

    private List<EventItem> listOfEvents = new ArrayList<>();
    private static final Date today = new Date();
    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
    private static final String strYYYYMMDD = formatter.format(today);


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

    public int size()
    {
        return listOfEvents.size();
    }


    public void clearEvents()
    {
        listOfEvents.clear();
    }

    public List<EventItem> getListOfActiveEvents() {

        //filter 1
        Predicate<EventItem> eventHasNotEndedYet = e -> Integer.valueOf(strYYYYMMDD) <= Integer.valueOf(e.getEventEndDate());

        return getListOfEventsSortedByStartDate()
                .stream()
                .filter(eventHasNotEndedYet)
                .collect(Collectors.toList());

    }
    public List<EventItem> getListOfActiveEventsInLocation(KeysLocations thisLocation) {

        //filter 1
        Predicate<EventItem> eventHasNotEndedYet = e -> Integer.valueOf(strYYYYMMDD) <= Integer.valueOf(e.getEventEndDate());
        //filter 2
        Predicate<EventItem> eventInLocation = e -> e.getEventLocation().equals(thisLocation);

        return getListOfEventsSortedByStartDate()
                .stream()
                .filter(eventHasNotEndedYet.and(eventInLocation))
                .collect(Collectors.toList());

    }

    public List<EventItem> getListOfActiveEventsInMonth(String strMM) {
        //strYYYYMMDD contains today..
        int todaysMM = Integer.parseInt(strYYYYMMDD.substring(4,6));
        int searchYYYY = Integer.parseInt(strYYYYMMDD.substring(0,4));

        if (Integer.parseInt(strMM) < todaysMM)
            searchYYYY +=1;

        String searchStart = searchYYYY + strMM + "01";
        String searchEnd = searchYYYY + strMM + "31";

        //Keeping for now commented out, to look back on
//        List<EventItem> theEvents = new ArrayList<>();
//        for (EventItem thisItem: listOfEvents)
//        {
//          int startD = Integer.parseInt(thisItem.getEventStartDate());
//          int endD = Integer.parseInt(thisItem.getEventEndDate().substring(0,6) + "31");
//           if (Integer.parseInt(searchStart) >= startD
//                    && Integer.parseInt(searchEnd) <= endD)
//           {
//               theEvents.add(thisItem);
//           }
//        }

        Predicate<EventItem> eventActiveInMonthRequested =
                e -> Integer.parseInt(searchStart) >= Integer.parseInt(e.getEventStartDate())
                        && Integer.parseInt(searchEnd) <= Integer.parseInt(e.getEventEndDate().substring(0,6) + "31");

        return getListOfEventsSortedByStartDate()
                .stream()
                .filter(eventActiveInMonthRequested)
                .collect(Collectors.toList());

    }

    public List<EventItem> getListOfActiveEventsInMonthInLocation(String strMM, KeysLocations location) {
        //strYYYYMMDD contains today..
        int todaysMM = Integer.parseInt(strYYYYMMDD.substring(4,6));
        int searchYYYY = Integer.parseInt(strYYYYMMDD.substring(0,4));

        if (Integer.parseInt(strMM) < todaysMM)
            searchYYYY +=1;

        String searchStart = searchYYYY + strMM + "01";
        String searchEnd = searchYYYY + strMM + "31";

        Predicate<EventItem> eventActiveInMonthRequested =
                e -> Integer.parseInt(searchStart) >= Integer.parseInt(e.getEventStartDate())
                        && Integer.parseInt(searchEnd) <= Integer.parseInt(e.getEventEndDate().substring(0,6) + "31");

        Predicate<EventItem> eventHappeningInLocation =
                e -> e.getEventLocation().equals(location) || e.getEventLocation().equals(KeysLocations.ALL_FLORIDA_KEYS);

        return getListOfEventsSortedByStartDate()
                .stream()
                .filter(eventActiveInMonthRequested.and(eventHappeningInLocation))
                .collect(Collectors.toList());

    }
}
