package com.cfs.discountcouponengine;


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



public class Main {
}
