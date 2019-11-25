package com.rodellison.conchrepublic.backend.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Data Fetch Util should")
class DataFetchUtilShould {

    private DataFetchUtil myDataFetchUtilObject = new DataFetchUtil();

    @Test
    @DisplayName("retrieve data from the external URL")
    void retrieveURLData() {

        String testYear = "2020";
        String testMonth = "06";

        String responseBody = myDataFetchUtilObject.fetchURLData(testYear + testMonth);

        assertTrue(responseBody.contains("<div id=\"wrapper\">"));

    }
}