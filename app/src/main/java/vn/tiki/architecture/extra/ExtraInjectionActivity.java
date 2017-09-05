package vn.tiki.architecture.extra;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import vn.tiki.architecture.R;
import vn.tiki.architecture.annotation.Extra;

/**
 * Created by Giang Nguyen on 9/3/17.
 */
public class ExtraInjectionActivity extends AppCompatActivity {

  @BindView(R.id.tvName) TextView tvName;
  @BindView(R.id.tvAge) TextView tvAge;

  @Extra String name;
  @Extra int age;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_extra_injection);
    ButterKnife.bind(this);
    ExtraInjectionActivity_.bindExtras(this);

    tvName.setText(name);
    tvAge.setText(String.valueOf(age));
  }
}
