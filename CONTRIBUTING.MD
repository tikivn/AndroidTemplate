# Contributing to Tiki's Android Template

## Coding Convention
### Naming
1. id -> [ViewType]Name

  - `tv` -> **TextView** (e.g. tvEmail)
  - `et` -> **EditText** (e.g. etPassword)
  - `bt` -> **Button** (e.g. btSubmit)
  - `iv` -> **ImageView** (e.g. ivThumb)
  - ...

2. strings.xml -> [Screen|Module]_name

  - `login_error_invalid_email`
  - `main_message_welcome`

3. classes

  - [Name]Activity -> Activity (e.g. LoginActivity)
  - [Name]Fragment -> Fragment (e.g. LoginFragment)

### Dependencies Injection
#### 1. ButterKnife first

  Butter knife can resolve most of references from the *R* (e.g. id, color, string. etc)

  ~~~java
  // View
  @BindView(R.id.btLogin) Button btLogin;
  @BindViews({ R.id.etEmail, R.id.etPassword }) List<View> inputViews;
  ...

  // String
  @BindString(R.string.msg_successful) String msgSuccessful;
  ...

  // Color
  @BindColor(R.color.colorAccent) int colorAccent;
  ...

  // Events
  @OnTextChanged(value = R.id.etEmail, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
  public void afterEmailInput(Editable editable) { ... }

  @OnClick(R.id.btLogin) public void onLoginClicked() { ... }
  ~~~

#### 2. Extras second

#### 3. Dagger third

- *AppComponent.plus* to creat sub-component
- *AnyComponent.inject* to inject dependencies
- *AppInjector* must be implemented by Application
- *ActivityInjector* must be implemented by Activity
- *Daggers.inject(Context, Object)* is alternative to `ActivityComponent.inject(Object)`
- *Daggers.inject(Context.getApplicationContext(), Object)* is alternative to `AppComponent.inject(Object)`

App Template

~~~java
[App.java]

public class App extends Application implements AppInjector {
  private AppComponent appComponent;

  @Override public void onCreate() {
    super.onCreate();

    // Create AppComponent first
    appComponent = DaggerAppComponent.builder()
        .appModule(new AppModule())
        .build();

    // Install AppInjector
    Daggers.installAppInjector(this);
    registerActivityLifecycleCallbacks(new SimpleActivityLifecycleCallbacks() {
      @Override public void onActivityCreated(Activity activity, Bundle bundle) {
        if (activity instanceof ActivityInjector) {
          Daggers.installActivityInjector((ActivityInjector) activity);
        }
      }

      @Override public void onActivityDestroyed(Activity activity) {
        if (activity instanceof ActivityInjector) {
          Daggers.uninstallActivityInjector((ActivityInjector) activity);
        }
      }
    });
  }

  @Override public Object appComponent() {
    return appComponent;
  }
}
~~~

Activity template

~~~java
public class ActivityTemplate extends AppCompatActivity implement ActivityInjector {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    // Inject dependencies.
    Daggers.inject(this, this);
  }
}
~~~
