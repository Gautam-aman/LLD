package models;

public class User {
    private static int nextId = 0 ;
    private int id;
    private String name;
    private String address;
    private Cart cart;

    User(String id, String name, String address, Cart cart) {
        this.id = ++nextId;
        this.name = name;
        this.address = address;
        cart = new Cart();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }
}
