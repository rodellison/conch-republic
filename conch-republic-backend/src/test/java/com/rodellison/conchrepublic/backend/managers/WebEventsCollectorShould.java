package com.rodellison.conchrepublic.backend.managers;

import com.rodellison.conchrepublic.backend.utils.DataFetchUtilTestDoubleShould;
import com.rodellison.conchrepublic.backend.utils.ExternalAPIFetchUtil;
import com.rodellison.conchrepublic.backend.utils.SearchDateUtil;
import org.hamcrest.collection.IsEmptyCollection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.jupiter.api.Assertions.*;

// Import log4j classes.
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

@DisplayName("Web Event Collector should")
class WebEventsCollectorShould {

    private static final Logger log = LogManager.getLogger(WebEventsCollectorShould.class);
    private static final int monthsToSearch = 4;
//    private final ExternalAPIFetchUtil myTestDataFetchUtil = new DataFetchUtil();
    private final ExternalAPIFetchUtil myTestDataFetchUtil = new DataFetchUtilTestDoubleShould();
    private final WebEventsCollector testWebEventsCollector = new WebEventsCollector(myTestDataFetchUtil, monthsToSearch);

    @Test
    @DisplayName("return fetched data for specific Year and Month")
    void returnURLFetchedDataForAYearAndMonth()
    {
        String testYYYYMM = "202001";
        String searchParmResults = testWebEventsCollector.getURLDataForAYearMonth(testYYYYMM);
        assertTrue(searchParmResults.contains("<div id=\"wrapper\">"));
    }

    @Test
    @DisplayName("return fetched data for all Search Dates")
    void returnArrayOfFetchedEventData()
    {
        ArrayList<String> searchParmResults = testWebEventsCollector.collectEventsForSeachDates(SearchDateUtil.getSearchDates(monthsToSearch));
        assertAll(
                () -> assertNotNull(searchParmResults),
                () -> assertTrue(searchParmResults.get(0).contains("<div id=\"wrapper\">")),
                () -> assertEquals(4, searchParmResults.size())
        );
    }

    @Test
    @DisplayName("get a list of the next x valid YYYYMM search parm dates")
    void getSearchDates() {

        int intTestMonthsToFetch = 4;
        ArrayList<String> theSearchDateParms = SearchDateUtil.getSearchDates(intTestMonthsToFetch);
        assertAll(
                () -> assertThat(theSearchDateParms, not(IsEmptyCollection.empty())),
                () -> assertThat(theSearchDateParms, hasItem("202001")),
                () -> assertThat(theSearchDateParms, hasSize(intTestMonthsToFetch))
        );

        theSearchDateParms.forEach(log::debug);  //log.info print each item

    }

}