package vn.tiki.architecture;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import vn.tiki.architecture.dagger.ActivityInjector;
import vn.tiki.architecture.dagger.AppInjector;
import vn.tiki.architecture.dagger.Daggers;
import vn.tiki.architecture.di.AppComponent;
import vn.tiki.architecture.di.AppModule;
import vn.tiki.architecture.di.DaggerAppComponent;
import vn.tiki.architecture.misc.SimpleActivityLifecycleCallbacks;

/**
 * Created by Giang Nguyen on 8/25/17.
 */

public class App extends Application implements AppInjector {
  private AppComponent appComponent;

  public static App from(Activity activity) {
    return (App) activity.getApplicationContext();
  }

  @Override public void onCreate() {
    super.onCreate();
    appComponent = DaggerAppComponent.builder()
        .appModule(new AppModule())
        .build();

    setupDagger();
  }

  protected void setupDagger() {
    Daggers.installAppInjector(this);

    registerActivityLifecycleCallbacks(new SimpleActivityLifecycleCallbacks() {
      @Override public void onActivityCreated(Activity activity, Bundle bundle) {
        if (activity instanceof ActivityInjector) {
          Daggers.installActivityInjector((ActivityInjector) activity);
        }
      }

      @Override public void onActivityDestroyed(Activity activity) {
        if (activity instanceof ActivityInjector) {
          Daggers.uninstallActivityInjector((ActivityInjector) activity);
        }
      }
    });
  }

  @Override public Object appComponent() {
    return appComponent;
  }
}
