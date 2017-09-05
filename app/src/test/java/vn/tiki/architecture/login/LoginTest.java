package vn.tiki.architecture.login;

import io.reactivex.Observable;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowToast;
import vn.tiki.architecture.BuildConfig;
import vn.tiki.architecture.RxSchedulerTestRule;
import vn.tiki.architecture.TestApplication;
import vn.tiki.architecture.di.AppModule;
import vn.tiki.architecture.model.UserModel;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Created by Giang Nguyen on 9/5/17.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, application = TestApplication.class)
public class LoginTest {

  @Rule public final RxSchedulerTestRule rxSchedulerTestRule = new RxSchedulerTestRule();

  @Mock UserModel userModel;

  private LoginActivity loginActivity;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    TestApplication testApplication = (TestApplication) RuntimeEnvironment.application;
    testApplication.setAppModule(new AppModule() {
      @Override public UserModel providerUserModel() {
        return userModel;
      }
    });

    loginActivity = Robolectric.buildActivity(LoginActivity.class)
        .create()
        .resume()
        .get();
  }

  @Test
  public void testInputInvalidEmail() throws Exception {
    loginActivity.etEmail.setText("foo@gmail");
    assertThat(loginActivity.tilEmail.getError()).isEqualTo(loginActivity.errorInvalidEmail);
  }

  @Test
  public void testInputValidEmail() throws Exception {
    loginActivity.etEmail.setText("foo@gmail.com");
    assertThat(loginActivity.tilEmail.getError()).isNull();
  }

  @Test
  public void testLoginFailure() throws Exception {
    final String email = "foo@gmail.com";
    final String password = "123456";
    when(userModel.login(
        anyString(),
        anyString())).thenReturn(Observable.<Boolean>error(new Exception("")));

    loginActivity.etEmail.setText(email);
    loginActivity.etPassword.setText(password);
    loginActivity.btLogin.performClick();

    assertThat(loginActivity.tilPassword.getError()).isEqualTo(loginActivity.errorWrongPassword);
  }

  @Test
  public void testLoginSuccess() throws Exception {
    final String email = "foo@gmail.com";
    final String password = "123456";
    when(userModel.login(
        anyString(),
        anyString())).thenReturn(Observable.just(true));

    loginActivity.etEmail.setText(email);
    loginActivity.etPassword.setText(password);
    loginActivity.btLogin.performClick();

    assertThat(ShadowToast.getTextOfLatestToast()).isEqualTo(loginActivity.msgSuccessful);
  }
}
