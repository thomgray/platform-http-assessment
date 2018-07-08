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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.co.bbc.platformhttpassessment.domain.HttpGetResult;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static uk.co.bbc.platformhttpassessment.UrlGetter.CONTENT_LENGTH_HEADER_NAME;
import static uk.co.bbc.platformhttpassessment.UrlGetter.DATE_HEADER_NAME;

@ExtendWith(MockitoExtension.class)
class UrlGetterTest {
    private static final String URL = "http://www.foo.com";
    private static final String URL_WITH_INVALID_PROTOCOL = "bad://www.foo.com";
    private static final String URL_WITH_SPACE = "https://www.foo and bar.com";
    private static final Long CONTENT_LENGTH = 100L;
    private static final String DATE = "Sun, 08 Jul 2018 11:15:12 GMT";
    private static final Integer OK_200 = 200;
    private static final Integer NOT_FOUND_404 = 404;

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
    void shouldCompleteGetRequestForUrlPopulatingResultWithResponse() throws Exception {
        setupMocks(OK_200, new Header[]{
                new BasicHeader(CONTENT_LENGTH_HEADER_NAME, String.valueOf(CONTENT_LENGTH)),
                new BasicHeader(DATE_HEADER_NAME, DATE)
        });

        HttpGetResult result = underTest.get(URL);

        verify(mockClient).execute(requestCaptor.capture());
        HttpGet request = requestCaptor.getValue();

        assertAll(
                () -> assertEquals(URL, request.getURI().toString()),
                () -> assertEquals(CONTENT_LENGTH, result.getContentLength()),
                () -> assertEquals(DATE, result.getDateTime()),
                () -> assertEquals(URL, result.getUrl()),
                () -> assertEquals(OK_200, result.getStatus())
        );
    }

    @Test
    void returnsAPartialResultIfHeadersAreMissing() throws Exception {
        setupMocks(NOT_FOUND_404, new Header[]{});

        HttpGetResult result = underTest.get(URL);

        assertAll(
                () -> assertNull(result.getContentLength()),
                () -> assertNull(result.getDateTime()),
                () -> assertEquals(URL, result.getUrl()),
                () -> assertEquals(NOT_FOUND_404, result.getStatus())
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {URL_WITH_INVALID_PROTOCOL, URL_WITH_SPACE})
    void returnsErrorResultForInvalidUrl(String url) throws Exception {
        setupMocks(OK_200, new Header[]{});

        HttpGetResult result = underTest.get(url);

        verifyZeroInteractions(mockClient);

        assertAll(
                () -> assertEquals(url, result.getUrl()),
                () -> assertEquals("invalid url", result.getError()),
                () -> assertNull(result.getStatus()),
                () -> assertNull(result.getDateTime()),
                () -> assertNull(result.getContentLength())
        );
    }

    private void setupMocks(int status, Header[] headers) throws Exception {
        when(mockClient.execute(any(HttpGet.class))).thenReturn(response);
        when(response.getStatusLine()).thenReturn(statusLine);

        when(statusLine.getStatusCode()).thenReturn(status);
        when(response.getAllHeaders()).thenReturn(headers);
    }
}