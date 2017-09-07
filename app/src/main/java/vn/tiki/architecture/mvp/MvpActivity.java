package vn.tiki.architecture.mvp;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Giang Nguyen on 9/4/17.
 */

public class MvpActivity<V extends Mvp.View, P extends Mvp.Presenter<V>> extends AppCompatActivity {

  @Nullable Binder<V, P> binder;

  /**
   * Connect Presenter to View then Presenter will attach/detach view and destroy base on lifecycle.
   * NOTE: this must be called before {@link Activity#onResume()} method.
   * @param presenter the Presenter
   * @param view the View
   */
  public void connect(P presenter, V view) {
    binder = new Binder<>(presenter, view);
  }

  @Override protected void onResume() {
    super.onResume();
    if (binder != null) {
      binder.bind();
    }
  }

  @Override protected void onPause() {
    super.onPause();
    if (binder != null) {
      binder.unbind();
    }
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    if (binder != null) {
      binder.destroy();
    }
  }
}
