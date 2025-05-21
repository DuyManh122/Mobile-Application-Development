package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class UserActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private String userUID;
    TextView userName;
    ImageView userAvatar;
    RecyclerView categoryRecyclerView;
    UserInformation userInfor;

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



        userName = findViewById(R.id.user_name);
        userAvatar = findViewById(R.id.user_avatar);
        categoryRecyclerView = findViewById(R.id.categoryRecyclerView);
        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));


        userName.setOnClickListener(v -> changeToUserInformationActivity());
        userAvatar.setOnClickListener(v -> changeToUserInformationActivity());

        loadDataFromFirestore(); //User Information
        loadCategoriesFromFirestore(); //Category
    }

    void changeToUserInformationActivity() {
        if (userInfor != null) {
            Intent intentUserInformation = new Intent(UserActivity.this, UserInformationActivity.class);
            intentUserInformation.putExtra("user_info", userInfor);
            startActivity(intentUserInformation);
        } else {
            Toast.makeText(this, "User data not loaded yet", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadDataFromFirestore() {
        userInfor = new UserInformation("Flower User", "Unknown", Gender.Other, "Unknown", "Unknown");
        db.collection("Users").document(userUID)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        userInfor = extractUserInfo(documentSnapshot);
                        updateUIWithData();
                    } else {
                        Toast.makeText(this, "No user data found", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Failed to load user data: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    private UserInformation extractUserInfo(DocumentSnapshot doc) {
        String name = doc.getString("name");
        String dob = doc.getString("dob");
        String genderStr = doc.getString("gender");
        String address = doc.getString("address");
        String phone = doc.getString("phoneNum");

        UserInformation userInfor = new UserInformation(name, dob, Gender.valueOf(genderStr), address, phone);
        return  userInfor;
    }


    private void loadCategoriesFromFirestore() {
        db.collection("Categories")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<Category> categoryList = new ArrayList<>();

                    for (DocumentSnapshot doc : queryDocumentSnapshots) {
                        String name = doc.getId();
                        Long drawableIdLong = doc.getLong("id");

                        if (drawableIdLong != null) {
                            int drawableId = drawableIdLong.intValue();
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
    private void updateUIWithData() {
        userName.setText(userInfor.getName());
    }
}