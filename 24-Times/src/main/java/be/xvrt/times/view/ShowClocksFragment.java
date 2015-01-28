package be.xvrt.times.view;

import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;
import be.xvrt.times.R;
import be.xvrt.times.controller.ShowClocksFragmentController;
import butterknife.ButterKnife;
import butterknife.InjectView;

public final class ShowClocksFragment extends Fragment {

    public static final String TAG = "ShowClocks";

    @InjectView(R.id.clocksLst)
    ListView clocksList;

    private boolean isFirstTimeUser;

    private ShowClocksFragmentController controller;

    public ShowClocksFragment() {
        isFirstTimeUser = false;
    }

    public ShowClocksFragmentController getController() {
        return controller;
    }

    public void setIsFirstTimeUser(boolean isFirstTimeUser) {
        this.isFirstTimeUser = isFirstTimeUser;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.show_clocks_fragment, container, false);
        ButterKnife.inject(this, view);

        registerForContextMenu(clocksList);

        controller = new ShowClocksFragmentController(view, getFragmentManager());
        if (isFirstTimeUser ) {
            controller.addInitialContent();
        }

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        ActionBar actionBar = getActivity().getActionBar();
        if (actionBar != null && !actionBar.isShowing()) {
            actionBar.show();
        }
    }

    @Override
    public void onDestroyView() {
        controller.onDestroyView();
        super.onDestroyView();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenuInfo menuInfo) {
        menu.add(0, 1, 1, R.string.editClock);
        menu.add(0, 2, 2, R.string.deleteClock);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterContextMenuInfo menuInfo = (AdapterContextMenuInfo) item.getMenuInfo();
        int selectedItem = menuInfo.position;

        switch (item.getItemId()) {
            case 1:
                return controller.handleClockEdit(selectedItem);
            case 2:
                return controller.handleClockDelete(selectedItem);
        }

        return false;
    }

}
