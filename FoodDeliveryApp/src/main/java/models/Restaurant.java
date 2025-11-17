package models;

import java.util.List;

public class Restaurant {
    private static int nextResId = 0;
    private int Restid;
    private String name;
    private String Address;
    List<MenuItems> menuItems;

    public Restaurant(String name, String address) {
        this.Restid = nextResId++;
        this.name = name;
        this.Address = address;
    }

    public void cleanup() {
        if (menuItems != null) {
            menuItems.clear();
        }
    }

    public static int getNextResId() {
        return nextResId;
    }

    public int getRestid() {
        return Restid;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return Address;
    }

    public static void setNextResId(int nextResId) {
        Restaurant.nextResId = nextResId;
    }

    public void setRestid(int restid) {
        Restid = restid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public void setMenuItems(List<MenuItems> menuItems) {
        this.menuItems = menuItems;
    }

    public List<MenuItems> getMenuItems() {
        return menuItems;
    }


};




