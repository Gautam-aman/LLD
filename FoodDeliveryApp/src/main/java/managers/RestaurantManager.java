package managers;

import models.Restaurant;

import java.util.ArrayList;
import java.util.List;

// Follow Singleton pattern
public class RestaurantManager {
    private List<Restaurant> restaurants;
    static RestaurantManager restaurantManager= null;
    RestaurantManager() {}


    public static RestaurantManager getInstance() {
        if (restaurantManager == null) {
            restaurantManager = new RestaurantManager();
        }
        return restaurantManager;
    }

    void addRestaurant(Restaurant restaurant) {
        restaurants.add(restaurant);
    }

    List<Restaurant> getRestaurants() {
        return List.copyOf(restaurants);
    }

    List<Restaurant> getRestaurantByLocation(String location) {
        List<Restaurant> result = new ArrayList<>();
        for (Restaurant restaurant : restaurants) {
            String loc = restaurant.getAddress();
            if (loc.equals(location)) {
                result.add(restaurant);
            }
        }
        return result;
    }

}
