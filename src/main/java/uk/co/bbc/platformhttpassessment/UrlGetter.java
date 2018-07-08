package uk.co.bbc.platformhttpassessment;

import org.apache.http.client.HttpClient;
import uk.co.bbc.platformhttpassessment.domain.HttpGetResult;

import javax.inject.Inject;
import java.io.IOException;

public class UrlGetter {
    private final HttpClient client;


    @Inject
    public UrlGetter(HttpClient client) {
        this.client = client;
    }

    public HttpGetResult get(String url) throws IOException {
        return null;
    }
}
