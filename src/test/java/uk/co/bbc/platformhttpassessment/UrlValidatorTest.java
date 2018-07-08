package uk.co.bbc.platformhttpassessment;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;


class UrlValidatorTest {
    private static final String URL_WITH_INVALID_PROTOCOL = "bad://www.foo.com";
    private static final String URL_WITH_SPACE = "https://www.foo and bar.com";

    @ParameterizedTest
    @ValueSource(strings = {URL_WITH_INVALID_PROTOCOL, URL_WITH_SPACE})
    void returnsEmptyOptionIfUrlIsInvalid() {

    }

}