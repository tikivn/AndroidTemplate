package vn.tiki.architecture;

import vn.tiki.architecture.di.AppComponent;
import vn.tiki.architecture.di.AppModule;
import vn.tiki.architecture.di.DaggerAppComponent;

/**
 * Created by Giang Nguyen on 9/5/17.
 */

public class TestApplication extends App {

  private AppComponent mockedAppComponent;

  public void setAppModule(AppModule appModule) {
    mockedAppComponent = DaggerAppComponent.builder()
        .appModule(appModule)
        .build();
  }

  @Override public AppComponent getAppComponent() {
    return mockedAppComponent;
  }
}
