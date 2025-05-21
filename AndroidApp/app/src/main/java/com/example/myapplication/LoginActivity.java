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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.AuthResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class LoginActivity extends AppCompatActivity {

    TextView textViewGoToTheRegister;
    EditText editTextUserId, editTextPassword;
    Button buttonLogin;

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

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        textViewGoToTheRegister = findViewById(R.id.txt_L_GoToRegister);
        editTextUserId = findViewById(R.id.txt_L_UserId);
        editTextPassword = findViewById(R.id.txt_L_Password);
        buttonLogin = findViewById(R.id.btn_L_Login);

        textViewGoToTheRegister.setOnClickListener(v -> {
            Intent intentLogToReg = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intentLogToReg);
        });

        buttonLogin.setOnClickListener(view -> {
            String email = editTextUserId.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();

            if (TextUtils.isEmpty(email)) {
                editTextUserId.setError("Email is required.");
                return;
            }

            if (TextUtils.isEmpty(password)) {
                editTextPassword.setError("Password is required.");
                return;
            }

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(LoginActivity.this, task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Authentication successful.", Toast.LENGTH_SHORT).show();

                            if (email.equals("admin@gmail.com") && password.equals("123456")) {
                                Intent intentAdmin = new Intent(LoginActivity.this, AdminActivity.class);
                                startActivity(intentAdmin);
                            } else {
                                Intent intentUser = new Intent(LoginActivity.this, UserActivity.class);
                                startActivity(intentUser);
                            }
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, "Authentication failed: " + task.getException().getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });
        });
    }
}
