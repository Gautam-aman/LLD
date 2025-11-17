package models;

public class PickUpOrder extends Order {
    private String RestaurantAddress;

    public PickUpOrder(){
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
