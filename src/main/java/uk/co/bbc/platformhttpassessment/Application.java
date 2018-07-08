package uk.co.bbc.platformhttpassessment;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import uk.co.bbc.platformhttpassessment.domain.HttpGetResult;

import javax.inject.Inject;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Application {

    private final UrlGetter urlGetter;

    @Inject
    public Application(UrlGetter urlGetter) {
        this.urlGetter = urlGetter;
    }

    public void run(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
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
            e.printStackTrace();
        } catch (IOException e) {
            //todo log to stderr
            e.printStackTrace();
        }
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
