package be.xvrt.times.controller;

import android.app.Fragment;
import android.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;
import be.xvrt.times.R;
import be.xvrt.times.view.ShowClocksFragment;

public final class FilterController {

    private final FragmentManager fragmentManager;

    private MenuItem clearFilterMenuItem;

    public FilterController(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public void setMenu(Menu menu) {
        clearFilterMenuItem = menu.findItem(R.id.clearFilterMenu);
    }

    public boolean setFilter(String cityFilter) {
        ShowClocksFragmentController controller = findShowClocksController();
        if (controller != null) {
            controller.setFilter(cityFilter);
        }

        if (clearFilterMenuItem != null) {
            clearFilterMenuItem.setEnabled(true);
        }

        return true;
    }

    private ShowClocksFragmentController findShowClocksController() {
        ShowClocksFragmentController result = null;

        Fragment fragment = fragmentManager.findFragmentByTag(ShowClocksFragment.TAG);
        if (fragment != null) {
            ShowClocksFragment showClocksFragment = (ShowClocksFragment) fragment;
            result = showClocksFragment.getController();
        }

        return result;
    }

    public boolean clearFilter() {
        ShowClocksFragmentController controller = findShowClocksController();
        if (controller != null) {
            controller.setFilter(null);
        }

        if (clearFilterMenuItem != null) {
            clearFilterMenuItem.setEnabled(false);
        }

        return true;
    }

}
