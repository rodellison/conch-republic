package com.rodellison.conchrepublic.backend.managers;

import com.rodellison.conchrepublic.backend.utils.DataFetchUtilTestDouble;
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

@DisplayName("Web Event Collection Manager should")
class WebEventsCollectionManagerShould {

    private static final Logger log = LogManager.getLogger(WebEventsCollectionManagerShould.class);
    private static final int monthsToSearch = 4;

    private final ExternalAPIFetchUtil myTestDataFetchUtil = new DataFetchUtilTestDouble();
    private final WebEventsCollectionManager testWebEventsCollectionManager = new WebEventsCollectionManager(myTestDataFetchUtil);

    @Test
    @DisplayName("return fetched data for specific Year and Month")
    void returnURLFetchedDataForAYearAndMonth()
    {
        String testYYYYMM = "202006";
        String searchParmResults = testWebEventsCollectionManager.getURLDataForAYearMonth(testYYYYMM);
        assertTrue(searchParmResults.contains("<div id=\"wrapper\">"));
    }

    @Test
    @DisplayName("return fetched data for all Search Dates")
    void returnArrayOfFetchedEventData()
    {
        ArrayList<String> searchParmResults = testWebEventsCollectionManager.collectEventsForSeachDates(SearchDateUtil.getSearchDates(monthsToSearch, 1));
        assertAll(
                () -> assertNotNull(searchParmResults),
                () -> assertTrue(searchParmResults.get(0).contains("<div id=\"wrapper\">")),
                () -> assertEquals(monthsToSearch/2, searchParmResults.size())
        );
    }

    @Test
    @DisplayName("get a list of the next x valid YYYYMM search parm dates")
    void getSearchDates() {

        ArrayList<String> theSearchDateParms = SearchDateUtil.getSearchDates(monthsToSearch, 1);
        assertAll(
                () -> assertThat(theSearchDateParms, not(IsEmptyCollection.empty())),
                () -> assertThat(theSearchDateParms, hasItem("201912")),
                () -> assertThat(theSearchDateParms, hasSize(monthsToSearch/2))
        );

        theSearchDateParms.forEach(log::debug);  //log.info print each item

    }

}