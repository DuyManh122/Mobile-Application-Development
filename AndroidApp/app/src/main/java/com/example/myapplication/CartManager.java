package com.example.myapplication;

import java.util.ArrayList;
import java.util.List;

public class CartManager {
    private static CartManager instance;
    private final List<CartClass> cartClasses = new ArrayList<>();

    private CartManager() {}

    public static CartManager getInstance() {
        if (instance == null) {
            instance = new CartManager();
        }
        return instance;
    }

    public void addToCart(CartClass item) {
        for (CartClass i : cartClasses) {
            if (i.getProduct().getProductName().equals(item.getProduct().getProductName())) {
                i.setQuantity(i.getQuantity() + item.getQuantity());
                return;
            }
        }
        cartClasses.add(item);
    }

    public List<CartClass> getCartItems() {
        return cartClasses;
    }

    public void clearCart() {
        cartClasses.clear();
    }
}