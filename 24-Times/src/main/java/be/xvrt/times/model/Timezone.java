package be.xvrt.times.model;

import android.graphics.Color;

public enum Timezone {
    PST("Pacific Standard Time", Color.RED, "GMT+2"),
    CST("Central Standard Time", Color.BLUE, "GMT+2"),
    EST("Eastern Standard Time", Color.BLUE, "GMT+2"),
    WET("Western Europe Time", Color.BLUE, "GMT+2"),
    CET("Central Europe Time", Color.BLUE, "GMT+2"),
    EET("Eastern Europe Time", Color.BLUE, "GMT+2"),
    UTC("Universal Time Coordinated", Color.BLUE, "GMT+2");

    private final String longName;
    private final int color;
    private final String gmtTimezone;

    private Timezone(String longName, int color, String gmtTimezone) {
        this.longName = longName;
        this.color = color;
        this.gmtTimezone = gmtTimezone;
    }

    public String getLongName() {
        return longName;
    }

    public int getColor() {
        return color;
    }

    public String getGmtTimezone() {
        return gmtTimezone;
    }

}
