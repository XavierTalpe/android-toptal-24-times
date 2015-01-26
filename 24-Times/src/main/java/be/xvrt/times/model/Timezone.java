package be.xvrt.times.model;

import android.graphics.Color;

public enum Timezone {
    PST("Pacific Standard Time", Color.rgb(0, 64, 192), "GMT+2"),
    CST("Central Standard Time", Color.rgb(128, 64, 64), "GMT+2"),
    EST("Eastern Standard Time", Color.rgb(64, 0, 192), "GMT+2"),
    WET("Western Europe Time", Color.rgb(192, 64, 32), "GMT+2"),
    CET("Central Europe Time", Color.rgb(64, 64, 255), "GMT+2"),
    EET("Eastern Europe Time", Color.rgb(0, 128, 128), "GMT+2"),
    UTC("Universal Time Coordinated", Color.rgb(192, 32, 32), "GMT+2");

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
