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
        loginBtn.setEnabled(false);
        usernameView.setEnabled(false);
        passwordView.setEnabled(false);
        registerTxt.setEnabled(false);

        progressBar.setVisibility(View.VISIBLE);
        errorView.setVisibility(View.GONE);

        String username = usernameView.getText().toString();
        String password = passwordView.getText().toString();

        if (username.length() == 0) {
            showError("user name cannot be empty");
        } else if (password.length() == 0) {
            showError("password cannot be empty");
        } else {
            ParseUser.logInInBackground(username, password, new LogInCallback() {
                @Override
                public void done(ParseUser user, ParseException exception) {
                    if (user == null) {
                        showError(exception.getMessage());
                    } else {
                        handleSuccess();
                    }
                }
            });
        }
    }

    private void showError(String message) {
        loginBtn.setEnabled(true);
        usernameView.setEnabled(true);
        passwordView.setEnabled(true);
        registerTxt.setEnabled(true);

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

    @OnClick(R.id.registerTxt)
    void handleRegister() {
        fragmentManager.beginTransaction()
                       .replace(R.id.main_fragment_container, new RegisterFragment(), RegisterFragment.TAG)
                       .addToBackStack(null)
                       .commit();
    }

}
