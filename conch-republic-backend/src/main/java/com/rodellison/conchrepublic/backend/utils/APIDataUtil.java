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

public class APIDataUtil {

    private static final String CLASS_NAME = "APIDataUtil";
    private static final Logger log = LogManager.getLogger(APIDataUtil.class);

    /**
     * GetAPIRequest is used to call the external (from AWS) API to get data
     *
     * @param strExternalAPIURL
     *         String object containing the url to be requested
     * @param strYYYYMM
     *         String object containing the year and month in order, e.g. 202006
     * @param enumLocation
     *         enumValue that will be converted to a URL resource
     *
     * @return String containing API response results (json text)
     */
    public String fetchURLData(String strExternalAPIURL, String strYYYYMM, KeysLocations enumLocation) {

        String strLocation = KeysLocations.getLocation(enumLocation);
        String requestURL = strExternalAPIURL + "/" + strLocation + "/" + strYYYYMM + "/";

        log.warn("Performing APIDataUtil GetAPIRequest : " + requestURL);

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
            log.info("Executing httpclient request");
            String responseBody = httpclient.execute(httpget, responseHandler);
            log.info("httpclient request returned");
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
