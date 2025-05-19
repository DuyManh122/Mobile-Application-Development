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

import java.util.ArrayList;
import java.util.List;

public class UserActivity extends AppCompatActivity {

    TextView userName;
    ImageView userAvatar;
    RecyclerView categoryRecyclerView;

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

        userName = findViewById(R.id.user_name);
        userAvatar = findViewById(R.id.user_avatar);
        categoryRecyclerView = findViewById(R.id.categoryRecyclerView);


        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        Bitmap springFlower = BitmapFactory.decodeResource(getResources(), R.drawable.ic_flower_vase_spring);
        Bitmap summerFlower = BitmapFactory.decodeResource(getResources(), R.drawable.ic_flower_vase_summer);
        Bitmap autumnFlower = BitmapFactory.decodeResource(getResources(), R.drawable.ic_flower_vase_autumn);
        Bitmap winterFlower = BitmapFactory.decodeResource(getResources(), R.drawable.ic_flower_vase_winter);

        userName.setOnClickListener(v -> changeToUserInformationActivity());
        userAvatar.setOnClickListener(v -> changeToUserInformationActivity());

        List<Category> categoryList = new ArrayList<>();
        categoryList.add(new Category("Spring", springFlower));
        categoryList.add(new Category("Summer", summerFlower));
        categoryList.add(new Category("Autumn", autumnFlower));
        categoryList.add(new Category("Winter", winterFlower));

        CategoryAdapter adapter = new CategoryAdapter(categoryList);
        categoryRecyclerView.setAdapter(adapter);
    }

    void changeToUserInformationActivity() {
        Intent intentUserInformation = new Intent(UserActivity.this, UserInformationActivity.class);
        startActivity(intentUserInformation);
    }


}