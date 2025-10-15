package com.cafe.model;

import java.time.LocalDateTime;

public class Order {
    private int orderId;
    private LocalDateTime dateTime;
    private OrderType type;
    private PaymentMethod paymentMethod;
    private OrderStatus status;
    private Product product;

    public Order(int orderId, LocalDateTime dateTime, OrderType type, PaymentMethod paymentMethod, OrderStatus status, Product product) {
        this.orderId = orderId;
        this.dateTime = dateTime;
        this.type = type;
        this.paymentMethod = paymentMethod;
        this.status = status;
        this.product = product;
    }

    public int getOrderId() { return orderId; }
    public void setOrderId(int orderId) { this.orderId = orderId; }
    public LocalDateTime getDateTime() { return dateTime; }
    public void setDateTime(LocalDateTime dateTime) { this.dateTime = dateTime; }
    public OrderType getType() { return type; }
    public void setType(OrderType type) { this.type = type; }
    public PaymentMethod getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(PaymentMethod paymentMethod) { this.paymentMethod = paymentMethod; }
    public OrderStatus getStatus() { return status; }
    public void setStatus(OrderStatus status) { this.status = status; }
    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }
}
