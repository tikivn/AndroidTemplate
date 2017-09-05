package vn.tiki.architecture.di;

import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import vn.tiki.architecture.model.UserModel;

/**
 * Created by Giang Nguyen on 8/25/17.
 */
@Module
public class AppModule {
  @Singleton @Provides public UserModel providerUserModel() {
    return new UserModel();
  }
}
