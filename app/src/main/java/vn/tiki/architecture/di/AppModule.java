package vn.tiki.architecture.di;

import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import vn.tiki.architecture.model.UserModel;
import vn.tiki.architecture.util.EmailValidator;

/**
 * Created by Giang Nguyen on 8/25/17.
 */
@Module
public class AppModule {
  @Singleton @Provides UserModel providerUserModel() {
    return new UserModel();
  }

  @Singleton @Provides EmailValidator providerEmailValidator() {
    return new EmailValidator();
  }
}
