package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ViewProductActivity extends AppCompatActivity {

    private ListView listViewProducts;
    private FirebaseFirestore db;
    private ArrayList<ProductClass> productList = new ArrayList<>();
    private ArrayAdapter<String> listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_product);

        listViewProducts = findViewById(R.id.lst_L_Products);
        db = FirebaseFirestore.getInstance();

        fetchProductsFromFirestore();
    }

    private void fetchProductsFromFirestore() {
        db.collection("Products")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        ArrayList<String> displayList = new ArrayList<>();
                        productList.clear();

                        for (DocumentSnapshot doc : queryDocumentSnapshots.getDocuments()) {
                            String productId = doc.getId();
                            String productName = doc.getString("productName");
                            String categoryId = doc.getString("categoryId");
                            int price = doc.getLong("price") != null ? doc.getLong("price").intValue() : 0;
                            long quantity = doc.getLong("quantity") != null ? doc.getLong("quantity") : 0;

//                            ProductClass product = new ProductClass(productId, productName, categoryId, price, (int) quantity);
//                            productList.add(product);
                            displayList.add(productName);
                        }

                        listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, displayList);
                        listViewProducts.setAdapter(listAdapter);

//                        listViewProducts.setOnItemClickListener((adapterView, view, position, id) -> {
//                            ProductClass selectedProduct = productList.get(position);
//                            Intent intent = new Intent(ViewProductActivity.this, ViewProductInfoActivity.class);
//                            intent.putExtra("ProductID", selectedProduct.getProductId());
//                            intent.putExtra("ProductName", selectedProduct.getProductName());
//                            intent.putExtra("CategoryID", selectedProduct.getCategoryId());
//                            intent.putExtra("Price", String.valueOf(selectedProduct.getPrice()));
//                            intent.putExtra("Quantity", String.valueOf(selectedProduct.getQuantity()));
//                            startActivity(intent);
//                        });

                    } else {
                        Toast.makeText(this, "No products found.", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Error loading products: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });
    }
}
