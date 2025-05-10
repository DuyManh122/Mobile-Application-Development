package com.example.myapplication;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.myapplication.databinding.ActivityProductBinding;

import java.util.Vector;

public class ProductActivity extends AppCompatActivity {

    EditText editTextProductId, editTextProductName, editTextPrice, editTextQuantity;
    Spinner spinnerCategoryName;
    Button buttonAddProduct;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        editTextProductId = findViewById(R.id.txt_P_ProductId);
        editTextProductName = findViewById(R.id.txt_P_ProductName);
        editTextPrice =  findViewById(R.id.txt_P_Price);
        editTextQuantity = findViewById(R.id.txt_P_Quantity);

        buttonAddProduct = findViewById(R.id.btn_P_AddProduct);
        spinnerCategoryName = findViewById(R.id.sp_P_Category);


//        ArrayAdapter ad = new ArrayAdapter(this, android.R.layout.simple_spinner_item, vecCategory);
//        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinnerCategoryName.setAdapter(ad);

        buttonAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editTextProductName.getText().toString().isEmpty() || editTextPrice.getText().toString().isEmpty() || editTextQuantity.getText().toString().isEmpty()) {
                    Toast.makeText(ProductActivity.this, "Fields Can't be blank", Toast.LENGTH_SHORT).show();
                } else {
                    if (true) {
                        Toast.makeText(ProductActivity.this, "New Product insert", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ProductActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}