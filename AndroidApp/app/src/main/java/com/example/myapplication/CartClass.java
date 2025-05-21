package com.example.myapplication;

public class CartClass {
    private ProductClass product;
    private int quantity;

    public CartClass(ProductClass product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public ProductClass getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int q) {
        this.quantity = q;
    }
}