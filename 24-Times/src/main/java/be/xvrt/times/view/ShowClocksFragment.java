package be.xvrt.times.view;

import com.parse.ParseUser;

import android.app.ActionBar;
import android.app.Fragment;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;
import android.widget.ProgressBar;
import be.xvrt.times.R;
import be.xvrt.times.controller.EditClockController;
import be.xvrt.times.model.Clock;
import be.xvrt.times.model.ClocksStore;
import butterknife.ButterKnife;
import butterknife.InjectView;

public final class ShowClocksFragment extends Fragment {

    public static final String TAG = "ShowClocks";
    @InjectView(R.id.progressBar)
    ProgressBar progressBar;
    @InjectView(R.id.clocksLst)
    ListView clocksList;
    private ClocksStore clocksStore;

    public ShowClocksFragment() {
    }

    public ClocksStore getClocksStore() {
        return clocksStore;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.show_clocks_fragment, container, false);

        ButterKnife.inject(this, view);
        registerForContextMenu(clocksList);

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

        final ClocksAdapter adapter = new ClocksAdapter(getActivity(), clocksStore);
        adapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                showClocks();
            }

            @Override
            public void onInvalidated() {
                showClocks();
            }

            private void showClocks() {
                progressBar.setVisibility(View.GONE);
                clocksList.setVisibility(View.VISIBLE);
                adapter.unregisterDataSetObserver(this);
            }
        });

        clocksList.setAdapter(adapter);
    }

    @Override
    public void onStop() {
        super.onStop();

        clocksStore = null;
        clocksList.setAdapter(null);
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
                Clock clockToEdit = clocksStore.getClock(selectedItem);

                EditClockDialog editClockDialog = new EditClockDialog();
                editClockDialog.setEditObject(clockToEdit);
                editClockDialog.setResultListener(new EditClockController(getActivity()));
                editClockDialog.show(getFragmentManager(), null);

                return true;
            case 2:
                Clock clockToDelete = clocksStore.getClock(selectedItem);
                clocksStore.remove(clockToDelete);
                return true;
        }

        return false;
    }

}
