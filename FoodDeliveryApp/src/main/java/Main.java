import Strategy.UPIPaymentStrategy;
import models.Order;
import models.Restaurant;
import models.User;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Zomato zomato = new Zomato();
        User user = new User("1" , "Aman " , "Alpha1" );
        List<Restaurant> restaurants = zomato.getRestaurants();
        if (restaurants != null) {
            for (Restaurant restaurant : restaurants) {
                System.out.println(restaurant.getName());
            }
        }
        else{
            System.out.println("Restaurant not found");
        }

        zomato.selectRestaurant(user , restaurants.get(0));
        zomato.addToCart(user , "1");
        zomato.addToCart(user , "2");
        Order order = zomato.placeOrderNow(user , "Delivery" , new UPIPaymentStrategy());
        zomato.payforOrder(user , order);
        zomato = null;
        user = null;

    }
}
