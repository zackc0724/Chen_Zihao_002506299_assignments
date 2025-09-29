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
public class CustomerDirectory {
    
    private final List<Customer> customers = new ArrayList<>();

    public void addCustomer(Customer zihao) { customers.add(zihao); }
    public void removeCustomer(Customer zihao) { customers.remove(zihao); }
    public List<Customer> getAll() { return customers; }

    public Customer findById(int id) {
        for (Customer c : customers) {
            if (c.getCustomerId() == id) return c;
        }
        return null;
    }

    public List<Customer> searchByName(String namePart) {
        String q = namePart == null ? "" : namePart.toLowerCase().trim();
        List<Customer> out = new ArrayList<>();
        for (Customer c : customers) {
            if (c.getFullName().toLowerCase().contains(q)) {
                out.add(c);
            }
        }
        return out;
    }

    public boolean idExists(int id) {
        return findById(id) != null;
    }
}
