package factory;

import Strategy.PayementStrategy;
import com.sun.jdi.event.StepEvent;
import models.*;
import org.springframework.validation.ObjectError;

import java.time.LocalDateTime;
import java.util.List;

public class ScheduledOrderFactory extends OrderFactory {

    public String scheduletime ;
    public ScheduledOrderFactory(String scheduletime) {
        this.scheduletime = scheduletime;
    }

    @Override
    public Order createOrder(User user, Cart cart, Restaurant restaurant, List<MenuItems> menuItems, PayementStrategy paymentStrategy, String orderType) {
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
        order.setScheduled(scheduletime);
        return order;

    }
}
