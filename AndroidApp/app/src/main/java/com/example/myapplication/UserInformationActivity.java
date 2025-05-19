package com.example.myapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class UserInformationActivity extends AppCompatActivity {
    UserInformation userInfor;
    UserInfoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_information);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_user_information), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button logoutButton = findViewById(R.id.ExitButton);
        ImageView changeUserInfoButton = findViewById(R.id.account_change_user_info);

        changeUserInfoButton.setOnClickListener(v -> changeUserInformation());
        logoutButton.setOnClickListener(v -> Logout());
    }

    void
}