package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private EditText student_text;
    private Button student_submit, student_clear;
    private TextView teacher_text;
    private void studentSubmitText() {
        String text = student_text.getText().toString();
        Bundle bundle = new Bundle();
        bundle.putString("student_text", text);
        Intent intent = new Intent(MainActivity.this, SecondaryActivity.class);
        intent.putExtra("student_data", bundle);
        MainActivity.this.startActivityForResult(intent, 99);
    }

    private void studentClearText() {
        student_text.setText("");
        teacher_text.setText("");
        Toast.makeText(this, "Delete Text Successfully!", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        teacher_text = findViewById(R.id.TeacherText);
        student_text = findViewById(R.id.StudentText);
        student_submit = findViewById(R.id.StudentSubmit);
        student_clear = findViewById(R.id.StudentClearText);


        student_submit.setOnClickListener(
                v -> studentSubmitText()
        );
        student_clear.setOnClickListener(
                v -> studentClearText()
        );
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 99 && resultCode == 33) {
            Bundle result_bundle = data.getBundleExtra("teacher_data");
            String result_teacher_str = result_bundle.getString("teacher_text");
            Log.d("result", result_teacher_str);

            teacher_text.setText(result_teacher_str);
        }
    }
}