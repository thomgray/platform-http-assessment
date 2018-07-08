package uk.co.bbc.platformhttpassessment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import uk.co.bbc.platformhttpassessment.domain.HttpGetResult;

import javax.inject.Inject;
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
    private final ObjectMapper objectMapper;

    @Inject
    public Application(UrlGetter urlGetter) {
        this.urlGetter = urlGetter;
        executor = Executors.newFixedThreadPool(5);
        objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    public void run(String[] args) {
        CompletionService<HttpGetResult> resultCompletionService = new ExecutorCompletionService<>(executor);

        List<HttpGetResult> results = Stream.of(args)
                .map((url) -> resultCompletionService.submit(() -> urlGetter.get(url)))
                .map(this::getResultFromFuture).collect(Collectors.toList());

    }

    private HttpGetResult getResultFromFuture(Future<HttpGetResult> future) {
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("Error getting result from future", e);
        }
    }
}
