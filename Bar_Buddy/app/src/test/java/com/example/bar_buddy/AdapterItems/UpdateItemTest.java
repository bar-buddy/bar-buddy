package com.example.bar_buddy.AdapterItems;

import org.junit.Test;

import static org.junit.Assert.*;

public class UpdateItemTest {

    UpdateItem item = new UpdateItem("1", "The Jim", "Cool bar", "8", "best");

    @Test
    public void getBar_idTest() {
        String exp = "1";
        assertEquals(exp, item.getBar_id());
    }

    @Test
    public void getBar_nameTest() {
        String exp = "The Jim";
        assertEquals(exp, item.getBar_name());
    }

    @Test
    public void getUpdate_descriptionTest() {
        String exp = "Cool bar";
        assertEquals(exp, item.getUpdate_description());
    }

    @Test
    public void getUpdate_start_timeTest() {
        String exp = "8";
        assertEquals(exp, item.getUpdate_start_time());
    }

    @Test
    public void getUpdate_titleTest() {
        String exp = "best";
        assertEquals(exp, item.getUpdate_title());
    }
}