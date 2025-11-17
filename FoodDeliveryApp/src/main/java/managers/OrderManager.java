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

    public void addOrder(Order order){
        orders.add(order);
    }

    public void  removeOrder(Order order){
        orders.remove(order);
    }

    public List<Order> getOrders(){
        return List.copyOf(orders);
    }



}
