package com.example.bar_buddy.AdapterItems;

import org.junit.Test;

import static org.junit.Assert.*;

public class BarItemTest {
    BarItem bar = new BarItem("123-456-7890", "12 Fast Lane", "The Jim");

    @Test
    public void setBar_idTest() {
        String exp = "1234";
        bar.setBar_id(exp);
        assertEquals(exp, bar.getBar_id());
    }

    @Test
    public void getBar_name() {
        String exp = "The Jim";
        assertEquals(exp, bar.getBar_name());
    }

    @Test
    public void getBar_phone() {
        String exp = "123-456-7890";
        assertEquals(exp, bar.getBar_phone());
    }

    @Test
    public void getBar_address() {
        String exp = "12 Fast Lane";
        assertEquals(exp, bar.getBar_address());
    }
}