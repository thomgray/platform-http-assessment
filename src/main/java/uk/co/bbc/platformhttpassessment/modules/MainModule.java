package uk.co.bbc.platformhttpassessment.modules;

import dagger.Module;
import dagger.Provides;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;

@Module
public class MainModule {
    @Provides
    HttpClient provideHttpClient() {
        return HttpClients.createDefault();
    }
}
