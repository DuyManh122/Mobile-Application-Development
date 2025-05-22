package com.example.myapplication;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private String userUID;

    TextView userName;
    ImageView userAvatar, iconCart;
    EditText searchProduct;
    RecyclerView categoryRecyclerView;

    UserInformation userInfor;
    ProductAdapter flowerAdapter;

    List<ProductClass> allFlowers = new ArrayList<>();
    Map<String, List<ProductClass>> flowerMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user);



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_user), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        userUID = mAuth.getCurrentUser().getUid();

        searchProduct = findViewById(R.id.txt_U_searchBar);
        userName = findViewById(R.id.user_name);
        userAvatar = findViewById(R.id.user_avatar);
        iconCart = findViewById(R.id.cartIcon);
        categoryRecyclerView = findViewById(R.id.categoryRecyclerView);
        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        RecyclerView flowerRecyclerView = findViewById(R.id.flowerRecyclerView);
        flowerRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        flowerAdapter = new ProductAdapter(new ArrayList<>(), product -> {
            showQuantityDialog(product);
        });        flowerRecyclerView.setAdapter(flowerAdapter);

        userName.setOnClickListener(v -> changeToUserInformationActivity());
        userAvatar.setOnClickListener(v -> changeToUserInformationActivity());

        searchProduct.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String keyword = s.toString().trim().toLowerCase();
                List<ProductClass> filtered = new ArrayList<>();

                if (!keyword.isEmpty()) {
                    for (ProductClass flower : allFlowers) {
                        if (flower.getProductName() != null &&
                                flower.getProductName().toLowerCase().contains(keyword)) {
                            filtered.add(flower);
                        }
                    }
                } else {
                    filtered.addAll(allFlowers);
                }

                flowerAdapter.updateData(filtered);
            }

            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}
        });

        iconCart.setOnClickListener(v -> {
                Intent intentToCart = new Intent(UserActivity.this, CartActivity.class);
                startActivity(intentToCart);
        });

        loadUserInfo();
        loadCategoriesFromFirestore();
        loadAllFlowers();
    }

    private void changeToUserInformationActivity() {
        if (userInfor != null) {
            Intent intent = new Intent(UserActivity.this, UserInformationActivity.class);
            intent.putExtra("user_info", userInfor);
            startActivity(intent);
        } else {
            Toast.makeText(this, "User data not loaded yet", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadUserInfo() {
        userInfor = new UserInformation("Flower User", "Unknown", Gender.Other, "Unknown", "Unknown");

        db.collection("Users").document(userUID)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        userInfor = extractUserInfo(documentSnapshot);
                        userName.setText(userInfor.getName());
                    } else {
                        Toast.makeText(this, "No user data found", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Failed to load user data: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                );
    }

    private UserInformation extractUserInfo(DocumentSnapshot doc) {
        String name = doc.getString("name");
        String dob = doc.getString("dob");
        String genderStr = doc.getString("gender");
        String address = doc.getString("address");
        String phone = doc.getString("phoneNum");

        return new UserInformation(name, dob, Gender.valueOf(genderStr), address, phone);
    }

    private void loadCategoriesFromFirestore() {
        db.collection("Categories")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<Category> categoryList = new ArrayList<>();

                    for (DocumentSnapshot doc : queryDocumentSnapshots) {
                        String name = doc.getId();
                        String drawableString = doc.getString("id");

                        if (drawableString != null) {
                            int drawableId = getResources().getIdentifier(drawableString, "drawable", getPackageName());
                            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), drawableId);
                            categoryList.add(new Category(name, bitmap));
                        } else {
                            Toast.makeText(this, "Drawable ID missing for " + name, Toast.LENGTH_SHORT).show();
                        }
                    }

                    CategoryAdapter adapter = new CategoryAdapter(categoryList);
                    categoryRecyclerView.setAdapter(adapter);
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Failed to load categories: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                );
    }

    private void loadAllFlowers() {
        allFlowers.clear();
        flowerMap.clear();

        db.collection("Categories").get().addOnSuccessListener(categorySnapshots -> {
            int totalCategories = categorySnapshots.size();
            final int[] categoriesLoaded = {0};

            for (QueryDocumentSnapshot categoryDoc : categorySnapshots) {
                String category = categoryDoc.getId();

                db.collection("Categories")
                        .document(category)
                        .collection("Products")
                        .get()
                        .addOnSuccessListener(productSnapshots -> {
                            List<ProductClass> categoryFlowers = new ArrayList<>();

                            for (QueryDocumentSnapshot productDoc : productSnapshots) {
                                ProductClass flower = productDoc.toObject(ProductClass.class);
                                flower.setcategoryName(category);
                                categoryFlowers.add(flower);
                            }

                            flowerMap.put(category, categoryFlowers);
                            allFlowers.addAll(categoryFlowers);

                            categoriesLoaded[0]++;
                            if (categoriesLoaded[0] == totalCategories) {
                                flowerAdapter.updateData(allFlowers);
                            }
                        })
                        .addOnFailureListener(e -> {
                            categoriesLoaded[0]++;
                            if (categoriesLoaded[0] == totalCategories) {
                                flowerAdapter.updateData(allFlowers);
                            }
                            Toast.makeText(this, "Failed to load products from: " + category, Toast.LENGTH_SHORT).show();
                        });
            }
        });
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
