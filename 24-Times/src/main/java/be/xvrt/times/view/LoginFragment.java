package be.xvrt.times.view;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import be.xvrt.times.R;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public final class LoginFragment extends Fragment {

    public static final String TAG = "Login";

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.login_fragment, container, false);

        ButterKnife.inject(this, view);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        ActionBar actionBar = getActivity().getActionBar();
        if (actionBar != null && actionBar.isShowing()) {
            actionBar.hide();
        }
    }

    @OnClick(R.id.loginBtn)
    void handleLogin() {
        loginBtn.setEnabled(false);
        usernameView.setEnabled(false);
        passwordView.setEnabled(false);

        progressBar.setVisibility(View.VISIBLE);
        errorView.setVisibility(View.GONE);

        String username = usernameView.getText().toString();
        String password = passwordView.getText().toString();

        if (username.length() == 0) {
            handleError("user name cannot be empty");
        } else if (password.length() == 0) {
            handleError("password cannot be empty");
        } else {
            ParseUser.logInInBackground(username, password, new LogInCallback() {
                @Override
                public void done(ParseUser user, ParseException exception) {
                    if (user == null) {
                        handleError(exception.getMessage());
                    } else {
                        handleSuccess();
                    }
                }
            });
        }
    }

    private void handleError(String message) {
        loginBtn.setEnabled(true);
        usernameView.setEnabled(true);
        passwordView.setEnabled(true);

        progressBar.setVisibility(View.GONE);

        errorView.setText(message);
        errorView.setVisibility(View.VISIBLE);
    }

    private void handleSuccess() {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.popBackStack();
        fragmentManager.beginTransaction()
                       .replace(R.id.main_fragment_container, new ShowClocksFragment(), ShowClocksFragment.TAG)
                       .commit();
    }

    @OnClick(R.id.registerTxt)
    void handleRegister() {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                       .replace(R.id.main_fragment_container, new RegisterFragment(), RegisterFragment.TAG)
                       .addToBackStack(null)
                       .commit();
    }

}
