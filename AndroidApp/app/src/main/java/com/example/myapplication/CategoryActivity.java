package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CategoryActivity extends AppCompatActivity {
    EditText editTextCategoryID, editTextCategoryName;
    Button btnSubmit;
    private DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_category);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.category_activity), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        dbHelper = new DBHelper(this);
        dbHelper.openDB();

        btnSubmit = findViewById(R.id.btn_c_submit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (editTextCategoryID.getText().toString().isEmpty() || editTextCategoryName.getText().toString().isEmpty()) {
                    Toast.makeText(CategoryActivity.this, "Fields can't be blank", Toast.LENGTH_SHORT).show();
                } else {
                    CategoryClass categoryClass = new CategoryClass(editTextCategoryID.getText().toString(), editTextCategoryName.getText().toString());
                    if (dbHelper.InsertCategory(categoryClass)) {
                        Toast.makeText(getApplicationContext(), "New Category Insert", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_LONG).show();
                    }
                }

            }
        });

    }
}