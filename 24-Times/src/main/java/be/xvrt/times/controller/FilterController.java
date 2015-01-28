package be.xvrt.times.controller;

import android.app.Fragment;
import android.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;
import be.xvrt.times.R;
import be.xvrt.times.view.SetFilterDialog.FilterEditListener;
import be.xvrt.times.view.ShowClocksFragment;

public final class FilterController implements FilterEditListener {

    private final FragmentManager fragmentManager;

    private MenuItem clearFilterMenuItem;

    public FilterController(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
        this.clearFilterMenuItem = null;
    }

    public void setMenu(Menu menu) {
        clearFilterMenuItem = menu.findItem(R.id.clearFilterMenu);
    }

    @Override
    public void onFilterUpdated(String filter) {
        setActiveFilter(filter);
    }

    public boolean clearFilter() {
        setActiveFilter(null);
        return true;
    }

    private void setActiveFilter(String filter) {
        ShowClocksFragmentController controller = findShowClocksController();
        if (controller != null) {
            controller.setFilter(filter);
        }

        if (clearFilterMenuItem != null) {
            clearFilterMenuItem.setEnabled(filter != null);
        }
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

}
