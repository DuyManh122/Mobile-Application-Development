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

public class DisplayStudentInformation extends AppCompatActivity {
    private TextView student;
    private Button backButton;
    private ImageView imageView;
    StudentDatabaseHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_display_student_information);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_display_student_information), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        imageView = findViewById(R.id.imageView);
        student = findViewById(R.id.TextNeedToChange);
        backButton = findViewById(R.id.Back);

        dbHelper = new StudentDatabaseHelper(this);

        String mssv = getIntent().getStringExtra("mssv");
        if (mssv != null) {
            StudentInformation studentInformation = dbHelper.getStudentByMssv(mssv);
            if (student != null) {
                student.setText(studentInformation.toString());
                Bitmap personalImage = studentInformation.getPersonalImage();
                imageView.setImageBitmap(personalImage);
            }
        }
        backButton.setOnClickListener(v -> {
            finish();
        });
    }
}