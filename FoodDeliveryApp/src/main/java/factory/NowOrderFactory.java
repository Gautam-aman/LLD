package factory;

import Strategy.PayementStrategy;
import ch.qos.logback.core.util.TimeUtil;
import models.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

public  class NowOrderFactory extends OrderFactory {

    @Override
    Order createOrder(User user, Cart cart, Restaurant restaurant, List<MenuItems> menuItems, PayementStrategy paymentStrategy, String orderType) {
        Order order = null;
        if (orderType == "Delivery"){
            var deliveryOrder = new DeliveryOrder();
            deliveryOrder.setUserAddress(user.getAddress());
            order = deliveryOrder;
        }
        else{
            var pickuporder = new PickUpOrder();
            pickuporder.setRestaurantAddress(restaurant.getAddress());
            order = pickuporder;
        }
        order.setUser(user);
        order.setRestaurant(restaurant);
        order.setPaymentStrategy(paymentStrategy);
        order.setMenuItems(menuItems);
        LocalDateTime now = LocalDateTime.now();
        order.setScheduled(now.toString());
        order.setTotal(cart.addTotalCost());
        return order;

    }
}
