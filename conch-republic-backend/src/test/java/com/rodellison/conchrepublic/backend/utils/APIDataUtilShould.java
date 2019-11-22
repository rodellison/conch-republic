package com.rodellison.conchrepublic.backend.utils;

import com.rodellison.conchrepublic.backend.model.KeysLocations;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("API Data Util should")
class APIDataUtilShould {

    private APIDataUtil myAPIDataUtilObject = new APIDataUtil();

    @Test
    @DisplayName("retrieve data from the external URL")
    void retrieveURLData() {

        String URLToTest = System.getenv("CONCH_REPUBLIC_BASE_URL");
        String testYear = "2020";
        String testMonth = "06";
        KeysLocations testLocation = KeysLocations.ALL_FLORIDA_KEYS;

        String responseBody = myAPIDataUtilObject.fetchURLData(URLToTest, testYear + testMonth, testLocation);

        assertTrue(responseBody.contains("<div id=\"wrapper\">"));

    }
}