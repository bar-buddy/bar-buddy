package com.example.bar_buddy;

public class BarItem {
    String bar_name;
    String bar_cover;
    String bar_wait_time_minutes;
    String bar_description;

    public BarItem() {

    }

    public BarItem(String text) {
        this.bar_name = text;
    }

    public String getBar_name() {
        return bar_name;
    }

    public String getBar_cover() {
        return bar_cover;
    }

    public String getBar_wait_time_minutes() {
        return bar_wait_time_minutes;
    }

    public String getBar_description() {
        return bar_description;
    }

}
