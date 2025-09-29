/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author 80424
 */
public class Customer {
    private int customerId;
    private String firstName;
    private String lastName;
    private String contact; // email or phone
    private final List<Order> orders = new ArrayList<>();

    public Customer() { }

    public Customer(int customerId, String firstName, String lastName, String contact) {
        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.contact = contact;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
    
    public List<Order> getOrders() { return orders; }
    
    public void addOrder(Order o) { orders.add(o); }
    
    public void removeOrder(Order o) { orders.remove(o); }
    
    public String getFullName() {
        String f = firstName == null ? "" : firstName;
        String l = lastName == null ? "" : lastName;
        return (f + " " + l).trim();
    }

    @Override
    public String toString() {
        // Helpful in combo boxes
        return customerId + " - " + getFullName();
    }
}
