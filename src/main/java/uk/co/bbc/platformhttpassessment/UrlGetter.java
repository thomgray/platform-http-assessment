package uk.co.bbc.platformhttpassessment;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import uk.co.bbc.platformhttpassessment.domain.HttpGetResult;

import javax.inject.Inject;
import java.io.IOException;
import java.util.List;

public class UrlGetter {
    public static final String DATE_HEADER_NAME = "Date";
    public static final String CONTENT_LENGTH_HEADER_NAME = "Content-Length";

    private final HttpClient client;

    @Inject
    public UrlGetter(HttpClient client) {
        this.client = client;
    }

    public HttpGetResult get(String url) throws IOException {
        HttpGet get = new HttpGet(url);
        HttpResponse response = client.execute(get);

        List<Header> headers = List.of(response.getAllHeaders());

        String contentLength = headers.stream()
                .filter((h) -> h.getName().equals(CONTENT_LENGTH_HEADER_NAME))
                .findFirst()
                .map(Header::getValue).orElse(null);

        String date = headers.stream()
                .filter((h) -> h.getName().equals(DATE_HEADER_NAME))
                .findFirst()
                .map(Header::getValue).orElse(null);

        return new HttpGetResult(url, response.getStatusLine().getStatusCode(), Long.valueOf(contentLength), date);
    }
}
