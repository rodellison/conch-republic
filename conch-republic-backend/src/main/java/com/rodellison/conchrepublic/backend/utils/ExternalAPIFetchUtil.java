package com.rodellison.conchrepublic.backend.utils;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;

public interface ExternalAPIFetchUtil {
    String fetchURLData(HttpGet getURI, CloseableHttpClient clientToUse);
}
