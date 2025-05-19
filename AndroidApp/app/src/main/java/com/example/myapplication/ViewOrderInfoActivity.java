package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class ViewOrderInfoActivity extends AppCompatActivity {

    TextView textViewProductId, textViewCategoryName, textViewProductName, textViewQuantity, textViewDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_order_info);

        textViewProductId = findViewById(R.id.tv_VP_ProductId);
        textViewCategoryName = findViewById(R.id.tv_VP_CategoryName);
        textViewProductName = findViewById(R.id.tv_VP_ProductName);
        textViewQuantity = findViewById(R.id.tv_VP_Quantity);
        textViewDate =  findViewById(R.id.tv_VP_Date);
        Intent intent = this.getIntent();

        textViewProductId.setText("Product Id: " + intent.getStringExtra("ProductID"));
        textViewCategoryName.setText("Category Name: " + intent.getStringExtra("CategoryName"));
        textViewProductName.setText("Product Name: " + intent.getStringExtra("ProductName"));
        textViewQuantity.setText("Quantity: " + intent.getStringExtra("Quantity"));
        textViewDate.setText("Date is: " + intent.getStringExtra("Date"));
    }
}