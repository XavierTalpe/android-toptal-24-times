package be.xvrt.times.model;

import java.util.ArrayList;
import java.util.List;

import com.parse.ParseUser;

// TODO: Try everything from UI thread.
public final class ClocksStore {

    public static final String KEY_ALL_CLOCKS = "clocks";
    private final ParseUser user;
    private final List<Clock> clocks;

    private final List<ClocksStoreListener> listeners;

    public ClocksStore(ParseUser user) {
        this.user = user;
        this.user.pinInBackground();

        this.clocks = getClocksFromServer(user);
        this.listeners = new ArrayList<ClocksStoreListener>();
    }

    private List<Clock> getClocksFromServer(ParseUser user) {
        List<Clock> clocks = user.getList(KEY_ALL_CLOCKS);
        if (clocks == null) {
            // TODO: Move
            //            Clock exampleClock = new Clock("GMT+1", "Brussels");
            //
            //            clocks.add(exampleClock);
            clocks = new ArrayList<Clock>();
        }

        return clocks;
    }

    public void clear() {
        for (Clock clock : clocks) {
            clock.deleteEventually();
        }

        clocks.clear();

        user.put(KEY_ALL_CLOCKS, clocks);
        user.saveEventually();
    }

    public int getCount() {
        return clocks.size();
    }

    public Clock getClock(int index) {
        return clocks.get(index);
    }

    public void add(Clock clock) {
        clocks.add(clock);

        user.put(KEY_ALL_CLOCKS, clocks);
        user.saveEventually();

        notifyListeners();
    }

    public void update(Clock clock) {
        clock.saveEventually();

        notifyListeners();
    }

    public void remove(Clock clock) {
        clocks.remove(clock);

        user.put(KEY_ALL_CLOCKS, clocks);
        user.saveEventually();

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
