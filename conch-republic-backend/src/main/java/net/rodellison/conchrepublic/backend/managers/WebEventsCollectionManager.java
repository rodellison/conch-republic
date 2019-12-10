package net.rodellison.conchrepublic.backend.managers;

import net.rodellison.conchrepublic.common.model.KeysLocations;
import net.rodellison.conchrepublic.backend.utils.DataFetchUtil;

// Import log4j classes.
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.util.ArrayList;

public class WebEventsCollectionManager {

    private static final Logger log = LogManager.getLogger(WebEventsCollectionManager.class);
    private final DataFetchUtil theDataFetchUtil;
    private static ArrayList<String> rawEventDataList = new ArrayList<>();

    public WebEventsCollectionManager(DataFetchUtil dataUtilToUse)
    {
        this.theDataFetchUtil = dataUtilToUse;
    }

    public ArrayList<String> collectEventsForSearchDates(ArrayList<String> searchDates)
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

        String strExternalAPIURL = System.getenv("CONCH_REPUBLIC_BASE_URL");
        String strLocation = KeysLocations.getLocation(KeysLocations.ALL_FLORIDA_KEYS);
        String requestURL = strExternalAPIURL + "/" + strLocation + "/" + strYYYYMM + "/";

        CloseableHttpClient clientToUse = HttpClients.createDefault();
        HttpGet httpget = new HttpGet(requestURL);

        return theDataFetchUtil.fetchURLData(httpget, clientToUse);

    }
}
