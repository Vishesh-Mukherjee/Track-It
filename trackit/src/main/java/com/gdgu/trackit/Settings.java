package com.gdgu.trackit;

import java.awt.Color;
import java.awt.Font;

public class Settings {

    private static Settings settings = new Settings();

    private String startingDate = "19-07-2021";
    private Color attended = new Color(0, 255, 0);
    private Color notAttended = new Color(255, 0, 0);
    private Color unknown = new Color(255, 255, 255);
    private Font summaryFont = new Font("", Font.BOLD, 14);
    private int percentageLimit = 75;

    private Settings() {

    }

    public static Settings getSettings() {
        return settings;
    }

    public String getStartingDate() {
        return startingDate;
    }

    public Color getAttended() {
        return attended;
    }

    public Color getNotAttended() {
        return notAttended;
    }

    public Color getUnknown() {
        return unknown;
    }

    public Font getSumaryFont() {
        return summaryFont;
    }

    public int getPercentageLimit() {
        return percentageLimit;
    }

}
