package be.xvrt.times.model;

import static be.xvrt.times.uil.ParseTestUtil.assertDeleted;

import com.parse.ParseException;

import android.app.Application;
import android.test.ApplicationTestCase;
import be.xvrt.times.util.ParseUtil;

public class ClockTest extends ApplicationTestCase<Application> {

    public ClockTest() {
        super(Application.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        ParseUtil.initParse(getContext());
    }

    public void testClockInitialization() {
        Clock clock = new Clock();
        clock.setTimezone("GMT+1");
        clock.setCity("Brussels");

        assertEquals("GMT+1", clock.getTimezone());
        assertEquals("Brussels", clock.getCity());
    }

    public void testSaveDelete() {
        Clock clock = new Clock();
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
