package com.cfs.discountcouponengine;

import java.util.ArrayList;
import java.util.List;

interface DiscountStrategy{
    double calculateDiscount(double baseAmount);
}

class FlatDiscount implements DiscountStrategy{
    private double baseAmount;
    public FlatDiscount(double originalAmount) {
        this.baseAmount = originalAmount;
    }
    @Override
    public double calculateDiscount(double originalAmount) {
        return Double.min(baseAmount, originalAmount);
    }
}

class PercentageDiscount implements DiscountStrategy{
    private double percentAmount;
    public PercentageDiscount(double percentAmount) {
        this.percentAmount = percentAmount;
    }
    @Override
    public double calculateDiscount(double originalAmount) {
        return (percentAmount / 100.0) * originalAmount;
    }
}

enum StrategyType{
    FLAT, PERCENTAGE;
}

class DiscountStrategyManager {
    private static DiscountStrategyManager instance  = null;

    public static DiscountStrategyManager getInstance() {
        if (instance == null) {
            instance = new DiscountStrategyManager();
        }
        return instance;
    }

    public DiscountStrategy getDiscountStrategy(StrategyType strategyType, double param1) {
        if(strategyType == StrategyType.FLAT){
            return new FlatDiscount(param1);
        }
        if(strategyType == StrategyType.PERCENTAGE){
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
    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }
    public Product getProduct() {
        return product;
    }
    public double getPrice(){
        return product.getPrice() * quantity;
    }
}

class Cart{
    private List<CartItem> cartItems;
    private double originalTotal;
    private double currentTotal;
    private boolean loyaltyMember;
    private String paymentBank;

    public Cart(){
        cartItems = new ArrayList<>();
        originalTotal = 0.0;
        currentTotal = 0.0;
        loyaltyMember = false;
        paymentBank = "";
    }

    public void addProduct(Product product, int quantity){
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

    public Coupon getNext() {
        return next;
    }

    public void applyDiscount(Cart cart){
        if (isApplicable(cart)){
            double discount = getDiscount(cart);
            cart.applyDiscount(discount);
            if (!isCombinable(cart)) return;
        }
        if(next != null) next.applyDiscount(cart);
    }

    public abstract boolean isCombinable(Cart cart);
    public abstract double getDiscount(Cart cart);
    public abstract boolean isApplicable(Cart cart);
}

class SeasonalOffer extends Coupon{
    private double percent;
    private String category;
    private DiscountStrategy discountStrategy;
    public SeasonalOffer(double percent, String category) {
        this.percent = percent;
        this.category = category;
        discountStrategy = DiscountStrategyManager.getInstance().getDiscountStrategy(StrategyType.PERCENTAGE, percent);
    }

    @Override
    public boolean isCombinable(Cart cart) {
        return true;
    }

    @Override
    public double getDiscount(Cart cart) {
        double total = 0;
        for(CartItem cartItem : cart.getCartItems()) {
            if (cartItem.getProduct().getCategory().equals(category)) {
                total += cartItem.getPrice();
            }
        }
        return discountStrategy.calculateDiscount(total);
    }

    @Override
    public boolean isApplicable(Cart cart) {
        for (CartItem cartItem : cart.getCartItems()) {
            if(cartItem.getProduct().getCategory().equals(category)){
                return true;
            }
        }
        return false;
    }
}

class LoyaltyDiscount extends Coupon{
    private double flat;
    private String category;
    private DiscountStrategy discountStrategy;
    public LoyaltyDiscount(double flat, String category) {
        this.flat = flat;
        this.category = category;
        discountStrategy = DiscountStrategyManager.getInstance().getDiscountStrategy(StrategyType.FLAT, flat);
    }

    @Override
    public boolean isCombinable(Cart cart) {
        return true;
    }

    @Override
    public double getDiscount(Cart cart) {
        return discountStrategy.calculateDiscount(cart.getCurrentTotal());
    }

    @Override
    public boolean isApplicable(Cart cart) {
        return cart.isLoyaltyMember();
    }
}

class CouponManager{
    private static CouponManager instance = null;
    private Coupon head;

    private CouponManager(){
        head = null;
    }

    public static CouponManager getInstance() {
        if (instance == null) {
            instance = new CouponManager();
        }
        return instance;
    }

    public void RegisterCoupon(Coupon coupon){
        if (head == null){
            head = coupon;
        }
        else{
            Coupon tempHead = head;
            while (tempHead.getNext() != null) {
                tempHead = tempHead.getNext();
            }
            tempHead.setNext(coupon);
        }
    }

    public double ApplyAll(Cart cart){
        if(head != null) head.applyDiscount(cart);
        return cart.getCurrentTotal();
    }
}

public class Main {
    public static void main(String[] args) {
        Product p1 = new Product("p1", 100.0, "p1");
        Product p2 = new Product("p2", 200.0, "p1");
        Product p3 = new Product("p3", 300.0, "p1");

        Cart cart = new Cart();
        cart.addProduct(p1, 10);
        cart.addProduct(p2, 10);
        cart.addProduct(p3, 10);
        cart.setLoyaltyMember(true);
        cart.setPaymentBank("ABC");

        CouponManager couponManager = CouponManager.getInstance();
        couponManager.RegisterCoupon(new SeasonalOffer(20, "p1"));
        couponManager.RegisterCoupon(new LoyaltyDiscount(200.00, "p1"));

        double total = couponManager.ApplyAll(cart);
        System.out.println("Final total: " + total);
    }
}
