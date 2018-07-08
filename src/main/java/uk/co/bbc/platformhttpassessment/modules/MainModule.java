package uk.co.bbc.platformhttpassessment.modules;

import dagger.Module;
import dagger.Provides;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.params.HttpParams;


@Module
public class MainModule {
    private static final int MS_PER_SEC = 1000;
    private static final int TIMEOUT_SECS = 5;
    @Provides
    HttpClient provideHttpClient() {
        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(TIMEOUT_SECS * MS_PER_SEC)
                .setSocketTimeout(TIMEOUT_SECS * MS_PER_SEC)
                .setConnectionRequestTimeout(TIMEOUT_SECS * MS_PER_SEC)
                .build();

        return HttpClientBuilder.create()
                .setDefaultRequestConfig(config)
                .build();
    }
}
