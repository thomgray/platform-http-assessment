package uk.co.bbc.platformhttpassessment;

import com.google.common.collect.Lists;
import com.google.common.collect.Streams;
import uk.co.bbc.platformhttpassessment.domain.HttpGetResult;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
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
        Stream<Future<HttpGetResult>> results = Stream.of(args)
                .map((url) -> resultCompletionService.submit(() -> urlGetter.get(url)));

        List<HttpGetResult> res = results.map(this::getResultFromFuture).collect(Collectors.toList());
    }

    private HttpGetResult getResultFromFuture(Future<HttpGetResult> future) {
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("Error getting result from future", e);
        }
    }
}
