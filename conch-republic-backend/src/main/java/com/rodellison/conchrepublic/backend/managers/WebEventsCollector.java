package com.rodellison.conchrepublic.backend.managers;

import com.rodellison.conchrepublic.backend.utils.ExternalAPIFetchUtil;
// Import log4j classes.
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.util.ArrayList;

public class WebEventsCollector {

    private static final Logger log = LogManager.getLogger(WebEventsCollector.class);

    private final ExternalAPIFetchUtil theDataFetchUtil;
    private final int monthsToSearch;
    private static ArrayList<String> rawEventDataList = new ArrayList<>();

    public WebEventsCollector(ExternalAPIFetchUtil dataUtilToUse, int countMonthsToSearch)
    {
        this.theDataFetchUtil = dataUtilToUse;
        this.monthsToSearch = countMonthsToSearch;
    }

    public ArrayList<String> collectEventsForSeachDates(ArrayList<String> searchDates)
    {
         try
        {
            log.info("Performing loop to obtain external URL results");
            searchDates.forEach(searchDate ->
            {
                String results = getURLDataForAYearMonth(searchDate);
                if (results != null)
                    rawEventDataList.add(results);
            });
        }
        catch (Exception e)
        {
            log.error("Error in collectEventsForSeachDates: " + e.getMessage());
        }

        return rawEventDataList;
    }

    public String getURLDataForAYearMonth(String strYYYYMM) {

        log.info("Fetching external URL results for date: " + strYYYYMM);
        return theDataFetchUtil.fetchURLData(strYYYYMM);

    }
}