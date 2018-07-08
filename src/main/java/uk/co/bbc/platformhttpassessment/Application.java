package uk.co.bbc.platformhttpassessment;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import uk.co.bbc.platformhttpassessment.domain.HttpGetResult;

import javax.inject.Inject;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Application {

    private final UrlGetter urlGetter;
    private final Executor executor;

    @Inject
    public Application(UrlGetter urlGetter) {
        this.urlGetter = urlGetter;
        executor = Executors.newFixedThreadPool(5);
    }

    public void run(String[] args) {
        CompletionService<HttpGetResult> resultCompletionService = new ExecutorCompletionService<>(executor);

        List<HttpGetResult> results = Stream.of(args)
                .map((url) -> resultCompletionService.submit(() -> urlGetter.get(url)))
                .map(this::getResultFromFuture).collect(Collectors.toList());

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.configure(JsonGenerator.Feature.AUTO_CLOSE_TARGET, false);

        try {
            objectMapper.writeValue(System.out, results);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
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
}
