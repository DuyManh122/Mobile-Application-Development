package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AdminActivity extends AppCompatActivity {

    TextView textViewAddCategory, textViewAddProduct, textViewViewOrders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.admin_activity), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        textViewAddCategory = findViewById(R.id.txt_A_Add_Category);
        textViewAddProduct = findViewById(R.id.txt_A_Add_Product);
        textViewViewOrders = findViewById(R.id.txt_A_View_Orders);


        textViewAddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intentCategory = new Intent(AdminActivity.this, CategoryActivity.class);
                startActivity(intentCategory);
            }
        });
        textViewAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intentProduct = new Intent(AdminActivity.this, ProductActivity.class);
                startActivity(intentProduct);
            }
        });
        textViewViewOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentViewOrders = new Intent(AdminActivity.this, ViewOrderActivity.class);
                startActivity(intentViewOrders);
            }
        });

    }
}