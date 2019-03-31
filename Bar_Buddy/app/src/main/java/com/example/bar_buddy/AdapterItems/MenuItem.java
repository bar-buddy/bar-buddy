package com.example.bar_buddy.AdapterItems;

public class MenuItem {
    private String item_name;
    private String item_price;
    private String item_description;

    public MenuItem() {
        //necessary empty constructor
    }

    public MenuItem(String name, String price, String description) {
        item_name = name;
        item_price = price;
        item_description = description;
    }

    public String getItem_name() { return item_name; }

    public String getItem_price() { return item_price; }

    public String getItem_description() { return item_description; }
}
