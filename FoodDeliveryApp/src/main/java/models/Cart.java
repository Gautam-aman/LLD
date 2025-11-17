package models;

import java.util.List;

public class Cart {
    Restraunt restraunt;
    List<MenuItems> menuItems;
    Cart(){
        restraunt = null;
    }

    void addItem(MenuItems item){
        if (restraunt == null){
            System.out.println("Select restaurant first");
            return;
        }
        menuItems.add(item);
    }

    double addTotalCost(){
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

}
