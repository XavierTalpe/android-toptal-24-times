package be.xvrt.times.controller;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import android.app.FragmentManager;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import be.xvrt.times.R;
import be.xvrt.times.view.RegisterFragment;
import be.xvrt.times.view.ShowClocksFragment;
import butterknife.InjectView;
import butterknife.OnClick;

public final class LoginFragmentController {

    private final FragmentManager fragmentManager;

    @InjectView(R.id.progressBar)
    ProgressBar progressBar;

    @InjectView(R.id.errorTxt)
    TextView errorView;

    @InjectView(R.id.usernameTxt)
    TextView usernameView;

    @InjectView(R.id.passwordTxt)
    TextView passwordView;

    @InjectView(R.id.loginBtn)
    TextView loginBtn;

    @InjectView(R.id.registerTxt)
    TextView registerTxt;

    public LoginFragmentController(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    @OnClick(R.id.loginBtn)
    void handleLogin() {
        setViewEnabled(false);

        String username = usernameView.getText().toString();
        String password = passwordView.getText().toString();

        if (username.length() == 0) {
            showError("user name cannot be empty");
        } else if (password.length() == 0) {
            showError("password cannot be empty");
        } else {
            login(username, password);
        }
    }

    private void login(String username, String password) {
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException exception) {
                if (user == null) {
                    handleError(exception);
                } else {
                    handleSuccess();
                }
            }
        });
    }

    private void handleSuccess() {
        fragmentManager.popBackStack();
        fragmentManager.beginTransaction()
                       .replace(R.id.main_fragment_container, new ShowClocksFragment(), ShowClocksFragment.TAG)
                       .commit();
    }

    private void handleError(ParseException exception) {
        String message = exception.getMessage();
        if (message.contains("HttpHostConnectException") ||
            message.contains("ConnectTimeoutException") ||
            message.contains("UnknownHostException")) {
            message = "no network connection available";
        }

        showError(message);
    }

    private void showError(String message) {
        setViewEnabled(true);

        errorView.setText(message);
        errorView.setVisibility(View.VISIBLE);
    }

    private void setViewEnabled(boolean enabled) {
        loginBtn.setEnabled(enabled);
        usernameView.setEnabled(enabled);
        passwordView.setEnabled(enabled);
        registerTxt.setEnabled(enabled);

        if (enabled) {
            progressBar.setVisibility(View.GONE);
        }
        else {
            progressBar.setVisibility(View.VISIBLE);
            errorView.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.registerTxt)
    void handleRegister() {
        fragmentManager.beginTransaction()
                       .replace(R.id.main_fragment_container, new RegisterFragment(), RegisterFragment.TAG)
                       .addToBackStack(null)
                       .commit();
    }

}
