package com.example.bar_buddy.AdapterItems;

import java.util.Date;

public class EventItem {
    private String event_date;
    private String event_description;
    private String event_start_time;
    private String event_end_time;
    private String event_name;

    public EventItem() {
        //necessary empty constructor
    }

    public String getEvent_date() {
        return event_date;
    }

    public String getEvent_description() {
        return event_description;
    }

    public String getEvent_start_time() {
        return event_start_time;
    }

    public String getEvent_end_time() {
        return event_end_time;
    }

    public String getEvent_name() {
        return event_name;
    }
}
