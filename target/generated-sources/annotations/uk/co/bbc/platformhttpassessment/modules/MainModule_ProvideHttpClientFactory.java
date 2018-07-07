package uk.co.bbc.platformhttpassessment.modules;

import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;
import org.apache.http.client.HttpClient;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class MainModule_ProvideHttpClientFactory implements Factory<HttpClient> {
  private final MainModule module;

  public MainModule_ProvideHttpClientFactory(MainModule module) {
    this.module = module;
  }

  @Override
  public HttpClient get() {
    return provideInstance(module);
  }

  public static HttpClient provideInstance(MainModule module) {
    return proxyProvideHttpClient(module);
  }

  public static MainModule_ProvideHttpClientFactory create(MainModule module) {
    return new MainModule_ProvideHttpClientFactory(module);
  }

  public static HttpClient proxyProvideHttpClient(MainModule instance) {
    return Preconditions.checkNotNull(
        instance.provideHttpClient(), "Cannot return null from a non-@Nullable @Provides method");
  }
}
