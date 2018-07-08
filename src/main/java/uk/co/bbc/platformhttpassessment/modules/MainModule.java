package uk.co.bbc.platformhttpassessment.modules;

import dagger.Module;
import dagger.Provides;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;

import java.util.concurrent.TimeUnit;

@Module
public class MainModule {
    @Provides
    HttpClient provideHttpClient() {
//        return HttpClientBuilder.create()
//                .setConnectionTimeToLive(4L, TimeUnit.SECONDS)
//                .build();
        return HttpClients.createSystem();
    }
}
