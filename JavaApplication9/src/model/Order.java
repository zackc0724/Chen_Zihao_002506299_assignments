/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDateTime;


/**
 *
 * @author 80424
 */
public class Order {
    private int orderId;
    private LocalDateTime orderDateTime;
    private OrderType type;
    private PaymentMethod paymentMethod;
    private OrderStatus status;

    private Product product;

    private int customerId;

    public Order() { }

    public Order(int orderId, LocalDateTime orderDateTime, OrderType type,
                 PaymentMethod paymentMethod, OrderStatus status,
                 Product product, int customerId) {
        this.orderId = orderId;
        this.orderDateTime = orderDateTime;
        this.type = type;
        this.paymentMethod = paymentMethod;
        this.status = status;
        this.product = product;
        this.customerId = customerId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public LocalDateTime getOrderDateTime() {
        return orderDateTime;
    }

    public void setOrderDateTime(LocalDateTime orderDateTime) {
        this.orderDateTime = orderDateTime;
    }

    public OrderType getType() {
        return type;
    }

    public void setType(OrderType type) {
        this.type = type;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
    
    @Override
    public String toString() {
        String prod = (product == null ? "(no product)" : product.getName());
        return "#" + orderId + " - " + prod + " - " + status;
    }
}