package vn.tiki.architecture.login;

import dagger.Module;
import dagger.Provides;
import vn.tiki.architecture.di.ActivityScope;
import vn.tiki.architecture.model.UserModel;

/**
 * Created by Giang Nguyen on 8/25/17.
 */
@Module
public class LoginModule {

  @ActivityScope
  @Provides LoginPresenter provideLoginPresenter(UserModel userModel) {
    return new LoginPresenter(userModel);
  }
}
