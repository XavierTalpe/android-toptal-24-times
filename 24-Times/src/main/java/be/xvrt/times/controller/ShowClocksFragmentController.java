package be.xvrt.times.controller;

import com.parse.ParseUser;

import android.app.FragmentManager;
import android.database.DataSetObserver;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import be.xvrt.times.R;
import be.xvrt.times.model.Clock;
import be.xvrt.times.model.ClocksStore;
import be.xvrt.times.model.Timezone;
import be.xvrt.times.view.ClocksAdapter;
import be.xvrt.times.view.EditClockDialog;
import butterknife.ButterKnife;
import butterknife.InjectView;

public final class ShowClocksFragmentController {

    @InjectView(R.id.progressBar)
    ProgressBar progressBar;

    @InjectView(R.id.clocksLst)
    ListView clocksList;

    private final FragmentManager fragmentManager;

    private final ClocksStore clocksStore;
    private final ClocksAdapter clocksAdapter;

    public ShowClocksFragmentController(View view, FragmentManager fragmentManager) {
        ButterKnife.inject(this, view);

        this.fragmentManager = fragmentManager;

        this.clocksStore = new ClocksStore(ParseUser.getCurrentUser());
        this.clocksAdapter = new ClocksAdapter(view.getContext(), clocksStore);
        this.clocksList.setAdapter(clocksAdapter);

        showLoadingAnimation();
    }

    public ClocksStore getClocksStore() {
        return clocksStore;
    }

    public void setFilter(String cityFilter) {
        clocksAdapter.setFilter(cityFilter);
    }

    private void showLoadingAnimation() {
        clocksAdapter.registerDataSetObserver(new DataSetObserver() {
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
                clocksAdapter.unregisterDataSetObserver(this);
            }
        });
    }

    public void addInitialContent() {
        Clock clock = new Clock();
        clock.setUser(ParseUser.getCurrentUser());
        clock.setCity("Brussels");
        clock.setTimezone(Timezone.CET.name());

        clocksStore.add(clock);
    }

    public void onDestroyView() {
        clocksList.setAdapter(null);
    }

    public boolean handleClockEdit(int selectedItem) {
        Clock clockToEdit = clocksStore.getClock(selectedItem);

        EditClockDialog editClockDialog = new EditClockDialog();
        editClockDialog.setEditObject(clockToEdit);
        editClockDialog.setResultListener(new EditClockController(fragmentManager));
        editClockDialog.show(fragmentManager, null);

        return true;
    }

    public boolean handleClockDelete(int selectedItem) {
        Clock clockToDelete = clocksStore.getClock(selectedItem);
        clocksStore.remove(clockToDelete);
        return true;
    }

}
