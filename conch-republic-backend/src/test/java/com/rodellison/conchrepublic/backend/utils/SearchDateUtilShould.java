package com.rodellison.conchrepublic.backend.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

import org.hamcrest.collection.IsEmptyCollection;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.MatcherAssert.assertThat;

import static org.junit.jupiter.api.Assertions.*;
// Import log4j classes.
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

@DisplayName("SearchDateUtil should")
class SearchDateUtilShould {

    private static final String CLASS_NAME = "SearchDateUtilTest";
    private static final Logger log = LogManager.getLogger(SearchDateUtilShould.class);

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