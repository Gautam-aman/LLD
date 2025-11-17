package managers;

import models.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderManager {
    private List<Order> orders;
    private static OrderManager orderManager;

    OrderManager(){}

    public static OrderManager getInstance(){
        if(orderManager == null){
            orderManager = new OrderManager();
        }
        return orderManager;
    }

    void addOrder(Order order){
        orders.add(order);
    }

    void removeOrder(Order order){
        orders.remove(order);
    }

    List<Order> getOrders(){
        return List.copyOf(orders);
    }



}
