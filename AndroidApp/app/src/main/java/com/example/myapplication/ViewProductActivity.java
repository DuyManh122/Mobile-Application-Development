package com.example.myapplication;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class ViewProductActivity extends AppCompatActivity {

    RecyclerView flowerRecyclerView;
    ProductAdapter flowerAdapter;
    List<ProductClass> flowerList = new ArrayList<>();
    FirebaseFirestore db;

    ImageView imageViewHomeActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_product);

        flowerRecyclerView = findViewById(R.id.category_flowerRecyclerView);
        flowerRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        flowerAdapter = new ProductAdapter(flowerList, product -> {
            showQuantityDialog(product);
        });
        flowerRecyclerView.setAdapter(flowerAdapter);

        imageViewHomeActivity = findViewById(R.id.app_back_user_button);
        db = FirebaseFirestore.getInstance();

        imageViewHomeActivity.setOnClickListener(v -> finish());

        String categoryName = getIntent().getStringExtra("categoryName");
        if (categoryName != null) {
            loadProductsByCategory(categoryName);
        }
    }

    private void loadProductsByCategory(String categoryName) {
        db.collection("Categories")
                .document(categoryName)
                .collection("Products")
                .get()
                .addOnSuccessListener(snapshot -> {
                    flowerList.clear();
                    for (QueryDocumentSnapshot doc : snapshot) {
                        ProductClass product = doc.toObject(ProductClass.class);
                        flowerList.add(product);
                    }
                    flowerAdapter.updateData(flowerList);
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Error loading products", Toast.LENGTH_SHORT).show()
                );
    }

    private void showQuantityDialog(ProductClass product) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter quantity");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        builder.setView(input);

        builder.setPositiveButton("Add to Cart", (dialog, which) -> {
            int quantity = Integer.parseInt(input.getText().toString());
            if (quantity > 0 && quantity < product.getproductQuantity()) {
                CartManager.getInstance().addToCart(new CartClass(product, quantity));
                Toast.makeText(this, "Added to cart", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Please enter valid quantity",Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        builder.show();
    }
}
