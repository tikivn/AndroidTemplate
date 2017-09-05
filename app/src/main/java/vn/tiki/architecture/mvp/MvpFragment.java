package vn.tiki.architecture.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

/**
 * Created by Giang Nguyen on 9/4/17.
 */

public class MvpFragment<V extends Mvp.View, P extends Mvp.Presenter<V>> extends Fragment {

  @Nullable Binder<V, P> binder;

  public void connect(P presenter, V view) {
    binder = new Binder<>(presenter, view);
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    if (binder != null) {
      binder.bind();
    }
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    if (binder != null) {
      binder.unbind();
    }
  }

  @Override public void onDestroy() {
    super.onDestroy();
    if (binder != null) {
      binder.destroy();
    }
  }
}
