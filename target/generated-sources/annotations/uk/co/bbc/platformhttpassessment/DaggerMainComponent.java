package uk.co.bbc.platformhttpassessment;

import dagger.internal.Preconditions;
import javax.annotation.Generated;
import uk.co.bbc.platformhttpassessment.modules.MainModule;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class DaggerMainComponent implements MainComponent {
  private DaggerMainComponent(Builder builder) {}

  public static Builder builder() {
    return new Builder();
  }

  public static MainComponent create() {
    return new Builder().build();
  }

  @Override
  public Application getApplication() {
    return new Application(new UrlGetter());
  }

  public static final class Builder {
    private Builder() {}

    public MainComponent build() {
      return new DaggerMainComponent(this);
    }

    /**
     * @deprecated This module is declared, but an instance is not used in the component. This
     *     method is a no-op. For more, see https://google.github.io/dagger/unused-modules.
     */
    @Deprecated
    public Builder mainModule(MainModule mainModule) {
      Preconditions.checkNotNull(mainModule);
      return this;
    }
  }
}
