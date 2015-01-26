package be.xvrt.times.model;

import java.util.ArrayList;
import java.util.List;

import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import android.os.Handler;
import android.os.Looper;

public final class ClocksStore {

    private final List<Clock> clocks;

    private final List<ClocksStoreListener> listeners;

    public ClocksStore(ParseUser user) {
        this.clocks = new ArrayList<Clock>();
        this.listeners = new ArrayList<ClocksStoreListener>();

        syncWithRemote(user);
    }

    //    TODO: Improve logging
    private void syncWithRemote(ParseUser user) {
        ParseQuery<Clock> query = ParseQuery.getQuery(Clock.class);
        query.whereEqualTo(Clock.KEY_CREATED_BY, user);
        query.findInBackground(new FindCallback<Clock>() {
            @Override
            public void done(final List<Clock> list, ParseException exception) {
                if (exception != null) {
                    exception.printStackTrace();
                } else {
                    Handler mainHandler = new Handler(Looper.getMainLooper());
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            clocks.clear();
                            clocks.addAll(list);

                            notifyListeners();
                        }
                    });
                }
            }
        });
    }

    public void clear() {
        for (Clock clock : clocks) {
            clock.deleteEventually();
        }

        clocks.clear();
    }

    public int getCount() {
        return clocks.size();
    }

    public Clock getClock(int index) {
        return clocks.get(index);
    }

    public void add(Clock clock) {
        clocks.add(clock);

        clock.saveEventually(new SaveCallback() {
            @Override
            public void done(ParseException exception) {
                if (exception != null) {
                    exception.printStackTrace();
                }
            }
        });

        notifyListeners();
    }

    public void update(Clock clock) {
        clock.saveEventually(new SaveCallback() {
            @Override
            public void done(ParseException exception) {
                if (exception != null) {
                    exception.printStackTrace();
                }
            }
        });

        notifyListeners();
    }

    public void remove(Clock clock) {
        clocks.remove(clock);

        clock.deleteEventually(new DeleteCallback() {
            @Override
            public void done(ParseException exception) {
                if (exception != null) {
                    exception.printStackTrace();
                }
            }
        });

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
            listener.onClocksStoreUpdated();
        }
    }

    public interface ClocksStoreListener {

        public void onClocksStoreUpdated();

    }

}
