package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ViewOrderActivity extends AppCompatActivity {

    private ListView listViewOrders;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    private List<OrderClass> orderList = new ArrayList<>();
    private ArrayAdapter<String> listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_order);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.view_order_activity), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        listViewOrders = findViewById(R.id.lst_L_Orders);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        fetchOrdersFromFirestore();
    }

    private void fetchOrdersFromFirestore() {
        String userUID = mAuth.getCurrentUser().getUid();

        db.collection("Users").document(userUID)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        List<Map<String, Object>> ordersData = (List<Map<String, Object>>) documentSnapshot.get("orders");

                        if (ordersData != null && !ordersData.isEmpty()) {
                            ArrayList<String> displayList = new ArrayList<>();
                            orderList.clear();

                            for (Map<String, Object> orderMap : ordersData) {
                                String productId = (String) orderMap.get("productID");
                                String categoryName = (String) orderMap.get("categoryName");
                                String productName = (String) orderMap.get("productName");
                                long quantity = orderMap.get("quantity") != null ? (long) orderMap.get("quantity") : 0;
                                int date = (int) orderMap.get("date");

                                OrderClass order = new OrderClass(productId, categoryName, productName, (int) quantity, date);
                                orderList.add(order);
                                displayList.add(productName + " (" + quantity + ")");
                            }

                            listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, displayList);
                            listViewOrders.setAdapter(listAdapter);

                            listViewOrders.setOnItemClickListener((adapterView, view, position, id) -> {
                                OrderClass selectedOrder = orderList.get(position);
                                Intent intent = new Intent(ViewOrderActivity.this, ViewOrderInfoActivity.class);
                                intent.putExtra("ProductID", selectedOrder.getProductId());
                                intent.putExtra("CategoryName", selectedOrder.getCategoryName());
                                intent.putExtra("ProductName", selectedOrder.getProductName());
                                intent.putExtra("Quantity", String.valueOf(selectedOrder.getQuantity()));
                                intent.putExtra("Date", selectedOrder.getDate());
                                startActivity(intent);
                            });

                        } else {
                            Toast.makeText(ViewOrderActivity.this, "No orders found.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(ViewOrderActivity.this, "User data not found.", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(ViewOrderActivity.this, "Failed to load data: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });
    }
}
