package com.example.bar_buddy.AdapterItems;

import org.junit.Test;

import static org.junit.Assert.*;

public class MenuItemTest {
    MenuItem item = new MenuItem("Hamburger", "$5", "Bread, meat, and lettuce.");

    @Test
    public void getItem_nameTest() {
        String exp = "Hamburger";
        assertEquals(exp, item.getItem_name());
    }

    @Test
    public void getItem_priceTest() {
        String exp = "$5";
        assertEquals(exp, item.getItem_price());
    }

    @Test
    public void getItem_descriptionTest() {
        String exp = "Bread, meat, and lettuce.";
        assertEquals(exp, item.getItem_description());
    }
}