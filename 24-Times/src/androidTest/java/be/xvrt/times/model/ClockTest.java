package be.xvrt.times.model;

import static be.xvrt.times.uil.ParseTestUtil.assertDeleted;

import com.parse.ParseException;
import com.parse.ParseUser;

import android.app.Application;
import android.test.ApplicationTestCase;

public class ClockTest extends ApplicationTestCase<Application> {

    public ClockTest() {
        super(Application.class);
    }

    public void testClockInitialization() {
        Clock clock = new Clock();
        clock.setUser(ParseUser.getCurrentUser());
        clock.setTimezone("GMT+1");
        clock.setCity("Brussels");

        assertEquals("GMT+1", clock.getTimezone());
        assertEquals("Brussels", clock.getCity());
    }

    public void testSaveDelete() {
        Clock clock = new Clock();
        clock.setUser(ParseUser.getCurrentUser());
        clock.setTimezone("GMT+1");
        clock.setCity("Brussels");

        try {
            clock.save();
            clock.delete();
        } catch (ParseException exception) {
            fail(exception.getMessage());
        }

        assertDeleted(clock.getObjectId(), Clock.class);
    }

}
