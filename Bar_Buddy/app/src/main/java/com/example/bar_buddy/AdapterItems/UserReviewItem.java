package com.example.bar_buddy.AdapterItems;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.model.value.TimestampValue;

import java.util.Date;

public class UserReviewItem {
    private String bar_cover;
    private String bar_wait;
    private Timestamp time_submitted;

    public UserReviewItem() {

    }

    public String getBar_cover() {
        return bar_cover;
    }

    public String getBar_wait() {
        return bar_wait;
    }

    public Date getTime_submitted() {
        return time_submitted.toDate();
    }
}
