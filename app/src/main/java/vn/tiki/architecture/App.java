package vn.tiki.architecture;

import android.app.Activity;
import android.app.Application;
import vn.tiki.architecture.di.AppComponent;
import vn.tiki.architecture.di.DaggerAppComponent;

/**
 * Created by Giang Nguyen on 8/25/17.
 */

public class App extends Application {
  private AppComponent appComponent;

  public static App from(Activity activity) {
    return (App) activity.getApplicationContext();
  }

  @Override public void onCreate() {
    super.onCreate();
    appComponent = DaggerAppComponent.builder()
        .build();
  }

  public AppComponent getAppComponent() {
    return appComponent;
  }
}
