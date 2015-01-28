package be.xvrt.times.controller;

import android.app.Fragment;
import android.app.FragmentManager;
import be.xvrt.times.model.Clock;
import be.xvrt.times.view.EditClockDialog.ClockEditListener;
import be.xvrt.times.view.ShowClocksFragment;

public final class EditClockController implements ClockEditListener {

    private final ShowClocksFragmentController controller;

    public EditClockController(FragmentManager fragmentManager) {
        Fragment fragment = fragmentManager.findFragmentByTag(ShowClocksFragment.TAG);
        if (fragment != null) {
            ShowClocksFragment showClocksFragment = (ShowClocksFragment) fragment;
            controller = showClocksFragment.getController();
        } else {
            controller = null;
        }
    }

    @Override
    public void onClockCreatedListener(Clock newClock) {
        if (controller != null) {
            controller.getClocksStore().add(newClock);
        }
    }

    @Override
    public void onClockUpdatedListener(Clock updatedClock) {
        if (controller != null) {
            controller.getClocksStore().update(updatedClock);
        }
    }

}
