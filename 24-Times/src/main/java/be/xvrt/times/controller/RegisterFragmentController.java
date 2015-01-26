package be.xvrt.times.controller;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import android.app.FragmentManager;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.view.View;
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
        registerBtn.setEnabled(false);
        emailView.setEnabled(false);
        passwordView.setEnabled(false);

        progressBar.setVisibility(View.VISIBLE);
        errorView.setVisibility(View.GONE);

        String email = emailView.getText().toString();
        String password = passwordView.getText().toString();

        if (email.length() == 0) {
            showError("user name cannot be empty");
        } else if (password.length() == 0) {
            showError("password cannot be empty");
        } else {
            ParseUser user = new ParseUser();
            user.setUsername(email);
            user.setPassword(password);
            user.setEmail(email);

            user.signUpInBackground(new SignUpCallback() {
                public void done(ParseException exception) {
                    if (exception == null) {
                        handleSuccess();
                    } else {
                        showError(exception.getMessage());
                    }
                }
            });
        }
    }

    private void showError(String message) {
        registerBtn.setEnabled(true);
        emailView.setEnabled(true);
        passwordView.setEnabled(true);

        progressBar.setVisibility(View.GONE);

        errorView.setText(message);
        errorView.setVisibility(View.VISIBLE);
    }

    private void handleSuccess() {
        fragmentManager.popBackStack();
        fragmentManager.beginTransaction()
                       .replace(R.id.main_fragment_container, new ShowClocksFragment(), ShowClocksFragment.TAG)
                       .commit();
    }

}
