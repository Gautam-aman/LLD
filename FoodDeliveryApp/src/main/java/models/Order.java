package models;

import javax.swing.plaf.IconUIResource;
import java.awt.*;
import java.util.List;

public  class Order {
    static int nextId = 0 ;
    User user;
    private int orderId;
    Restaurant restaurant;
    List<MenuItem> menuItems;
    PaymentStrategy paymentStrategy;
    double total;
    String Scheduled;

    public String getType() {
        return null;
    }

    Order(){
        user = null;
        restaurant = null;
        paymentStrategy = null;
        total = 0;
        Scheduled = "";
        nextId++;
    }

    Boolean processPayment(){
        if (paymentStrategy != null) {
            paymentStrategy.pay();
            return true;
        }
        System.out.println("Enter payment method");
        return false;
    }


}

