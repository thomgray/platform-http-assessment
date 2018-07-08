package uk.co.bbc.platformhttpassessment;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.net.URI;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UrlValidatorTest {
    final UrlValidator underTest = new UrlValidator();

    @ParameterizedTest
    @ValueSource(strings = {
            "http://www.foo.com",
            "https://foo.bar?with=param"
    })
    void shouldReturnOptionalOfUrlForValidUrls(String url) {
        Optional<URI> result = underTest.getValidUri(url);

        assertEquals(Optional.of(URI.create(url)), result);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "bad://www.foo.com",
            "https://www.foo with spaces"
    })
    void returnsEmptyOptionIfUrlIsInvalid(String url) {
        Optional<URI> result = underTest.getValidUri(url);

        assertEquals(Optional.empty(), result);
    }

}