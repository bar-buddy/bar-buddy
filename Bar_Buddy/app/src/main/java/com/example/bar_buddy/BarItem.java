package com.example.bar_buddy;

public class BarItem {
    private String bar_name;
    private String bar_cover;
    private String bar_wait_time_minutes;
    private String bar_description;
    private String bar_phone;
    private String bar_address;

    public BarItem() {

    }

    public BarItem(String bar_name, String bar_cover, String bar_wait_time_minutes, String bar_description, String bar_phone, String bar_address) {
        this.bar_name = bar_name;
        this.bar_cover = bar_cover;
        this.bar_wait_time_minutes = bar_wait_time_minutes;
        this.bar_description = bar_description;
        this.bar_phone = bar_phone;
        this.bar_address = bar_address;
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

    public String getBar_phone() {
        return bar_phone;
    }

    public String getBar_address() {
        return bar_address;
    }
}
