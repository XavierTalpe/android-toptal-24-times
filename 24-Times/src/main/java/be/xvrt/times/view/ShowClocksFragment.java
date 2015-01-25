package be.xvrt.times.view;

import com.parse.ParseUser;

import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import be.xvrt.times.R;
import be.xvrt.times.model.ClocksStore;
import butterknife.ButterKnife;
import butterknife.InjectView;

public final class ShowClocksFragment extends Fragment {

    public static final String TAG = "ShowClocks";

    private ClocksStore clocksStore;

    @InjectView(R.id.clocksLst)
    ListView clocksList;

    public ShowClocksFragment() {
    }

    public ClocksStore getClocksStore() {
        return clocksStore;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.show_clocks_fragment, container, false);

        ButterKnife.inject(this, view);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        ActionBar actionBar = getActivity().getActionBar();
        if (actionBar != null && !actionBar.isShowing()) {
            actionBar.show();
        }

        clocksStore = new ClocksStore(ParseUser.getCurrentUser());
        clocksList.setAdapter(new ClocksAdapter(getActivity(), clocksStore));
    }

    @Override
    public void onStop() {
        super.onStop();

        clocksStore = null;
        clocksList.setAdapter(null);
    }

}
