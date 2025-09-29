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
public class ProductCatalog {
    
    private final List<Product> products = new ArrayList<>();

    public void addProduct(Product p) { products.add(p); }
    public void removeProduct(Product p) { products.remove(p); }
    public List<Product> getAll() { return products; }

    public Product findById(int id) {
        for (Product p : products) {
            if (p.getProductId() == id) return p;
        }
        return null;
    }

    public List<Product> findByName(String namePart) {
        String q = namePart == null ? "" : namePart.toLowerCase().trim();
        List<Product> out = new ArrayList<>();
        for (Product p : products) {
            if (p.getName() != null && p.getName().toLowerCase().contains(q)) {
                out.add(p);
            }
        }
        return out;
    }

    public boolean idExists(int id) {
        return findById(id) != null;
    }
}
