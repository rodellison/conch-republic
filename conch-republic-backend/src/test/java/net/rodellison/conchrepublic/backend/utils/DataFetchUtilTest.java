package net.rodellison.conchrepublic.backend.utils;

import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicStatusLine;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalMatchers.eq;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayName("Data Fetch Util should")
class DataFetchUtilTest {

    @Test
    @DisplayName(" should return successfully")
    void fetchURLData() throws IOException {

        HttpGet mockHttpGet = new HttpGet("http://someURL");
        CloseableHttpClient mockHTTPClient = mock(CloseableHttpClient.class);

        when(mockHTTPClient.execute(any(HttpGet.class), any(ResponseHandler.class)))
                .thenAnswer(item -> {
                    final BasicHttpResponse ret = new BasicHttpResponse(
                            new BasicStatusLine(HttpVersion.HTTP_1_1, HttpURLConnection.HTTP_OK, "OK"));
                    ret.setEntity(new StringEntity("just testing", StandardCharsets.UTF_8));

                    @SuppressWarnings("unchecked")
                    final ResponseHandler<String> handler = (ResponseHandler<String>) item.getArguments()[1];
                    return handler.handleResponse(ret);

                });

        DataFetchUtil testDFU = new DataFetchUtil();
        String result = testDFU.fetchURLData(mockHttpGet, mockHTTPClient);
        assertEquals("just testing", result);
    }

    @Test
    @DisplayName(" should return null from IOException")
    void fetchDataGetIOException() throws IOException {

        HttpGet mockHttpGet = new HttpGet("http://someURL");
        CloseableHttpClient mockHTTPClient = mock(CloseableHttpClient.class);

        when(mockHTTPClient.execute(any(HttpGet.class), any(ResponseHandler.class)))
                .thenThrow(IOException.class);

        DataFetchUtil testDFU = new DataFetchUtil();
        String result = testDFU.fetchURLData(mockHttpGet, mockHTTPClient);
        assertNull(result);
    }

    @Test
    @DisplayName(" should return null from ClientProtocol exception")
    void fetchDataGetClientProtocolException() throws IOException {

        HttpGet mockHttpGet = new HttpGet("http://someURL");
        CloseableHttpClient mockHTTPClient = mock(CloseableHttpClient.class);

        when(mockHTTPClient.execute(any(HttpGet.class), any(ResponseHandler.class)))
                .thenThrow(ClientProtocolException.class);

        DataFetchUtil testDFU = new DataFetchUtil();
        String result = testDFU.fetchURLData(mockHttpGet, mockHTTPClient);
        assertNull(result);
    }
}