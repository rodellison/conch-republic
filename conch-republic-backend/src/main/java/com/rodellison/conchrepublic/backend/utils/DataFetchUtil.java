package com.rodellison.conchrepublic.backend.utils;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
// Import log4j classes.
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.io.IOException;

public class DataFetchUtil  {

    private static final String CLASS_NAME = "DataFetchUtil";
    private static final Logger log = LogManager.getLogger(DataFetchUtil.class);

    /**
     * GetAPIRequest is used to call the external (from AWS) API to get data
     *
     * @param getURI
     *         HttpGet object containing URI we're searching for
     * @param clientToUse
     *         CloseableHttpClient to use for this execution
     *
     * @return String containing API response results (json text)
     */

    public String fetchURLData(HttpGet getURI, CloseableHttpClient clientToUse) {

        log.info("Performing " + CLASS_NAME + " API Request : " + getURI.getURI());

        // Create a response handler
        ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

            @Override
            public String handleResponse(final HttpResponse response) throws IOException {
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
            String responseBody = clientToUse.execute(getURI, responseHandler);
            log.debug("httpclient request returned");
            clientToUse.close();
            return responseBody;

        } catch (ClientProtocolException cpe) {
            log.error("DataFetchUtil:ClientProtocolException occurred: " + cpe.getMessage());
            return null;
        } catch (IOException ioe) {
            log.error("DataFetchUtil:IOException occurred: " + ioe.getMessage());
            return null;

        }
    }

}
