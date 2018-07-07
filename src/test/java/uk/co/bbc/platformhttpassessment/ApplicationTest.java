package uk.co.bbc.platformhttpassessment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ApplicationTest {
    public static final String URL_1 = "http://foo.com";
    public static final String URL_2 = "htps://www.bar.co.uk";
    Application underTest;

    @Mock
    UrlGetter urlGetter;

    @BeforeEach
    void setup() {
        underTest = new Application(urlGetter) {
        };
    }

    @Test
    void shouldMakeRequestForEachUrlInArgs() {
        String[] args = {URL_1, URL_2};

        InOrder inOrder = Mockito.inOrder(urlGetter);

        inOrder.verify(urlGetter).get(URL_1);
        inOrder.verify(urlGetter).get(URL_2);
        inOrder.verifyNoMoreInteractions();
    }


}