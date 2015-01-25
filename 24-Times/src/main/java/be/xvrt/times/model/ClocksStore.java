package be.xvrt.times.model;

import java.util.ArrayList;
import java.util.List;

import com.parse.ParseUser;

// TODO: Try everything from UI thread.
public final class ClocksStore {

    private final ParseUser user;
    private final List<Clock> clocks;

    private final List<ClocksStoreListener> listeners;

    public ClocksStore(ParseUser user) {
        this.user = user;
        this.clocks = getClocksFromServer(user);

        listeners = new ArrayList<ClocksStoreListener>();
    }

    private List<Clock> getClocksFromServer(ParseUser user) {
        List<Clock> clocks = user.getList("clocks");
        if (clocks == null) {
            // TODO: Move
            //            Clock exampleClock = new Clock("GMT+1", "Brussels");
            //
            //            clocks.add(exampleClock);
            clocks = new ArrayList<Clock>();

            user.add("clocks", clocks);
            user.saveInBackground();
        }

        return clocks;
    }

    public void clear() {
        for (Clock clock : clocks) {
            clock.deleteInBackground();
        }

        clocks.clear();
        user.saveInBackground();
    }

    public int getCount() {
        return clocks.size();
    }

    public Clock getClock( int index) {
        return clocks.get(index);
    }

    public void add(Clock clock) {
        clocks.add(clock);

        clock.saveInBackground();
        user.saveInBackground();

        notifyListeners();
    }

    public void update(Clock clock) {
        clock.saveInBackground();
        user.saveInBackground();

        notifyListeners();
    }

    public void remove(Clock clock) {
        clocks.remove(clock);

        user.saveInBackground();
        clock.deleteInBackground();

        notifyListeners();
    }

    public void addListener(ClocksStoreListener listener) {
        listeners.add(listener);
    }

    public void removeListener(ClocksStoreListener listener) {
        listeners.remove(listener);
    }

    private void notifyListeners() {
        for (ClocksStoreListener listener : listeners) {
            listener.onClocksStoreUpdated(clocks);
        }
    }

    public interface ClocksStoreListener {

        public void onClocksStoreUpdated(List<Clock> clocks);

    }

}
