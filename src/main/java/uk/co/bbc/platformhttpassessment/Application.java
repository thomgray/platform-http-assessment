package uk.co.bbc.platformhttpassessment;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import uk.co.bbc.platformhttpassessment.domain.HttpGetResult;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Application {

    private static final int THREADS = 5;
    private final UrlGetter urlGetter;

    @Inject
    public Application(UrlGetter urlGetter) {
        this.urlGetter = urlGetter;
    }

    public void run(String[] args) {
        setProxies();

        ExecutorService executor = Executors.newFixedThreadPool(THREADS);
        ExecutorCompletionService<HttpGetResult> resultCompletionService = new ExecutorCompletionService<>(executor);

        List<HttpGetResult> results = Stream.of(args)
                .map((url) -> resultCompletionService.submit(() -> urlGetter.get(url)))
                .map(this::getResultFromFuture).collect(Collectors.toList());
        executor.shutdown();

        ObjectMapper objectMapper = createObjectMapper();
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(System.out, results);
            System.out.print("\n");
        } catch (JsonProcessingException e) {
            System.err.println("Error formatting results: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Encountered error while writing to std out: " + e.getMessage());
        }
    }

    private void setProxies() {
        // this isn't working. Application won't work on reith :(
        Stream.of("http", "https").forEach((protocol) -> {
            String envKey = protocol.toUpperCase() + "_PROXY";
            String proxyHostKey = protocol + ".proxyHost";
            String proxyPortKey = protocol + ".proxyPort";
            String proxtValue = System.getenv(envKey);

            if (proxtValue != null) {
                String[] parts = proxtValue.split(":");
                String portValue = parts.length > 1 ? parts[1] : null;

                System.setProperty(proxyHostKey, parts[0]);
                if (portValue != null) {
                    System.setProperty(proxyPortKey, portValue);
                }
            }
        });
    }

    private HttpGetResult getResultFromFuture(Future<HttpGetResult> future) {
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("Error getting result from future", e);
        }
    }

    private ObjectMapper createObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.configure(JsonGenerator.Feature.AUTO_CLOSE_TARGET, false);
        return objectMapper;
    }
}
