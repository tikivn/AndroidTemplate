
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
1. **ButterKnife** first

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

2. **Extras** second


# Problems

- 1 activity have many presenters (e.g. many
- all presenters need to call destroy when activity.onDestroy()