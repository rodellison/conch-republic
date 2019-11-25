package com.rodellison.conchrepublic.backend.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Data Fetch Util should")
class DataFetchUtilShould {

    private DataFetchUtilTestDouble myDataFetchUtilObject = new DataFetchUtilTestDouble();

    @Test
    @DisplayName("retrieve data from the external URL")
    void retrieveURLData() {

        String testYYYYMM = "202006";
        String responseBody = myDataFetchUtilObject.fetchURLData(testYYYYMM);

        assertTrue(responseBody.contains("<div id=\"wrapper\">"));

    }
}