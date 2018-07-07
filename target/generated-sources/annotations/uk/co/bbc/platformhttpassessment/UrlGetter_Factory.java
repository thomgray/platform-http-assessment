package uk.co.bbc.platformhttpassessment;

import dagger.internal.Factory;
import javax.annotation.Generated;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class UrlGetter_Factory implements Factory<UrlGetter> {
  private static final UrlGetter_Factory INSTANCE = new UrlGetter_Factory();

  @Override
  public UrlGetter get() {
    return provideInstance();
  }

  public static UrlGetter provideInstance() {
    return new UrlGetter();
  }

  public static UrlGetter_Factory create() {
    return INSTANCE;
  }

  public static UrlGetter newUrlGetter() {
    return new UrlGetter();
  }
}
