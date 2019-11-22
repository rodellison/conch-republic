package com.rodellison.conchrepublic.backend.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

import org.hamcrest.collection.IsEmptyCollection;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.MatcherAssert.assertThat;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Search Date Util")
class SearchDateUtilTest {

    @Test
    @DisplayName("can get a list of YYYYMM search parm dates")
    void getSearchDates() {

        ArrayList<String> theSearchDateParms = SearchDateUtil.getSearchDates();
        assertThat(theSearchDateParms, not(IsEmptyCollection.empty()));
        assertThat(theSearchDateParms, hasItem("202010"));
        assertThat(theSearchDateParms, hasSize(12));

    }
}