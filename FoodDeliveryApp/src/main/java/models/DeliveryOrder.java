package models;

public class DeliveryOrder extends Order {
    private String UserAddress;

    DeliveryOrder(){
        UserAddress = null;
    }

    @Override
    public String getType(){
        return "Delivery Order";
    }

    public String getUserAddress() {
        return UserAddress;
    }

    public void setUserAddress(String userAddress) {
        UserAddress = userAddress;
    }
}
