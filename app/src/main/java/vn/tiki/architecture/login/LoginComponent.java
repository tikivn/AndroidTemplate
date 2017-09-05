package vn.tiki.architecture.login;

import dagger.Subcomponent;

/**
 * Created by Giang Nguyen on 8/25/17.
 */
@Subcomponent(modules = LoginModule.class)
public interface LoginComponent {
  void inject(LoginActivity __);
}
