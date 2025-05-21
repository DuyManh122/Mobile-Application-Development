package com.example.myapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.WriteBatch;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    CartAdapter cartAdapter;
    Button checkoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cart);
        setTitle("Your Cart");

        recyclerView = findViewById(R.id.cartRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        cartAdapter = new CartAdapter(CartManager.getInstance().getCartItems());
        recyclerView.setAdapter(cartAdapter);

        checkoutButton = findViewById(R.id.checkoutButton);
        checkoutButton.setOnClickListener(v -> processCheckout());
    }

    private void processCheckout() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        String userId = auth.getCurrentUser().getUid();

        List<CartClass> items = CartManager.getInstance().getCartItems();

        if (items.isEmpty()) {
            Toast.makeText(this, "Cart is empty", Toast.LENGTH_SHORT).show();
            return;
        }

        WriteBatch batch = db.batch();

        for (CartClass item : items) {
            ProductClass product = item.getProduct();
            int orderedQuantity = item.getQuantity();

            DocumentReference orderRef = db.collection("Orders")
                    .document(currentUser.getEmail())
                    .collection("Items")
                    .document();
            Map<String, Object> orderData = new HashMap<>();
            orderData.put("productName", product.getProductName());
            orderData.put("price", product.getproductPrice());
            orderData.put("quantity", orderedQuantity);

            batch.set(orderRef, orderData);

            DocumentReference productRef = db.collection("Categories")
                    .document(product.getcategoryName())
                    .collection("Products")
                    .document(product.getProductName());

            batch.update(productRef, "productQuantity",
                    FieldValue.increment(-orderedQuantity));
        }

        batch.commit()
                .addOnSuccessListener(unused -> {
                    CartManager.getInstance().clearCart();
                    cartAdapter.notifyDataSetChanged();
                    Toast.makeText(this, "Order placed successfully", Toast.LENGTH_LONG).show();
                    finish(); // Go back or navigate as needed
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Order failed: " + e.getMessage(), Toast.LENGTH_LONG).show()
                );
    }

}