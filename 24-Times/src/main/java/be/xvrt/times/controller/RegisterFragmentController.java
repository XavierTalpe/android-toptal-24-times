package be.xvrt.times.controller;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import android.app.FragmentManager;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;
import be.xvrt.times.R;
import be.xvrt.times.view.ShowClocksFragment;
import butterknife.InjectView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public final class RegisterFragmentController {

    private final FragmentManager fragmentManager;

    @InjectView(R.id.progressBar)
    ProgressBar progressBar;

    @InjectView(R.id.errorTxt)
    TextView errorView;

    @InjectView(R.id.emailTxt)
    TextView emailView;

    @InjectView(R.id.passwordTxt)
    TextView passwordView;

    @InjectView(R.id.showPasswordCbx)
    CheckBox showPasswordCbx;

    @InjectView(R.id.registerBtn)
    TextView registerBtn;

    public RegisterFragmentController(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    @OnCheckedChanged(R.id.showPasswordCbx)
    void toggleShowPassword() {
        TransformationMethod currentTransformationMethod = passwordView.getTransformationMethod();

        if (currentTransformationMethod instanceof HideReturnsTransformationMethod) {
            passwordView.setTransformationMethod(PasswordTransformationMethod.getInstance());
        } else {
            passwordView.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        }
    }

    @OnClick(R.id.registerBtn)
    void handleRegister() {
        setViewEnabled(false);

        String email = emailView.getText().toString();
        String password = passwordView.getText().toString();

        if (email.length() == 0) {
            showError("user name cannot be empty");
        } else if (password.length() == 0) {
            showError("password cannot be empty");
        } else {
            signUp(email, password);
        }
    }

    private void signUp(String email, String password) {
        ParseUser user = new ParseUser();
        user.setUsername(email);
        user.setPassword(password);
        user.setEmail(email);

        user.signUpInBackground(new SignUpCallback() {
            public void done(ParseException exception) {
                if (exception == null) {
                    handleSuccess();
                } else {
                    handleError(exception);
                }
            }
        });
    }

    private void handleError(ParseException exception) {
        String message = exception.getMessage();
        if (message.contains("HttpHostConnectException") ||
            message.contains("ConnectTimeoutException")) {
            message = "no network connection available";
        }

        showError(message);
    }

    private void showError(String message) {
        setViewEnabled(true);

        errorView.setText(message);
        errorView.setVisibility(View.VISIBLE);
    }

    private void handleSuccess() {
        ShowClocksFragment showClocksFragment = new ShowClocksFragment();
        showClocksFragment.setIsFirstTimeUser(true);

        fragmentManager.popBackStack();
        fragmentManager.beginTransaction()
                       .replace(R.id.main_fragment_container, showClocksFragment, ShowClocksFragment.TAG)
                       .commit();
    }

    private void setViewEnabled(boolean enabled) {
        registerBtn.setEnabled(enabled);
        emailView.setEnabled(enabled);
        passwordView.setEnabled(enabled);
        showPasswordCbx.setEnabled(enabled);

        if (enabled) {
            progressBar.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.VISIBLE);
            errorView.setVisibility(View.GONE);
        }
    }

}
