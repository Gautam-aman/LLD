package Notification;

import models.Order;

public class NotificationService {

   public void notify(Order order){
       System.out.println(order.getOrderId() +  " is placed successfully");
   }

}
