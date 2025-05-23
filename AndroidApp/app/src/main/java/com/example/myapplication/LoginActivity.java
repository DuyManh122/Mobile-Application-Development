package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private TextView textViewGoToTheRegister;
    private EditText editTextUserId, editTextPassword;
    private Button buttonLogin;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.login_activity), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Firebase Authentication instance
        mAuth = FirebaseAuth.getInstance();

        // Ánh xạ các thành phần UI
        textViewGoToTheRegister = findViewById(R.id.txt_L_GoToRegister);
        editTextUserId = findViewById(R.id.txt_L_UserId);
        editTextPassword = findViewById(R.id.txt_L_Password);
        buttonLogin = findViewById(R.id.btn_L_Login);

        // Chuyển sang màn hình đăng ký
        textViewGoToTheRegister.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        // Xử lý khi nhấn nút "Login"
        buttonLogin.setOnClickListener(view -> {
            String email = editTextUserId.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();

            // Kiểm tra rỗng
            if (TextUtils.isEmpty(email)) {
                editTextUserId.setError("Email is required.");
                return;
            }

            if (TextUtils.isEmpty(password)) {
                editTextPassword.setError("Password is required.");
                return;
            }

            // Thực hiện đăng nhập với Firebase
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(LoginActivity.this, task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();

                            // Phân quyền admin hay user
                            if (email.equals("admin@gmail.com")) {
                                startActivity(new Intent(LoginActivity.this, AdminActivity.class));
                            } else {
                                startActivity(new Intent(LoginActivity.this, UserActivity.class));
                            }

                            finish(); // Đóng LoginActivity

                        } else {
                            Toast.makeText(LoginActivity.this, "Lỗi: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
        });
    }
}
