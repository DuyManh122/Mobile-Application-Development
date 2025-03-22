package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SecondaryActivity extends AppCompatActivity {
    private Intent intent;
    private EditText student;
    private String student_text;
    private Button update_change, restore_student_text;

    protected void updateStudentChange() {
        Bundle bundle = new Bundle();
        bundle.putString("teacher_text", student.getText().toString());
        intent.putExtra("teacher_data",bundle);
        setResult(33, intent);
        finish();
    }

    protected void restoreStudentText() {
        student.setText(student_text);
    }

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
        intent = getIntent();
        Bundle bundle = intent.getBundleExtra("student_data");
        student_text = bundle.getString("student_text");
        student = findViewById(R.id.TextNeedToChange);
        student.setText(student_text);

        update_change = findViewById(R.id.Change);
        restore_student_text = findViewById(R.id.Restore);

        update_change.setOnClickListener(
                v -> updateStudentChange()
        );

        restore_student_text.setOnClickListener(
                v -> restoreStudentText()
        );
    }
}