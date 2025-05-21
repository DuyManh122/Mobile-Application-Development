package com.example.myapplication;

import java.io.Serializable;

public class ProductClass implements Serializable {

    private String productDescription;
    private String productName;
    private String categoryName;
    private int productPrice;
    private int productQuantity;

    private String productResourceId;


    public ProductClass() {}

    public ProductClass(String productDescription, String productName, String categoryName, int productPrice, int productQuantity, String productResourceId) {
        this.productDescription = productDescription;
        this.productName = productName;
        this.categoryName = categoryName;
        this.productPrice = productPrice;
        this.productQuantity = productQuantity;
        this.productResourceId = productResourceId;
    }


    public String getProductResourceId() {
        return productResourceId;
    }

    public void setProductResourceId(String productResourceId) {
        this.productResourceId = productResourceId;
    }

    public String getproductDescription() {
        return productDescription;
    }

    public void setproductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getcategoryName() {
        return categoryName;
    }

    public void setcategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getproductPrice() {
        return productPrice;
    }

    public void setproductPrice(int productPrice) {
        this.productPrice = productPrice;
    }

    public int getproductQuantity() {
        return productQuantity;
    }

    public void setproductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }
}
