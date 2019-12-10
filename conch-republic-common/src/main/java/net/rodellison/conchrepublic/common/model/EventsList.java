package net.rodellison.conchrepublic.common.model;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;


public class EventsList implements Iterable<EventItem> {

    private List<EventItem> listOfEvents = new ArrayList<>();
    private static final Date today = new Date();
    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
    private static final String strYYYYMMDD = formatter.format(today);

    public List<EventItem> getListOfEventsSortedByStartDate()
    {
        listOfEvents.sort(EventItem.BY_START_DATE);
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
                e -> (Integer.valueOf(e.getEventEndDate()) >= Integer.valueOf(searchStart) && Integer.valueOf(e.getEventStartDate()) <= Integer.valueOf(searchEnd));

        return getListOfEventsSortedByStartDate()
                .stream()
                .filter(eventActiveInMonthRequested)
                .collect(Collectors.toList());

    }

    public List<EventItem> getListOfActiveEventsInMonthInLocation(String strMM, KeysLocations location) {

        int todaysMM = Integer.parseInt(strYYYYMMDD.substring(4,6));
        int searchYYYY = Integer.parseInt(strYYYYMMDD.substring(0,4));

        if (Integer.parseInt(strMM) < todaysMM)
            searchYYYY +=1;

        String searchStart = searchYYYY + strMM + "01";
        String searchEnd = searchYYYY + strMM + "31";

        Predicate<EventItem> eventActiveInMonthRequested =
                e -> (Integer.valueOf(e.getEventEndDate()) >= Integer.valueOf(searchStart) && Integer.valueOf(e.getEventStartDate()) <= Integer.valueOf(searchEnd));

        Predicate<EventItem> eventActiveInLocationRequested =
                e -> e.getEventLocation().equals(location) || e.getEventLocation().equals(KeysLocations.ALL_FLORIDA_KEYS);

        return getListOfEventsSortedByStartDate()
                .stream()
                .filter(eventActiveInMonthRequested.and(eventActiveInLocationRequested))
                .collect(Collectors.toList());

    }

//    private Boolean activeInMonthAndLocationRequested(String StartDate, String EndDate, String monthRequested, KeysLocations thisEventLocation, KeysLocations locationBeingSearched)
//    {
//        //strYYYYMMDD contains today..
//        int todaysMM = Integer.parseInt(strYYYYMMDD.substring(4,6));
//        int searchYYYY = Integer.parseInt(strYYYYMMDD.substring(0,4));
//
//        if (Integer.parseInt(monthRequested) < todaysMM)
//            searchYYYY +=1;
//
//        String searchStart = searchYYYY + monthRequested + "01";
//        String searchEnd = searchYYYY + monthRequested + "31";
//        if (Integer.valueOf(EndDate) >= Integer.valueOf(searchStart) && Integer.valueOf(StartDate) <= Integer.valueOf(searchEnd))
//            if (thisEventLocation.equals(locationBeingSearched) || thisEventLocation.equals(KeysLocations.ALL_FLORIDA_KEYS))
//                return true;
//            else
//                return false;
//        else
//            return false;
//    }
}
