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
import android.widget.TextView;
import be.xvrt.clocks.app.R;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public final class RegisterFragment extends Fragment {

    @InjectView(R.id.errorTxt)
    TextView errorView;

    @InjectView(R.id.emailTxt)
    TextView emailView;

    @InjectView(R.id.passwordTxt)
    TextView passwordView;

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
    void handleLogin() {
        errorView.setVisibility(View.INVISIBLE);

        String email = emailView.getText().toString();
        String password = passwordView.getText().toString();

        ParseUser user = new ParseUser();
        user.setUsername(email);
        user.setPassword(password);
        user.setEmail(email);

        user.signUpInBackground(new SignUpCallback() {
            public void done(ParseException exception) {
                if (exception == null) {
                    handleLoginSuccess();
                } else {
                    setErrorMessage(exception.getMessage());
                }
            }
        });
    }

    private void setErrorMessage(String message) {
        errorView.setText(message);
        errorView.setVisibility(View.VISIBLE);
    }

    private void handleLoginSuccess() {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.popBackStack();
        fragmentManager.beginTransaction()
                       .replace(R.id.container, new ShowClocksFragment(), "main")
                       .commit();
    }

}
