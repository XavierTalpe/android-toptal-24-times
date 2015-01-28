package be.xvrt.times.model;

import java.util.List;

import com.parse.ParseUser;

import android.test.ApplicationTestCase;
import be.xvrt.times.ParseApplication;
import be.xvrt.times.model.ClocksStore.ClocksStoreListener;
import be.xvrt.times.uil.ParseTestUtil;

public class ClocksStoreTest extends ApplicationTestCase<ParseApplication> {

    private ClocksStore clocksStore;

    private int[] calledListenersCount;

    private Clock brussels;

    public ClocksStoreTest() {
        super(ParseApplication.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();

        ParseUser user = ParseTestUtil.getTestUser();
        clocksStore = new ClocksStore(user);

        Thread.sleep(2000); // Wait for initial query to finish.

        calledListenersCount = new int[]{0};
        clocksStore.addListener(new ClocksStoreListener() {
            @Override
            public void onClocksStoreUpdated() {
                calledListenersCount[0]++;
            }
        });

        brussels = new Clock();
        brussels.setUser(user);
        brussels.setCity("Brussels");
        brussels.setTimezone("GMT+1");
    }

    @Override
    protected void tearDown() throws Exception {
        clocksStore.clear();

        Thread.sleep(2000);
        ParseUser.logOut();

        super.tearDown();
    }

    public void testEmptyStore() throws Exception {
        assertEquals(0, clocksStore.getCount());
        assertEquals(0, calledListenersCount[0]);
    }

    public void testClockIsAddedRemoved() throws Exception {
        assertEquals(0, clocksStore.getCount());
        assertEquals(0, calledListenersCount[0]);

        clocksStore.add(brussels);

        assertEquals(1, clocksStore.getCount());
        assertEquals(1, calledListenersCount[0]);

        clocksStore.remove(brussels);

        assertEquals(0, clocksStore.getCount());
        assertEquals(2, calledListenersCount[0]);
    }

    public void testClockIsUpdatedAndRemoved() throws Exception {
        clocksStore.add(brussels);

        brussels.setTimezone("GMT+5");
        brussels.setCity("Washington");

        clocksStore.update(brussels);

        assertEquals(1, clocksStore.getCount());
        assertEquals(2, calledListenersCount[0]);

        clocksStore.remove(brussels);

        assertEquals(0, clocksStore.getCount());
        assertEquals(3, calledListenersCount[0]);
    }

    public void testQuery() throws Exception {
        List<Clock> clocks = clocksStore.queryAll();
        assertEquals(0, clocks.size());

        clocksStore.add(brussels);

        clocks = clocksStore.queryAll();
        assertEquals(1, clocks.size());

        clocks = clocksStore.query("Brussels");
        assertEquals(1, clocks.size());

        clocks = clocksStore.query("Washington");
        assertEquals(0, clocks.size());
    }

}
