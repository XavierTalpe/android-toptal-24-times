package be.xvrt.times.controller;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import be.xvrt.times.model.Clock;
import be.xvrt.times.model.ClocksStore;
import be.xvrt.times.view.NewClockDialog.ClockCreationListener;
import be.xvrt.times.view.ShowClocksFragment;

public final class NewClockController implements ClockCreationListener {

    private final ClocksStore clocksStore;

    public NewClockController(Activity activity) {
        FragmentManager fragmentManager = activity.getFragmentManager();
        Fragment showClocksFragment = fragmentManager.findFragmentByTag(ShowClocksFragment.TAG);

        if (showClocksFragment != null) {
            clocksStore = ((ShowClocksFragment) showClocksFragment).getClocksStore();
        } else {
            clocksStore = null;
        }
    }

    @Override
    public void onClockCreatedListener(Clock newClock) {
        if (clocksStore != null) {
            clocksStore.add(newClock);
        }
    }

}
