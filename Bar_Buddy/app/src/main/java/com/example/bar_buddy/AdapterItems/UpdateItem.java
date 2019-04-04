package com.example.bar_buddy.AdapterItems;

public class UpdateItem {
    private String update_id;
    private String bar_id;
    private String bar_name;
    private String update_description;
    private String update_start_time;
    private String update_title;
    private String update_image;

    public UpdateItem() {

    }
  
    public UpdateItem(String id, String name, String desc, String start, String title){
        bar_id = id;
        bar_name = name;
        update_description = desc;
        update_start_time = start;
        update_title = title;
    }
  
    public void setUpdate_id(String id) {
        this.update_id = id;
    }

    public String getUpdate_id() { return update_id; }
  
    public String getBar_id() { return bar_id; }

    public String getBar_name() { return bar_name; }

    public String getUpdate_description() { return update_description; }

    public String getUpdate_start_time() { return update_start_time; }

    public String getUpdate_title() { return update_title; }

    public String getUpdate_image() { return update_image; }
}
