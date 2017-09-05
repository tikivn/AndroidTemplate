package vn.tiki.architecture.mvp;

import android.support.annotation.CallSuper;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Giang Nguyen on 8/25/17.
 */

public class BasePresenter<V extends Mvp.View> implements Mvp.Presenter<V> {

  @NonNull private Queue<ViewAction<V>> pendingViewActions = new LinkedList<>();
  @Nullable private V view;

  @Nullable public V getView() {
    return view;
  }

  @NonNull public V getViewOrThrow() {
    final V view = getView();
    if (view == null) {
      throw new IllegalStateException("view not attached");
    }
    return view;
  }

  @MainThread
  public void sendToView(ViewAction<V> action) {
    if (view == null) {
      pendingViewActions.add(action);
    } else {
      action.call(view);
    }
  }

  @CallSuper
  @Override public void attach(V view) {
    this.view = view;
    if (pendingViewActions.isEmpty()) {
      return;
    }

    do {
      final ViewAction<V> action = pendingViewActions.remove();
      action.call(view);
    } while (pendingViewActions.size() > 0);
  }

  @CallSuper
  @Override public void detach() {
    this.view = null;
  }

  @Override public void destroy() {

  }
}
