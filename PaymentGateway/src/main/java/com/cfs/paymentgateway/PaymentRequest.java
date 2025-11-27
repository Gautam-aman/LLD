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

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReciever() {
        return Reciever;
    }

    public void setReciever(String reciever) {
        Reciever = reciever;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
