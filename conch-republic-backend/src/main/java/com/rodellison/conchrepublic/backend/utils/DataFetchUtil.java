package com.rodellison.conchrepublic.backend.utils;

import com.rodellison.conchrepublic.backend.model.KeysLocations;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
// Import log4j classes.
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.io.IOException;

public class DataFetchUtil implements ExternalAPIFetchUtil {

    private static final String CLASS_NAME = "DataFetchUtil";
    private static final Logger log = LogManager.getLogger(DataFetchUtil.class);

    /**
     * GetAPIRequest is used to call the external (from AWS) API to get data
     *
     * @param strYYYYMM
     *         String object containing the year and month in order, e.g. 202006
     *
     * @return String containing API response results (json text)
     */
    @Override
    public String fetchURLData(String strYYYYMM) {

        String strExternalAPIURL = System.getenv("CONCH_REPUBLIC_BASE_URL");
        String strLocation = KeysLocations.getLocation(KeysLocations.ALL_FLORIDA_KEYS);
        String requestURL = strExternalAPIURL + "/" + strLocation + "/" + strYYYYMM + "/";

        log.warn("Performing " + CLASS_NAME + " API Request : " + requestURL);

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet(requestURL);

        // Create a response handler
        ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

            @Override
            public String handleResponse(final HttpResponse response) throws ClientProtocolException, IOException {
                int status = response.getStatusLine().getStatusCode();
                if (status >= 200 && status < 300) {
                    HttpEntity entity = response.getEntity();
                    return entity != null ? EntityUtils.toString(entity) : null;
                } else {
                    log.warn("Unexpected status returned from httpGet call " + status);
                    throw new ClientProtocolException("Unexpected response status: " + status);
                }
            }
        };

        try {
            log.debug("Executing httpclient request");
            String responseBody = httpclient.execute(httpget, responseHandler);
            log.debug("httpclient request returned");
            httpclient.close();
            return responseBody;

        } catch (ClientProtocolException cpe) {
            log.error("APIDataUtil:ClientProtocolException occurred: " + cpe.getMessage());
            return null;
        } catch (IOException ioe) {
            log.error("APIDataUtil:IOException occurred: " + ioe.getMessage());
            return null;

        }
    }




}
