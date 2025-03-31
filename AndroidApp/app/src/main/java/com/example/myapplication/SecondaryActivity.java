package com.example.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SecondaryActivity extends AppCompatActivity {
    private Intent intent;
    private TextView student;
    private String studentText;
    private Button backButton;
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_secondary);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.secondary), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        imageView = findViewById(R.id.imageView);
        student = findViewById(R.id.TextNeedToChange);
        backButton = findViewById(R.id.Back);

        intent = getIntent();
        Bundle bundle = intent.getBundleExtra("student_data");
        studentText = bundle.getString("student_text");
        student.setText(studentText);

        byte[] byteArray = bundle.getByteArray("picture_bytes");
        Bitmap personalImage = null;
        if (byteArray != null) {
            personalImage = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        } else {
            Log.e("Error", "Null bitmap");
        }

        imageView.setImageBitmap(personalImage);
        backButton.setOnClickListener(v -> {
                finish();
        });
    }
}