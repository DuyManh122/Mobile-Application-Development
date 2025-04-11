package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SecondaryActivity extends AppCompatActivity {
    TextView emailRegister, passwordRegister;
    Button buttonRegister, buttonLogin;
    private FirebaseAuth mAuth;

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
        emailRegister = findViewById(R.id.Email_register);
        passwordRegister = findViewById(R.id.Password_register);
        buttonRegister = findViewById(R.id.Button_register);
        buttonLogin = findViewById(R.id.Button_login);

        mAuth = FirebaseAuth.getInstance();
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SecondaryActivity.this, MainActivity.class));
                finish();
            }
        });
    }
    private void register() {
        String emailRegisterNew = emailRegister.getText().toString().trim();
        String passwordRegisterNew = passwordRegister.getText().toString().trim();

        //Check empty Email and Password
        if (emailRegisterNew.isEmpty()) {
            Toast.makeText(SecondaryActivity.this, "Email không được để trống", Toast.LENGTH_SHORT).show();
            return;
        }
        if (passwordRegisterNew.isEmpty()) {
            Toast.makeText(SecondaryActivity.this, "Mật khẩu không được để trống", Toast.LENGTH_SHORT).show();
            return;
        }
        else if (passwordRegisterNew.length() < 6) {
            Toast.makeText(this, "Mật khẩu phải có ít nhất 6 ký tự", Toast.LENGTH_SHORT).show();
            return;
        }


        mAuth.createUserWithEmailAndPassword(emailRegisterNew, passwordRegisterNew)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(SecondaryActivity.this, "Registration successful with email " + emailRegisterNew,
                                    Toast.LENGTH_SHORT).show();

                            startActivity(new Intent(SecondaryActivity.this, MainActivity.class));
                            finish();
                        } else {
                            Toast.makeText(SecondaryActivity.this,
                                    "Đăng ký thất bại: " + task.getException().getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}