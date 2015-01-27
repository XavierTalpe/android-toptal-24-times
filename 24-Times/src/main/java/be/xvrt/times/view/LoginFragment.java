package be.xvrt.times.view;

import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import be.xvrt.times.R;
import be.xvrt.times.controller.LoginFragmentController;
import butterknife.ButterKnife;

public final class LoginFragment extends Fragment {

    public static final String TAG = "Login";

    private LoginFragmentController controller;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.login_fragment, container, false);

        controller = new LoginFragmentController(this.getFragmentManager());
        ButterKnife.inject(controller, view);

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

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        controller = null;
    }

}
