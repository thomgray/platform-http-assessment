package uk.co.bbc.platformhttpassessment;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class Application_Factory implements Factory<Application> {
  private final Provider<UrlGetter> urlGetterProvider;

  public Application_Factory(Provider<UrlGetter> urlGetterProvider) {
    this.urlGetterProvider = urlGetterProvider;
  }

  @Override
  public Application get() {
    return provideInstance(urlGetterProvider);
  }

  public static Application provideInstance(Provider<UrlGetter> urlGetterProvider) {
    return new Application(urlGetterProvider.get());
  }

  public static Application_Factory create(Provider<UrlGetter> urlGetterProvider) {
    return new Application_Factory(urlGetterProvider);
  }

  public static Application newApplication(UrlGetter urlGetter) {
    return new Application(urlGetter);
  }
}
