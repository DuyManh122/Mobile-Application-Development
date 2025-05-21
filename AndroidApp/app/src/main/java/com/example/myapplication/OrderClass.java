package com.example.myapplication;

public class OrderClass {

    private String userEmail;
    private String productName;
    private double price;
    private int quantity;

    public OrderClass() {}

    public OrderClass(String userEmail, String productName, double price, int quantity) {
        this.userEmail = userEmail;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
    }

    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

}
