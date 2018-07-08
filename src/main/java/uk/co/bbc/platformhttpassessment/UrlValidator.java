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
            String scheme = uri.getScheme();
            if (scheme != null && schemeIsValid(scheme)) {
                return Optional.of(uri);
            } else {
                return Optional.empty();
            }
        } catch (URISyntaxException e) {
            return Optional.empty();
        }
    }

    private boolean schemeIsValid(String scheme) {
        return VALID_SCHEMES.contains(scheme);
    }
}
