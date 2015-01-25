package be.xvrt.times.util;

import android.app.Fragment;
import android.app.FragmentManager;
import be.xvrt.times.R;

public final class FragmentUtil {

    private FragmentUtil() {
    }

    public static void addOnlyOnce(FragmentManager fragmentManager, Fragment fragment, String tag) {
        fragmentManager.executePendingTransactions();

        Fragment existingFragment = fragmentManager.findFragmentByTag(tag);
        if (existingFragment == null) {
            fragmentManager.beginTransaction()
                           .add(R.id.main_fragment_container, fragment, tag)
                           .commit();
        }
    }

}
