package com.example.paymentservice.dto;

public class PaymentRequest {
    private String orderId;
    private double amount;
    private String userId;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "PaymentRequest{" +
                "orderId='" + orderId + '\'' +
                ", amount=" + amount +
                ", userId='" + userId + '\'' +
                '}';
    }

    public PaymentRequest(String orderId, double amount, String userId) {
        this.orderId = orderId;
        this.amount = amount;
        this.userId = userId;
    }

    public PaymentRequest() {
    }
}
