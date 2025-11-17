package factory;

import Strategy.PayementStrategy;
import models.*;

import java.util.List;

public abstract class OrderFactory {
    public abstract Order createOrder(User user , Cart cart , Restaurant restaurant , List<MenuItems> menuItems , PayementStrategy paymentStrategy,
                               String orderType);
}
