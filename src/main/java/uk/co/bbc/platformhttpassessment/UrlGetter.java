package uk.co.bbc.platformhttpassessment;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import uk.co.bbc.platformhttpassessment.domain.HttpGetResult;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URI;
import java.util.List;

public class UrlGetter {
    static final String DATE_HEADER_NAME = "Date";
    static final String CONTENT_LENGTH_HEADER_NAME = "Content-Length";

    private final HttpClient client;
    private final UrlValidator validator;

    @Inject
    UrlGetter(HttpClient client, UrlValidator validator) {
        this.client = client;
        this.validator = validator;
    }

    public HttpGetResult get(String url) {
        HttpGetResult.Builder resultBuilder = new HttpGetResult.Builder()
                .url(url);

        validator.getValidUri(url).ifPresentOrElse(
                (uri) -> performGetRequest(resultBuilder, uri),
                () -> resultBuilder.error("invalid url")
        );

        return resultBuilder.build();
    }

    private void performGetRequest(HttpGetResult.Builder resultBuilder, URI uri) {
        HttpGet get = new HttpGet(uri);
        HttpResponse response;

        try {
            response = client.execute(get);
            resultBuilder.statusCode(response.getStatusLine().getStatusCode());
            setHeaderValues(resultBuilder, response);
        } catch (IOException e) {
            resultBuilder.error("connection error: " + e.getMessage());
        } finally {
            get.releaseConnection();
        }
    }


    private void setHeaderValues(HttpGetResult.Builder resultBuilder, HttpResponse response) {
        List<Header> headers = List.of(response.getAllHeaders());
        String contentLength = getHeader(CONTENT_LENGTH_HEADER_NAME, headers);
        String date = getHeader(DATE_HEADER_NAME, headers);
        resultBuilder.date(date);
        try {
            resultBuilder.contentLength(Long.valueOf(contentLength));
        } catch (NumberFormatException e) {
            // ignore exception, leave field as null
        }
    }

    private String getHeader(String header, List<Header> headers) {
        return headers.stream()
                .filter((h) -> h.getName().equals(header))
                .findFirst()
                .map(Header::getValue).orElse(null);
    }
}
