package be.xvrt.times.model;

import java.util.TimeZone;

import android.graphics.Color;

public enum Timezone {
    HST("Hawaii Standard Time", Color.rgb(0, 64, 192)),
    PST8PDT("Pacific Standard Time", Color.rgb(0, 64, 192)),
    MST("Mountain Standard Time", Color.rgb(128, 64, 64)),
    CST6CDT("Central Standard Time", Color.rgb(128, 64, 64)),
    EST("Eastern Standard Time", Color.rgb(64, 0, 192)),
    WET("Western Europe Time", Color.rgb(192, 64, 32)),
    CET("Central Europe Time", Color.rgb(64, 64, 255)),
    EET("Eastern Europe Time", Color.rgb(0, 128, 128)),
    PRC("China Standard Time", Color.rgb(0, 128, 128)),
    ROK("Korea Standard Time", Color.rgb(0, 128, 128)),
    NZ("New Zealand Time", Color.rgb(0, 128, 128)),
    UTC("Universal", Color.rgb(192, 32, 32)),
    Zulu("Zulu", Color.rgb(192, 32, 32)); // TODO

    private final String longName;
    private final int color;

    private Timezone(String longName, int color) {
        this.longName = longName;
        this.color = color;
    }

    public String getLongName() {
        return longName;
    }

    public int getColor() {
        return color;
    }

    public String asGMT() {
        TimeZone timeZone = TimeZone.getTimeZone(name());
        int rawOffset = timeZone.getRawOffset();
        int nbHours = rawOffset / 3600000;

        if (nbHours >= 0) {
            return "GMT+" + nbHours;
        } else {
            return "GMT" + nbHours;
        }
    }

}
