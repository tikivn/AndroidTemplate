package vn.tiki.architecture.mvp;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Giang Nguyen on 9/4/17.
 */

public class MvpActivity<V extends Mvp.View, P extends Mvp.Presenter<V>> extends AppCompatActivity {

  @Nullable Binder<V, P> binder;

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
