package be.xvrt.times.view;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import be.xvrt.times.R;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public final class RegisterFragment extends Fragment {

    public static final String TAG = "Register";

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.register_fragment, container, false);

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
            handleError("user name cannot be empty");
        } else if (password.length() == 0) {
            handleError("password cannot be empty");
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
                        handleError(exception.getMessage());
                    }
                }
            });
        }
    }

    private void handleError(String message) {
        registerBtn.setEnabled(true);
        emailView.setEnabled(true);
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

}
