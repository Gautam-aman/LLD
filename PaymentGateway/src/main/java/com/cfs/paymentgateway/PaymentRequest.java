package com.cfs.paymentgateway;


public class PaymentRequest {
    private String sender;
    private String Reciever;
    private Double amount;
    private String currency;

    public PaymentRequest(String sender, String reciever, Double amount, String currency) {
        this.sender = sender;
        this.Reciever = reciever;
        this.amount = amount;
        this.currency = currency;

    }

}
