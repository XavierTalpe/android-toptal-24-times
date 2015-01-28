package be.xvrt.times;

import com.parse.ParseUser;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import be.xvrt.times.controller.EditClockController;
import be.xvrt.times.controller.FilterController;
import be.xvrt.times.util.FragmentUtil;
import be.xvrt.times.view.EditClockDialog;
import be.xvrt.times.view.LoginFragment;
import be.xvrt.times.view.SetFilterDialog;
import be.xvrt.times.view.ShowClocksFragment;


public class Main extends Activity {

    private FilterController filterController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.hide();

        filterController = new FilterController(getFragmentManager());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        filterController.setMenu(menu);

        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (ParseUser.getCurrentUser() == null) {
            FragmentUtil.addOnlyOnce(getFragmentManager(), new LoginFragment(), LoginFragment.TAG);
        } else {
            FragmentUtil.addOnlyOnce(getFragmentManager(), new ShowClocksFragment(), ShowClocksFragment.TAG);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean result = false;

        switch (item.getItemId()) {
            case R.id.addClockMenu:
                EditClockDialog editClockDialog = new EditClockDialog();
                editClockDialog.setResultListener(new EditClockController(getFragmentManager()));
                editClockDialog.show(getFragmentManager(), null);

                result = true;
                break;
            case R.id.setFilterMenu:
                SetFilterDialog setFilterDialog = new SetFilterDialog();
                setFilterDialog.setResultListener(filterController);
                setFilterDialog.show(getFragmentManager(), null);
                break;
            case R.id.clearFilterMenu:
                result = filterController.clearFilter();
                break;
            case R.id.logoutMenu:
                logout();

                result = true;
                break;
        }

        return result || super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        filterController = null;

        super.onDestroy();
    }

    private void logout() {
        ParseUser.logOut();

        getFragmentManager().beginTransaction()
                            .replace(R.id.main_fragment_container, new LoginFragment())
                            .commit();
    }

}
