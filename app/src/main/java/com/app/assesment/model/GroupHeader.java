package com.app.assesment.model;

import java.util.ArrayList;

public class GroupHeader {
    public static final String TAG = "GroupHeader";

    private String title;
    private ArrayList<ChildItems> Items;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<ChildItems> getItems() {
        return Items;
    }

    public void setItems(ArrayList<ChildItems> items) {
        Items = items;
    }
}
