package be.xvrt.times.model;

import android.test.ApplicationTestCase;
import be.xvrt.times.ParseApplication;

public class TimezoneTest extends ApplicationTestCase<ParseApplication> {

    public TimezoneTest() {
        super(ParseApplication.class);
    }

    public void testGmtIsCorrectlyComputed() {
        assertEquals("GMT+1", Timezone.CET.asGMT());
        assertEquals("GMT-8", Timezone.PST8PDT.asGMT());
        assertEquals("GMT+9", Timezone.ROK.asGMT());
    }

}
