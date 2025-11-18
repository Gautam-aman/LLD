package models;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    Restaurant restaurant;
    List<MenuItems> menuItems;
    Cart(){
        restaurant = null;
        this.menuItems = new ArrayList<>();
    }



   public void addItem(MenuItems item){
        if (restaurant == null){
            System.out.println("Select restaurant first");
            return;
        }
        menuItems.add(item);
    }

   public double addTotalCost(){
        double cost = 0;
        for(MenuItems item : menuItems){
            cost += item.getPrice();
        }
        return cost;
    }

    Boolean isEmpty(){
        return menuItems.isEmpty();
    }

    void clearCart(){
        menuItems.clear();
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public List<MenuItems> getMenuItems() {
        return menuItems;
    }

    public void setMenuItems(List<MenuItems> menuItems) {
        this.menuItems = menuItems;
    }
}
