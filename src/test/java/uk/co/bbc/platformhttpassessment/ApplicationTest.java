package uk.co.bbc.platformhttpassessment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.co.bbc.platformhttpassessment.domain.HttpGetResult;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ApplicationTest {
    public static final String URL_1 = "http://foo.com";
    public static final String URL_2 = "https://www.bar.co.uk";
    Application underTest;

    @Mock
    UrlGetter urlGetter;

    private final ByteArrayOutputStream sysOutStream = new ByteArrayOutputStream();
    private PrintStream sysOut;

    @BeforeEach
    void setup() {
        underTest = new Application(urlGetter);
        sysOut = System.out;
        System.setOut(new PrintStream(sysOutStream));
    }

    @AfterEach
    void teardown() {
        System.setOut(sysOut);
    }

    @Test
    void shouldMakeRequestForEachUrlInArgs() throws Exception {
        String[] args = {URL_1, URL_2};

        underTest.run(args);

        verify(urlGetter).get(URL_1);
        verify(urlGetter).get(URL_2);
        verifyNoMoreInteractions(urlGetter);
    }

    @Test
    void shouldLogResulsAsJsonStream() throws Exception {
        HttpGetResult result1 = new HttpGetResult(URL_1, 200, 100L, "Date now", null);
        HttpGetResult result2 = new HttpGetResult(URL_2, 200, 200L, "Date now", null);

        when(urlGetter.get(anyString())).thenReturn(result1).thenReturn(result2);

        underTest.run(new String[]{URL_1, URL_2});

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        String expectedOutput = objectMapper.writeValueAsString(List.of(result1, result2));

        assertEquals(expectedOutput, sysOutStream.toString());
    }


}