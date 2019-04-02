package com.example.bar_buddy.AdapterItems;

import java.io.Serializable;

public class BarItem implements Serializable {
    private String bar_id;
    private String bar_name;
    private String bar_cover;
    private String bar_wait;
    private String bar_description;
    private String bar_phone;
    private String bar_address;
    private String bar_image;
    private String bar_hours_operation;

    public BarItem() {

    }

    public void setBar_id(String id) {
        this.bar_id = id;
    }

    public String getBar_id() { return bar_id; }

    public String getBar_name() {
        return bar_name;
    }

    public String getBar_cover() {
        return bar_cover;
    }

    public String getBar_wait() {
        return bar_wait;
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

    public String getBar_image() { return bar_image; }

    public String getBar_hours_operation() { return bar_hours_operation; }
}
