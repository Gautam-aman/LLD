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
        return r > 2;
    }
}

 abstract class PaymentGateway {
    public BankingSystem bankingSystem;
    public PaymentGateway(BankingSystem bankingSystem) {
        this.bankingSystem = bankingSystem;
    }

    public boolean processPayment(PaymentRequest paymentRequest) {
        if (!validate(paymentRequest)){
            System.out.println("Validation Failed");
            return false;
        }
        if (!initiatePayment(paymentRequest)){
            System.out.println("Initiation Failed");
            return false;
        }
        if (!confirmPayment(paymentRequest)){
            System.out.println("Confirmation Failed");
            return false;
        }
        return true;
    }
    public abstract boolean  initiatePayment(PaymentRequest paymentRequest);
    public abstract boolean confirmPayment(PaymentRequest paymentRequest) ;
    public abstract boolean validate(PaymentRequest paymentRequest) ;
}

class PaytmGateway extends PaymentGateway {
     public PaytmGateway() {
       super(new PaytmBankingSystem());
    }


    @Override
    public boolean initiatePayment(PaymentRequest paymentRequest) {
        System.out.println("PaytmGateway initiatePayment");
        return bankingSystem.processPayment();
    }

    @Override
    public boolean confirmPayment(PaymentRequest paymentRequest) {
        System.out.println("Payment Successfull");
        return true;
    }

    @Override
    public boolean validate(PaymentRequest paymentRequest) {
        System.out.println("Validating Payment");
        double amount = paymentRequest.getAmount();
        if(amount < 0 || !paymentRequest.getCurrency().equals("INR")) {
            return false;
        }
        return true;
    }
}

class GooglePayGateway extends PaymentGateway {
    public GooglePayGateway() {
        super(new PaytmBankingSystem());
    }
    @Override
    public boolean initiatePayment(PaymentRequest paymentRequest) {
        System.out.println("GooglePayGateway initiatePayment");
        return bankingSystem.processPayment();
    }
    @Override
    public boolean confirmPayment(PaymentRequest paymentRequest) {
        System.out.println("Confirming Payment");
        return true;
    }
    @Override
    public boolean validate(PaymentRequest paymentRequest) {
        double amount = paymentRequest.getAmount();
        if(amount < 0 || !paymentRequest.getCurrency().equals("INR")) {
            return false;
        }
        return true;
    }
}



public class Main {
}
