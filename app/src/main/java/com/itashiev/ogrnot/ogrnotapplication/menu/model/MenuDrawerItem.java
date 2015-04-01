package com.itashiev.ogrnot.ogrnotapplication.menu.model;

public class MenuDrawerItem {
    private String title = "";
    private int icon;

    public MenuDrawerItem() {

    }

    public MenuDrawerItem(String title, int icon) {
        this.title = title;
        this.icon = icon;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
