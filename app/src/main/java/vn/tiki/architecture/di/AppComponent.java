package vn.tiki.architecture.di;

import dagger.Component;
import javax.inject.Singleton;
import vn.tiki.architecture.login.LoginComponent;
import vn.tiki.architecture.login.LoginModule;

/**
 * Created by Giang Nguyen on 8/25/17.
 */
@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
  LoginComponent plus(LoginModule __);
}
