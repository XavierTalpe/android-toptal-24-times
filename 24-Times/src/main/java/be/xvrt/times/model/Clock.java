package be.xvrt.times.model;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Clock")
public final class Clock extends ParseObject {

    public static final String KEY_CREATED_BY = "createdBy";
    private static final String KEY_TIMEZONE = "timezone";
    private static final String KEY_CITY = "city";

    public Clock() {
        put(KEY_CREATED_BY, ParseUser.getCurrentUser());
    }

    public String getTimezone() {
        return getString(KEY_TIMEZONE);
    }

    public void setTimezone(String timezone) {
        put(KEY_TIMEZONE, timezone);
    }

    public String getCity() {
        return getString(KEY_CITY);
    }

    public void setCity(String city) {
        put(KEY_CITY, city);
    }

}
