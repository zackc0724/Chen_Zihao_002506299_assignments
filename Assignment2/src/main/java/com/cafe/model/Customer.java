package com.cafe.model;

public class Customer {
    private int id;
    private String firstName;
    private String lastName;
    private long contact;
    private Order activeOrder; // one at a time

    public Customer(int id, String firstName, String lastName, long contact) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.contact = contact;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public long getContact() { return contact; }
    public void setContact(long contact) { this.contact = contact; }

    public Order getActiveOrder() { return activeOrder; }
    public void setActiveOrder(Order activeOrder) { this.activeOrder = activeOrder; }

    @Override
    public String toString() { return id + " - " + firstName + " " + lastName; }
}
