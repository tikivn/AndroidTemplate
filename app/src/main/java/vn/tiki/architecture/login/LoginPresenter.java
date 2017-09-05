package vn.tiki.architecture.login;

import android.support.annotation.NonNull;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import vn.tiki.architecture.model.UserModel;
import vn.tiki.architecture.mvp.ViewAction;
import vn.tiki.architecture.mvp.rx.RxBasePresenter;
import vn.tiki.architecture.util.EmailValidator;
import vn.tiki.architecture.util.PasswordValidator;

/**
 * Created by Giang Nguyen on 8/25/17.
 */

class LoginPresenter extends RxBasePresenter<LoginView> {

  @NonNull private final PublishSubject<String> emails = PublishSubject.create();
  @NonNull private final PublishSubject<String> passwords = PublishSubject.create();
  @NonNull private final UserModel userModel;

  private String email;
  private String password;

  LoginPresenter(@NonNull final UserModel userModel) {
    this.userModel = userModel;
    final EmailValidator emailValidator = new EmailValidator();
    final PasswordValidator passwordValidator = new PasswordValidator();

    final Observable<Boolean> validateEmail = emails
        .doOnNext(new Consumer<String>() {
          @Override public void accept(String s) throws Exception {
            email = s;
          }
        })
        .map(new Function<String, Boolean>() {
          @Override public Boolean apply(@io.reactivex.annotations.NonNull String s)
              throws Exception {
            return emailValidator.validate(s);
          }
        });

    final Observable<Boolean> validatePassword = passwords
        .doOnNext(new Consumer<String>() {
          @Override public void accept(String s) throws Exception {
            password = s;
          }
        })
        .map(new Function<String, Boolean>() {
          @Override public Boolean apply(@io.reactivex.annotations.NonNull String s)
              throws Exception {
            return passwordValidator.validate(s);
          }
        });

    final Disposable emailValidation = validateEmail
        .distinctUntilChanged()
        .subscribe(new Consumer<Boolean>() {
          @Override public void accept(Boolean valid) throws Exception {
            if (valid) {
              getViewOrThrow().hideValidationEmailError();
            } else {
              getViewOrThrow().showValidationEmailError();
            }
          }
        });

    final Disposable passwordValidation = validatePassword
        .distinctUntilChanged()
        .subscribe(new Consumer<Boolean>() {
          @Override public void accept(Boolean valid) throws Exception {
            if (valid) {
              getViewOrThrow().hideValidationPasswordError();
            } else {
              getViewOrThrow().showValidationPasswordError();
            }
          }
        });

    final Disposable loginValidation = Observable.combineLatest(
        validateEmail,
        validatePassword,
        new BiFunction<Boolean, Boolean, Boolean>() {
          @Override public Boolean apply(
              @io.reactivex.annotations.NonNull Boolean validEmail,
              @io.reactivex.annotations.NonNull Boolean validPassword) throws Exception {
            return validEmail && validPassword;
          }
        })
        .subscribe(new Consumer<Boolean>() {
          @Override public void accept(Boolean validSubmit) throws Exception {
            if (validSubmit) {
              getViewOrThrow().enableSubmit();
            } else {
              getViewOrThrow().disableSubmit();
            }
          }
        });

    disposeOnDestroy(emailValidation, passwordValidation, loginValidation);
  }

  @Override public void attach(LoginView view) {
    super.attach(view);
  }

  void onInputEmail(String email) {
    emails.onNext(email);
  }

  void onInputPassword(String password) {
    passwords.onNext(password);
  }

  void onClickLogin() {
    final LoginView view = getViewOrThrow();
    view.disableSubmit();
    view.hideAuthenticationError();
    view.showLoading();

    disposeOnDestroy(userModel.login(email, password)
        .map(new Function<Boolean, Boolean>() {
          @Override public Boolean apply(@io.reactivex.annotations.NonNull Boolean aBoolean)
              throws Exception {
            if (!aBoolean) {
              throw new Exception("authentication failed");
            }
            return true;
          }
        })
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
            new Consumer<Boolean>() {
              @Override public void accept(final Boolean aBoolean) throws Exception {
                sendToView(new ViewAction<LoginView>() {
                  @Override public void call(@NonNull LoginView view) {
                    view.showLoginSuccess();
                  }
                });
              }
            },
            new Consumer<Throwable>() {
              @Override public void accept(Throwable throwable) throws Exception {
                sendToView(new ViewAction<LoginView>() {
                  @Override public void call(@NonNull LoginView view) {
                    view.hideLoading();
                    view.enableSubmit();
                    view.showAuthenticationError();
                  }
                });
              }
            }));
  }
}
