package be.xvrt.times.view;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import be.xvrt.clocks.app.R;

public class ShowClocksFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_show_clocks, container, false);
        TextView viewById = (TextView) rootView.findViewById(R.id.text_view);
        viewById.setText("Xavier Is logged in!");
        return rootView;
    }

}
