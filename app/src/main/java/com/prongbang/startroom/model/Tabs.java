package com.prongbang.startroom.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mdev on 10/11/2017 AD.
 */

public class Tabs {
    private int id;
    private String name;
    private int icon;

    public Tabs() {}

    public Tabs(int id, String name, int icon) {
        this.id = id;
        this.name = name;
        this.icon = icon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public static List<Tabs> generate() {
        List<Tabs> tabs = new ArrayList<>();
        tabs.add(new Tabs(1, "LOAN", 0));
        tabs.add(new Tabs(2, "BOOK", 0));
        tabs.add(new Tabs(3, "USER", 0));
        return tabs;
    }
}
