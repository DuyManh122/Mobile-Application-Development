package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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

public class MainActivity extends AppCompatActivity {

    private TextView Email, Password;

    private Button buttonLogin, buttonCreateNewAccount;
    private FirebaseAuth mAuth;
    CheckBox checkboxRemember;
    SharedPreferences sharedPreferences;
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
        Email = findViewById(R.id.Email);
        Password = findViewById(R.id.Password);
        buttonLogin = findViewById(R.id.Button_login);
        buttonCreateNewAccount = findViewById(R.id.Register);

        checkboxRemember = findViewById(R.id.checkbox_remember);
        sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);

        String savedEmail = sharedPreferences.getString("email", "");
        String savedPassword = sharedPreferences.getString("password", "");

        if (!savedEmail.isEmpty()) Email.setText(savedEmail);
        if (!savedPassword.isEmpty()) Password.setText(savedPassword);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                log();
            }
        });
        buttonCreateNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });

    }

    private void log() {
        String EmailNew = Email.getText().toString().trim();
        String PasswordNew = Password.getText().toString().trim();

        //Check empty Email and Password
        if (EmailNew.isEmpty()) {
            Toast.makeText(MainActivity.this, "Email không được để trống", Toast.LENGTH_SHORT).show();
            return;
        }
        if (PasswordNew.isEmpty()) {
            Toast.makeText(MainActivity.this, "Mật khẩu không được để trống", Toast.LENGTH_SHORT).show();
            return;
        }

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(EmailNew, PasswordNew)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Đăng nhập thành công với email " + EmailNew,
                                    Toast.LENGTH_SHORT).show();
                            if (checkboxRemember.isChecked()) {
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("email", EmailNew);
                                editor.putString("password", PasswordNew);
                                editor.apply();
                            }
                        } else {
                            Toast.makeText(MainActivity.this, "Đăng nhập thất bại",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    void register (){
        Intent intent = new Intent(MainActivity.this, SecondaryActivity.class);
        startActivity(intent);
    }

}