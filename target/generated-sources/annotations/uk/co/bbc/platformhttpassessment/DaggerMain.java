package uk.co.bbc.platformhttpassessment;

import dagger.internal.Preconditions;
import javax.annotation.Generated;
import uk.co.bbc.platformhttpassessment.modules.MainModule;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class DaggerMain extends Main {
  private DaggerMain(Builder builder) {}

  public static Builder builder() {
    return new Builder();
  }

  public static Main create() {
    return new Builder().build();
  }

  public static final class Builder {
    private Builder() {}

    public Main build() {
      return new DaggerMain(this);
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
