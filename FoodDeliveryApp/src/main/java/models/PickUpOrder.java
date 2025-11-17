package models;

public class PickUpOrder extends Order {
    private String RestaurantAddress;

    PickUpOrder(){
        RestaurantAddress = null;
    }

    @Override
    public String getType(){
        return "PickUp Order";
    }

    public String getRestaurantAddress() {
        return RestaurantAddress;
    }

    public void setRestaurantAddress(String restaurantAddress) {
        RestaurantAddress = restaurantAddress;
    }
}
