package models;

import Strategy.PayementStrategy;

import javax.swing.plaf.IconUIResource;
import java.awt.*;
import java.util.List;

public  class Order {
    static int nextId = 0 ;
    User user;
    private int orderId;
    Restaurant restaurant;
    List<MenuItems> menuItems;
    PayementStrategy paymentStrategy;
    double total;
    String Scheduled;

    public String getType() {
        return null;
    }

   public Order(){
        user = null;
        restaurant = null;
        paymentStrategy = null;
        total = 0;
        Scheduled = "";
        nextId++;
    }

    Boolean processPayment(){
        if (paymentStrategy != null) {
            paymentStrategy.pay(total);
            return true;
        }
        System.out.println("Enter payment method");
        return false;
    }

    public static int getNextId() {
        return nextId;
    }

    public static void setNextId(int nextId) {
        Order.nextId = nextId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
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

    public PayementStrategy getPaymentStrategy() {
        return paymentStrategy;
    }

    public void setPaymentStrategy(PayementStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getScheduled() {
        return Scheduled;
    }

    public void setScheduled(String scheduled) {
        Scheduled = scheduled;
    }
}

