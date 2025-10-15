package com.cafe.model;

public class Product {
    private int id;
    private String name;
    private Category category;
    private double price;
    private int number;
    private int prepMinutes;

    public Product(int id, String name, Category category, double price, int number, int prepMinutes) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.number = number;
        this.prepMinutes = prepMinutes;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public int getNumber() { return number; }
    public void setNumber(int number) { this.number = number; }
    public int getPrepMinutes() { return prepMinutes; }
    public void setPrepMinutes(int prepMinutes) { this.prepMinutes = prepMinutes; }

    @Override
    public String toString() { return name + " ($" + price + ")"; }
}
