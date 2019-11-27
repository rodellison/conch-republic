package com.rodellison.conchrepublic.backend.utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public final class SearchDateUtil {

    private static List<String> setupSearchDates(int monthsToFetch, int segment) {

        ArrayList<String> searchDates = new ArrayList<>();

        Date today = new Date(); // Fri Jun 17 14:54:28 PDT 2016
        Calendar cal = Calendar.getInstance();  //calendar is 0 based for months so Jan = month 0, February = month 1, etc.
        cal.setTime(today); // don't forget this if date is arbitrary e.g. 01-01-2014

        int month;
        int year;

        for (int i = 0; i < monthsToFetch; i++) {
            month = cal.get(Calendar.MONTH) + 1;
            year = cal.get(Calendar.YEAR);

            String strYYYYMM = year + String.format("%02d", Integer.valueOf(month));
            // displays the current calendar
            //System.out.println("Year and Month is:  " + cal.get(Calendar.MONTH));
            searchDates.add(strYYYYMM);
            // roll month
            cal.roll(Calendar.MONTH, true);
            if (cal.get(Calendar.MONTH) == 0) {
                cal.roll(Calendar.YEAR, true);
            }
        }

        int startCounter = (segment == 1) ? 0 : monthsToFetch / 2;
        int endMonth = (segment == 1) ? monthsToFetch / 2 : monthsToFetch;

        return searchDates.subList(startCounter, endMonth);


    }

    public static final ArrayList<String> getSearchDates(int monthsToFetch, int segment) {
        //the segment variable is to allow us to specify either the first half of the total dates,
        //or the second  e.g. value is 1, or 2
        List<String> returnSearch = setupSearchDates(monthsToFetch, segment);
        return new ArrayList<String>(returnSearch);
    }

}
