package com.rodellison.conchrepublic.backend.utils;

import com.rodellison.conchrepublic.backend.model.KeysLocations;
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicStatusLine;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalMatchers.eq;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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