package com.cfs.paymentgateway;

import java.util.Random;

interface BankingSystem{
    public boolean processPayment();
}

class PaytmBankingSystem implements BankingSystem{

    @Override
    public boolean processPayment() {
        System.out.println("PaytmBankingSystem processPayment");
        Random rand = new Random();
        int r = rand.nextInt(10);
        return r > 5;
    }
}

class GooglePayBankingSystem implements BankingSystem{
    @Override
    public boolean processPayment() {
        System.out.println("GooglePayBankingSystem processPayment");
        Random rand = new Random();
        int r = rand.nextInt(10);
        return r > 5;
    }
}




public class Main {
}
