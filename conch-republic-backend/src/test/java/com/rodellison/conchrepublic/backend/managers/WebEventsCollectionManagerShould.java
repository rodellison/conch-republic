package com.rodellison.conchrepublic.backend.managers;

import com.rodellison.conchrepublic.backend.utils.DataFetchUtil;
import com.rodellison.conchrepublic.backend.utils.ExternalAPIFetchUtil;
import com.rodellison.conchrepublic.backend.utils.SearchDateUtil;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

// Import log4j classes.
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

@DisplayName("Web Event Collection Manager should")
class WebEventsCollectionManagerShould {

    private static final Logger log = LogManager.getLogger(WebEventsCollectionManagerShould.class);
    private static final int monthsToSearch = 4;

    @Test
    @DisplayName("return fetched data for segment of Search Dates")
    void returnArrayOfFetchedEventData()
    {
        ExternalAPIFetchUtil myMockDataFetch = mock(ExternalAPIFetchUtil.class);
        String divWrapperHtml = "</div id=\"wrapper\">";

        //mockito spy to leave one method as-is, but override the other with a when...
        WebEventsCollectionManager testWebEventsCollectionManager = new WebEventsCollectionManager(myMockDataFetch);
        WebEventsCollectionManager aSpyTestEventsCollectionManager = spy(testWebEventsCollectionManager);
 //       when(aSpyTestEventsCollectionManager.getURLDataForAYearMonth(anyString()))
 //               .thenReturn(divWrapperHtml);

        when(myMockDataFetch.fetchURLData(any(HttpGet.class), any(CloseableHttpClient.class)))
                .thenReturn(divWrapperHtml);

        ArrayList<String> searchDateList = SearchDateUtil.getSearchDates(monthsToSearch, 1);
        ArrayList<String> searchParmResults = aSpyTestEventsCollectionManager.collectEventsForSearchDates(searchDateList);

        assertAll(
                () -> assertNotNull(searchParmResults),
                () -> assertTrue(searchParmResults.get(0).equals(divWrapperHtml)),
                () -> assertEquals(monthsToSearch/2, searchParmResults.size())
        );
    }

}