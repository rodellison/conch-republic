package com.rodellison.conchrepublic.backend.utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public final class SearchDateUtil {

    public static final ArrayList<String> searchDates = new ArrayList<>();

    private static final void setupSearchDates() {
        Date today = new Date(); // Fri Jun 17 14:54:28 PDT 2016
        Calendar cal = Calendar.getInstance();  //calendar is 0 based for months so Jan = month 0, February = month 1, etc.
        cal.setTime(today); // don't forget this if date is arbitrary e.g. 01-01-2014

        int month;
        int year;

        //12 total months including today, if starting at month 0
        for (int i = 0; i < 12; i++) {
            month = cal.get(Calendar.MONTH) + 1;
            year = cal.get(Calendar.YEAR);

            String strYYYYMM = year + String.format("%02d", Integer.valueOf(month));
            // displays the current calendar
            //System.out.println("Year and Month is:  " + cal.get(Calendar.MONTH));
            searchDates.add(strYYYYMM);
            // roll month
            cal.roll(Calendar.MONTH, true);
            if (cal.get(Calendar.MONTH) == 0)
            {
                cal.roll(Calendar.YEAR, true);
            }
         }
    }

    public static final ArrayList<String> getSearchDates()
    {
        setupSearchDates();
        return searchDates;
    }

}
