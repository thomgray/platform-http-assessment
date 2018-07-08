package uk.co.bbc.platformhttpassessment;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.message.BasicHeader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.co.bbc.platformhttpassessment.domain.HttpGetResult;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UrlGetterTest {
    private static final String URL_1 = "http://www.foo.com";
    private static final long CONTENT_LENGTH = 100L;
    private static final String DATE = "Sun, 08 Jul 2018 11:15:12 GMT";
    private static final int OK_200 = 200;
    public static final String DATE_HEADER_NAME = "Date";
    public static final String CONTENT_LENGTH_HEADER_NAME = "Content-Length";

    @Mock
    private HttpClient mockClient;
    @Mock
    private HttpResponse response;
    @Mock
    private StatusLine statusLine;

    @Captor
    private ArgumentCaptor<HttpGet> requestCaptor;

    private UrlGetter underTest;

    @BeforeEach
    void setup() {
        underTest = new UrlGetter(mockClient);
    }

    @Test
    void shouldCompleteGetRequestForUrlPopulatingResultWithResponse() throws IOException {
        when(mockClient.execute(any(HttpGet.class))).thenReturn(response);

        when(response.getStatusLine()).thenReturn(statusLine);
        when(statusLine.getStatusCode()).thenReturn(OK_200);

        when(response.getAllHeaders()).thenReturn(new Header[]{
                new BasicHeader(CONTENT_LENGTH_HEADER_NAME, String.valueOf(CONTENT_LENGTH)),
                new BasicHeader(DATE_HEADER_NAME, DATE)
        });

        HttpGetResult result = underTest.get(URL_1);

        verify(mockClient).execute(requestCaptor.capture());
        HttpGet request = requestCaptor.getValue();

        assertEquals(URL_1, request.getURI().toString());

        assertEquals(CONTENT_LENGTH, result.getContentLength());
        assertEquals(DATE, result.getDateTime());
        assertEquals(URL_1, result.getUrl());
        assertEquals(OK_200, result.getStatus());
    }
}