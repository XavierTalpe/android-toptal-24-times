package be.xvrt.times;

import com.parse.ParseUser;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import be.xvrt.times.util.FragmentUtil;
import be.xvrt.times.util.ParseUtil;
import be.xvrt.times.view.LoginFragment;
import be.xvrt.times.view.ShowClocksFragment;


public class Main extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ActionBar actionBar = getActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.hide();

        ParseUtil.initParse(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (ParseUser.getCurrentUser() == null) {
            FragmentUtil.addOnlyOnce(getFragmentManager(), new LoginFragment(), "login");
        } else {
            FragmentUtil.addOnlyOnce(getFragmentManager(), new ShowClocksFragment(), "main");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean result = false;

        switch (item.getItemId()) {
            case R.id.logoutMenu:
                logout();
                result = true;
                break;
        }

        return result || super.onOptionsItemSelected(item);
    }

    private void logout() {
        ParseUser.logOut();

        getFragmentManager().beginTransaction()
                            .replace(R.id.container, new LoginFragment())
                            .commit();
    }

}
