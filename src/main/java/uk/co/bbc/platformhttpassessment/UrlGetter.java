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
import java.util.Optional;

public class UrlGetter {
    public static final String DATE_HEADER_NAME = "Date";
    public static final String CONTENT_LENGTH_HEADER_NAME = "Content-Length";

    private final HttpClient client;
    private final UrlValidator validator;

    @Inject
    public UrlGetter(HttpClient client, UrlValidator validator) {
        this.client = client;
        this.validator = validator;
    }

    public HttpGetResult get(String url) throws IOException {
        HttpGetResult.Builder resultBuilder = new HttpGetResult.Builder()
                .url(url);

        Optional<URI> validUri = validator.getValidUri(url);

        if (!validUri.isPresent()) {
            return resultBuilder.error("invalid url").build();
        }

        HttpGet get = new HttpGet(validUri.get());
        HttpResponse response = client.execute(get);

        resultBuilder.statusCode(response.getStatusLine().getStatusCode());

        List<Header> headers = List.of(response.getAllHeaders());
        String contentLength = getHeader(CONTENT_LENGTH_HEADER_NAME, headers);
        String date = getHeader(DATE_HEADER_NAME, headers);
        resultBuilder.date(date);
        try {
            resultBuilder.contentLength(Long.valueOf(contentLength));
        } catch (NumberFormatException e) {
            // ignore exception, leave field as null
        }

        return resultBuilder.build();
    }

    private String getHeader(String header, List<Header> headers) {
        return headers.stream()
                .filter((h) -> h.getName().equals(header))
                .findFirst()
                .map(Header::getValue).orElse(null);
    }
}
