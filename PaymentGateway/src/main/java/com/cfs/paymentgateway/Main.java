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
        super(new GooglePayBankingSystem());
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


class PaymentGatewayProxy extends PaymentGateway {
    private PaymentGateway realPaymentGateway;
    int retries;
    public PaymentGatewayProxy(PaymentGateway realPaymentGateway, int retries) {
        super(realPaymentGateway.bankingSystem);
        this.realPaymentGateway = realPaymentGateway;
        this.retries = retries;
    }

    @Override
    public boolean processPayment(PaymentRequest paymentRequest) {
        boolean result = false;
        for (int i = 0; i < retries; i++) {
            if (i > 0){
                System.out.println("Retrying Payment");
            }
            result = realPaymentGateway.processPayment(paymentRequest);
            if (result) break;
        }
        if (!result){
            System.out.println("Payment Failed");
        }
        return result;
    }

    public boolean validate(PaymentRequest paymentRequest) {
        return realPaymentGateway.validate(paymentRequest);
    }

    public  boolean initiatePayment(PaymentRequest paymentRequest) {
       // System.out.println("Initiating Payment");
        return realPaymentGateway.initiatePayment(paymentRequest);
    }

    public boolean confirmPayment(PaymentRequest paymentRequest) {
        return realPaymentGateway.confirmPayment(paymentRequest);
    }

}

class GatewayFactory{
    private static GatewayFactory instance = null;


    public static GatewayFactory getInstance(){
        if(instance == null){
            instance = new GatewayFactory();
        }
        return instance;
    }

    public PaymentGateway getGateway(GatewayType gatewayType){
        if (gatewayType == GatewayType.PAYTM){
           PaytmGateway paytmGateway = new PaytmGateway();
           return new PaymentGatewayProxy(paytmGateway , 3);
        }
        else if (gatewayType == GatewayType.GOOGLEPAY){
            GooglePayGateway googlePayGateway = new GooglePayGateway();
            return new PaymentGatewayProxy(googlePayGateway , 3);
        }
        return null;
    }
}

class PaymentService{
    public static  PaymentService instance = null;
    private PaymentGateway paymentGateway;

    public static PaymentService getInstance(){
        if(instance == null){
            instance = new PaymentService();
        }
        return instance;
    }

    public void setGateway(PaymentGateway paymentGateway){
        this.paymentGateway = paymentGateway;
    }

    public boolean processPayment(PaymentRequest paymentRequest){
        return paymentGateway.processPayment(paymentRequest);
    }


}

class PaymentController{
    private static PaymentController instance = null;
    public static PaymentController getInstance(){
        if(instance == null){
            instance = new PaymentController();
        }
        return instance;
    }

    public boolean handlePayment(PaymentRequest paymentRequest , GatewayType gatewayType){
        PaymentGateway paymentGateway = GatewayFactory.getInstance().getGateway(gatewayType);
        PaymentService paymentService = PaymentService.getInstance();
        paymentService.setGateway(paymentGateway);
        return paymentService.processPayment(paymentRequest);
    }

}


public class Main {
    public static void main(String[] args) {
        PaymentRequest newRequest = new PaymentRequest("Aman" ,"Kishan",  20.00 , "INR");
        boolean pay = PaymentController.getInstance().handlePayment(newRequest, GatewayType.PAYTM);
        System.out.println("Final Payment Status = " + pay);
    }
    }

