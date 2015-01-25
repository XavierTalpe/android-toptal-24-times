package be.xvrt.times.model;

import static be.xvrt.times.uil.ParseTestUtil.assertDeleted;

import java.util.List;

import com.parse.ParseUser;

import android.app.Application;
import android.test.ApplicationTestCase;
import be.xvrt.times.model.ClocksStore.ClocksStoreListener;
import be.xvrt.times.uil.ParseTestUtil;

public class ClocksStoreTest extends ApplicationTestCase<Application> {

    private ClocksStore clocksStore;

    private int[] calledListenersCount;

    private Clock brussels;

    public ClocksStoreTest() {
        super(Application.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();

        ParseUser user = ParseTestUtil.getTestUser();
        clocksStore = new ClocksStore(user);
        clocksStore.clear();

        calledListenersCount = new int[]{0};
        clocksStore.addListener(new ClocksStoreListener() {
            @Override
            public void onClocksStoreUpdated(List<Clock> clocks) {
                calledListenersCount[0]++;
            }
        });

        brussels = new Clock();
        brussels.setCity("Brussels");
        brussels.setTimezone("GMT+1");
    }

    @Override
    protected void tearDown() throws Exception {
        clocksStore.clear();
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

        assertDeleted(brussels.getObjectId(), Clock.class);
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

        assertDeleted(brussels.getObjectId(), Clock.class);
    }

}
