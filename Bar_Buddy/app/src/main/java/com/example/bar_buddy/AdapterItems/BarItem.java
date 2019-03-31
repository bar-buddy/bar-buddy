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

    public BarItem(
            String bar_id, String bar_name, String bar_cover, String bar_wait, String bar_description,
            String bar_phone, String bar_address, String bar_image, String bar_hours_operation) {
        this.bar_id = bar_id;
        this.bar_name = bar_name;
        this.bar_cover = bar_cover;
        this.bar_wait = bar_wait;
        this.bar_description = bar_description;
        this.bar_phone = bar_phone;
        this.bar_address = bar_address;
        this.bar_image = bar_image;
        this.bar_hours_operation = bar_hours_operation;
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
