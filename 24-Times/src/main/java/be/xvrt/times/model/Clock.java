package be.xvrt.times.model;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("Clock")
public final class Clock extends ParseObject {

    private static final String KEY_TIMEZONE = "timezone";
    private static final String KEY_CITY = "city";

    public Clock() {
    }

    public String getTimezone() {
        return getString(KEY_TIMEZONE);
    }

    public String getCity() {
        return getString(KEY_CITY);
    }

    public void setTimezone(String timezone) {
        put(KEY_TIMEZONE, timezone);
    }

    public void setCity(String city) {
        put(KEY_CITY, city);
    }

}
