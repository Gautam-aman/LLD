package com.cfs.discountcouponengine;


import java.util.List;

interface DiscountStrategy{
    public double calculateDiscount(double baseAmount);
}

class FlatDiscount implements DiscountStrategy{
    private double baseAmount;
    public FlatDiscount(double originalAmount) {
        this.baseAmount = originalAmount;
    }
    @Override
    public double calculateDiscount(double originalAmount) {
        return Double.min(baseAmount , originalAmount );
    }
}

class PercentageDiscount implements DiscountStrategy{
    private double percentAmount;
    public PercentageDiscount(double percentAmount) {
        this.percentAmount = percentAmount;
    }
    @Override
    public double calculateDiscount(double originalAmount) {
        return (percentAmount/100.0 ) * originalAmount;
    }
}

enum StrategyType{
    FLAT , PERCENTAGE;
}

class DiscountStrategyManager {
    private static DiscountStrategyManager instance  = null;


    public static DiscountStrategyManager getInstance() {
        if (instance == null) {
            instance = new DiscountStrategyManager();
        }
        return instance;
    }

    public DiscountStrategy getDiscountStrategy(StrategyType strategyType , double param1) {
        if(strategyType == StrategyType.FLAT){
            return new FlatDiscount(param1);
        }
        else if(strategyType == StrategyType.PERCENTAGE){
            return new PercentageDiscount(param1);
        }
        return null;
    }

}


class Product{
    private String name;
    private double price;
    private String category;
    public Product(String name, double price, String category) {
        this.name = name;
        this.price = price;
        this.category = category;
    }
    public String getName() {
        return name;
    }
    public double getPrice() {
        return price;
    }
    public String getCategory() {
        return category;
    }
}

class CartItem{
    private Product product;
    private int quantity;
    public CartItem(Product product , int quatity) {
        this.product = product;
        this.quantity = quatity;
    }
    public Product getProduct() {
        return product;
    }
    public double getPrice(){
        return  product.getPrice()*quantity;
    }
}

class Cart{
    private List<CartItem> cartItems;
    private double originalTotal;
    private double currentTotal;
    private boolean loyaltyMember;
    private String paymentBank;

    public Cart(){
        originalTotal = 0.0;
        currentTotal = 0.0;
        loyaltyMember = false;
        paymentBank = "";
    }


    public void addProduct(Product product , int quantity){
        CartItem cartItem = new CartItem(product, quantity);
        cartItems.add(cartItem);
        currentTotal += cartItem.getPrice();
        originalTotal += cartItem.getPrice();
    }

    public double getCurrentTotal() {
        return currentTotal;
    }
    public double getOriginalTotal() {
        return originalTotal;
    }

    public void applyDiscount(double discount){
        currentTotal -= discount;
        if (currentTotal < 0) {
            currentTotal = 0.0;
        }
    }

    void setLoyaltyMember(boolean loyaltyMember){
        this.loyaltyMember = loyaltyMember;
    }

    public boolean isLoyaltyMember(){
        return loyaltyMember;
    }

    public void setPaymentBank(String paymentBank) {
        this.paymentBank = paymentBank;
    }
    public String getPaymentBank() {
        return paymentBank;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

}

abstract class Coupon{
    private Coupon next;
    public Coupon() {
        next = null;
    }

    public void setNext(Coupon coupon) {
        this.next = coupon;
    }

    public void applyDiscount(Cart cart){
        if (isApplicable(cart)){
            double discount = getDiscount(cart);
            cart.applyDiscount(discount);
            System.out.println("Discount applied: " + discount);
            if (!isCombinable(cart)) return;
        }
        if(next != null) next.applyDiscount(cart);
    }

    public abstract boolean isCombinable(Cart cart);
    public abstract double getDiscount(Cart cart) ;
    public abstract boolean isApplicable(Cart cart);

}

class SeasonalOffer extends Coupon{

    private double percent;
    private String Category;
    private DiscountStrategy discountStrategy;
    public SeasonalOffer(double percent, String Category) {
        this.percent = percent;
        this.Category = Category;
       discountStrategy = DiscountStrategyManager.getInstance().getDiscountStrategy(StrategyType.PERCENTAGE, percent);
    }

    @Override
    public boolean isCombinable(Cart cart) {
        return false;
    }

    @Override
    public double getDiscount(Cart cart) {
        return 0;
    }

    @Override
    public boolean isApplicable(Cart cart) {
        for (CartItem cartItem : cart.getCartItems()) {
            if(!cartItem.getProduct().getCategory().equals(Category)){
                return false;
            }
        }
        return true;
    }
}


public class Main {
}
