package uk.co.bbc.platformhttpassessment;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

public class UrlValidator {
    private static final List<String> VALID_SCHEMES = List.of("http", "https");

    @Inject
    public UrlValidator() {
    }

    public Optional<URI> getValidUri(String url) {
        try {
            URI uri = new URI(url);
            if (!schemeIsValid(uri.getScheme())) {
                return Optional.empty();
            }
            return Optional.of(uri);
        } catch (URISyntaxException e) {
            return Optional.empty();
        }
    }

    private boolean schemeIsValid(String scheme) {
        return VALID_SCHEMES.contains(scheme);
    }
}
