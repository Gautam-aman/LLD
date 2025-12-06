package com.cfs.quickcommerce;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class product{
    private String name;
    private String price;
    private String id;
    public product(String name, String price, String id) {
        this.name = name;
        this.price = price;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

class  ProductFactory{
    private static String name;
    private static String price;
    public static product createProduct(String id){
        if (id == "1"){
            name = "Apple";
            price = "100";
        }
        else if (id == "2"){
            name = "Orange";
            price = "200";
        }
        else if (id == "3"){
            name = "Water";
            price = "300";
        }
        else{
            name="Rice";
            price = "400";
        }
        return new product(name, price, id);
    }
}

abstract interface InventoryStore {
    public abstract void addProduct(product product , int quantity);
    public abstract void removeProduct(String id , int quantity);
    public abstract Integer checkStock(String id );
    public abstract List<product> availableProducts();
}

class DbStore implements InventoryStore {

    Map<String, product > products; // id - > prod
    Map<String , Integer> stock; // id -> quantity

    public DbStore() {
        products = new HashMap<>();
        stock = new HashMap<>();
    }

    @Override
    public void addProduct(product product, int quantity) {
        String id = product.getId();
        // If product not already in catalog, add it
        if (!products.containsKey(id)) {
            products.put(id, product);
        }
        stock.put(id, stock.getOrDefault(id, 0) + quantity);
    }


    @Override
    public void removeProduct(String id , int quantity) {
        if (stock.get(id) ==0) return;
        if  (stock.get(id) >= quantity) {
            stock.put(id, stock.get(id) - quantity);
        }
        else stock.put(id, 0);

    }

    @Override
    public String checkStock(String id ) {
      //  String id = product.getId();
        return String.valueOf(stock.get(id));
    }

    @Override
    public List<product> availableProducts() {
        List<product> ans = new ArrayList<>();
        for(var it : stock.entrySet()){
            String id = it.getKey();
            int quantity = it.getValue();
            if (quantity > 0) ans.add(products.get(id));
        }
        return ans;
    }
}

class InventoryManager{
    private InventoryStore inventoryStore;
    public InventoryManager(InventoryStore inventoryStore) {
        this.inventoryStore = inventoryStore;
    }

    public void addStock(String id , int quantity){
        product newproduct = ProductFactory.createProduct(id);
        inventoryStore.addProduct(newproduct, quantity);
        System.out.println("Added product " + newproduct.getName());
    }

    public void removeStock(String id , int quantity){
        inventoryStore.removeProduct(id, quantity);
    }

    public Integer checkStock(String id){
        return inventoryStore.checkStock(id);
    }

    public List<product> availableProducts() {
        return inventoryStore.availableProducts();
    }
}

interface ReplenishStrategy{
    public void fill(InventoryManager manager  ,Map<String , Integer> stock);
}

class ThreshHoldReplenish implements ReplenishStrategy{
    private int replenish;
    public ThreshHoldReplenish(int replenish) {
        this.replenish = replenish;
    }

    @Override
    public void fill(InventoryManager manager, Map<String, Integer> stock) {
        for(var it : stock.entrySet()){
            String id = it.getKey();
            int quantity = it.getValue();
            Integer current = manager.checkStock(id);
            if (current < quantity){
                manager.addStock(id, quantity);
                System.out.println("Added stock " + id + " " + quantity);
            }
        }
    }
}



class WeeklyReplenishStrategy implements ReplenishStrategy{

    @Override
    public void fill(InventoryManager manager, Map<String, Integer> stock) {
        System.out.println("Weekly Replenish");
    }
}

class DarkStore{
    private String name;
    private Double x , y;
    private InventoryManager manager;
    private ReplenishStrategy replenishStrategy;
    public DarkStore(String name, Double x , Double y) {
        this.name = name;
        this.x = x;
        this.y = y;
        manager = new InventoryManager(new DbStore()); // Can make inventory store factory
    }

    public double Distanceto(double ux , double uy){
        return Math.sqrt(Math.pow(x - ux, 2) + Math.pow(y - uy, 2));
    }

    public void refill(Map<String , Integer> stock) {
        if (replenishStrategy!=null){
            replenishStrategy.fill(manager, stock);
        }
    }

    public List<product> getAllProducts() {
        return manager.availableProducts();
    }

    public Integer checkStock(String id){
        return manager.checkStock(id);
    }

    public void removeStock(String id , int quantity){
        manager.removeStock(id, quantity);
    }

    public void addStock(String id , int quantity){
        manager.addStock(id, quantity);
    }

    public void setReplenishStrategy(ReplenishStrategy replenishStrategy) {
        this.replenishStrategy = replenishStrategy;
    }

}

public class Main {
}
