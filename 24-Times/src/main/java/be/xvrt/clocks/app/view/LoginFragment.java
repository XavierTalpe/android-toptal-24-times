package be.xvrt.clocks.app.view;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import be.xvrt.clocks.app.R;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public final class LoginFragment extends Fragment {

    @InjectView(R.id.errorTxt)
    TextView errorView;

    @InjectView(R.id.usernameTxt)
    TextView usernameView;

    @InjectView(R.id.passwordTxt)
    TextView passwordView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.login_fragment, container, false);

        ButterKnife.inject(this, view);

        return view;
    }

    @OnClick(R.id.loginBtn)
    void handleLogin() {
        errorView.setVisibility(View.INVISIBLE);

        String username = usernameView.getText().toString();
        String password = passwordView.getText().toString();

        // TODO: Extract as class?
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException exception) {
                if (user == null) {
                    errorView.setText(exception.getMessage());
                    errorView.setVisibility(View.VISIBLE);
                } else {
                    FragmentManager fragmentManager = getFragmentManager();
                    //                    fragmentManager.popBackStack();
                    fragmentManager.beginTransaction()
                                   .replace(R.id.container, new ShowClocksFragment())
                                   .commit();
                }
            }
        });
    }

    @OnClick(R.id.registerTxt)
    void handleRegister() {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                       .replace(R.id.container, new RegisterFragment())
                       .addToBackStack(null)
                       .commit();
    }

}
