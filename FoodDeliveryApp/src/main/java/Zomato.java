import Notification.NotificationService;
import Strategy.PayementStrategy;
import Strategy.UPIPaymentStrategy;
import factory.NowOrderFactory;
import factory.OrderFactory;
import factory.ScheduledOrderFactory;
import managers.OrderManager;
import managers.RestaurantManager;
import models.*;

import java.util.List;

public class Zomato {

    Zomato(){
        initialiseRestaurants();
    }

    private void initialiseRestaurants() {
        Restaurant restaurant1 = new Restaurant("Restaurant1"  , "Alpha1");
        MenuItems menuItems1 = new MenuItems("1" , "Burger" , 50.00);
        MenuItems menuItems2 = new MenuItems("2" , "Pizza" , 60.00);
        restaurant1.setMenuItems(List.of(menuItems1, menuItems2));

        RestaurantManager restaurantManager = RestaurantManager.getInstance();

        restaurantManager.addRestaurant(restaurant1);

    }

    List<Restaurant> getRestaurants() {
        RestaurantManager restaurantManager=  RestaurantManager.getInstance();
        return restaurantManager.getRestaurants();
    }

    void selectRestaurant(User user , Restaurant restaurant) {
        Cart cart = user.getCart();
        cart.setRestaurant(restaurant);
    }

    void addToCart(User user , String itemCode) {

        Restaurant restaurant = user.getCart().getRestaurant();
        if (restaurant == null) {
            System.out.println("Restaurant is null");
            return;
        }
        for(MenuItems menuItems : restaurant.getMenuItems()) {
            if (menuItems.getCode().equals(itemCode)) {
                user.getCart().addItem(menuItems);
                break;
            }
        }

    }

    Order placeOrderNow(User user, String orderType , PayementStrategy payementStrategy) {
        return checkout(user , orderType , payementStrategy , new NowOrderFactory());
    }

    Order placeOrderLater (User user, String orderType , PayementStrategy payementStrategy , String time) {
        return checkout(user , orderType , payementStrategy , new ScheduledOrderFactory(time));
    }

    public Order checkout(User user, String orderType, PayementStrategy payementStrategy, OrderFactory orderFactory) {
        if(user.getCart() == null) {
            System.out.println("Cart is null");
        }
        Cart userCart = user.getCart();
        Restaurant orderRestaurant = user.getCart().getRestaurant();
        List<MenuItems> menuItems = userCart.getMenuItems();
        double cost = userCart.addTotalCost();

        Order order = orderFactory.createOrder(user , userCart , orderRestaurant , menuItems , payementStrategy , orderType);
        OrderManager.getInstance().addOrder(order);
        return order;
    }

    void payforOrder(User user ,Order order) {
        Boolean ispaymentSuccess = order.processPayment();
        if (ispaymentSuccess) {
            System.out.println("Payment Success");
            NotificationService notificationService = new NotificationService();
            notificationService.notify(order);
            user.setCart(null);
        }
        else {
            System.out.println("Payment Failed");
        }
    }


}
