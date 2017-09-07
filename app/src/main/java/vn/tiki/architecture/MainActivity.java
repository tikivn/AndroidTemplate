package vn.tiki.architecture;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import vn.tiki.architecture.extra.ExtraInjectionActivity_;
import vn.tiki.architecture.login.LoginActivity;

public class MainActivity extends AppCompatActivity {

  private static final String TAG = "MainActivity";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Log.d(TAG, "onCreate() called with: savedInstanceState = [" + savedInstanceState + "]");
  }

  public void openExtraInjection(View view) {
    final Intent intent = ExtraInjectionActivity_.intentBuilder(this)
        .name("Giang")
        .age(29)
        .make();
    startActivity(intent);
  }

  public void openLogin(View view) {
    startActivity(LoginActivity.intent(this));
  }
}
