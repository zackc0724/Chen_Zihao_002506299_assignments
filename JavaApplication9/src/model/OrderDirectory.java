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
public class OrderDirectory {
    
    private final List<Order> orders = new ArrayList<>();

    public void addOrder(Order o) { orders.add(o); }
    public void removeOrder(Order o) { orders.remove(o); }
    public List<Order> getAll() { return orders; }

    public Order findById(int id) {
        for (Order o : orders) {
            if (o.getOrderId() == id) return o;
        }
        return null;
    }
    
    public boolean idExists(int id) {
        return findById(id) != null;
    }

    public List<Order> findByCustomerId(int customerId) {
        List<Order> out = new ArrayList<>();
        for (Order o : orders) {
            if (o.getCustomerId() == customerId) out.add(o);
        }
        return out;
    }
}
