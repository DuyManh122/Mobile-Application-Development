package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
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

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {
    TextView textViewGoToTheRegister;

    EditText editTextUserId, editTextPassword;
    Button buttonLogin;

    private DBHelper dbHelper;
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

        dbHelper = new DBHelper(this);
        dbHelper.openDB();

        textViewGoToTheRegister = findViewById(R.id.txt_L_GoToRegister);

        textViewGoToTheRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intentLogToReg = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intentLogToReg);

            }
        });


        editTextUserId = findViewById(R.id.txt_L_UserId);
        editTextPassword = findViewById(R.id.txt_L_Password);
        buttonLogin = findViewById(R.id.btn_L_Login);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<UserClass> userDetails = dbHelper.ValidLogin(editTextUserId.getText().toString(),
                        editTextPassword.getText().toString());
                if (userDetails.size()!=0){
                    UserClass user = userDetails.get(0);
                    String userType = user.getUserType();//Admin
                    Toast.makeText(LoginActivity.this, "User found " + userType, Toast.LENGTH_LONG).show();
                    if (userType.equals("Admin")){
                        Intent intentRegister = new Intent(LoginActivity.this, AdminActivity.class);
                        startActivity(intentRegister);
                    } else {
                        Intent intentReg = new Intent(LoginActivity.this, UserActivity.class);
                        startActivity(intentReg);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Invalid User", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}