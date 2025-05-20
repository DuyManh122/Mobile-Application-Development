package com.example.myapplication;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserInformationActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_CHANGE_USER_INFO = 99;
    private static final String CHANNEL_ID = "my_channel_id";

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private String userUID;

    private UserInformation userInfor;
    private List<UserInfo> userList;
    private UserInfoAdapter adapter;

    private TextView userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information);

        createNotificationChannel();

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        userUID = mAuth.getCurrentUser().getUid();

        userInfor = (UserInformation) getIntent().getSerializableExtra("user_info");
        initializeViews();
        setupRecyclerView();
        setupButtons();

        if (userInfor != null) {
            updateUIWithData(userInfor);
        } else {
            loadUserData();
        }

        ImageView backButton = findViewById(R.id.account_back_user_button);
        backButton.setOnClickListener(v -> navigateBackToUserActivity());
    }

    private void navigateBackToUserActivity() {
        Intent intent = new Intent(this, UserActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }

    private void initializeViews() {
        userName = findViewById(R.id.HelloUser);
    }

    private void setupRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        userList = new ArrayList<>();
        adapter = new UserInfoAdapter(userList);
        recyclerView.setAdapter(adapter);
    }

    private void setupButtons() {
        Button logoutButton = findViewById(R.id.ExitButton);
        ImageView changeButton = findViewById(R.id.account_change_user_info);

        changeButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, ChangeUserInforActivity.class);
            intent.putExtra("user_info", userInfor);
            startActivityForResult(intent, REQUEST_CODE_CHANGE_USER_INFO);
        });

        logoutButton.setOnClickListener(v -> logoutUser());
    }

    private void loadUserData() {
        db.collection("Users").document(userUID)
                .get()
                .addOnSuccessListener(this::onUserDataLoaded)
                .addOnFailureListener(e -> showToast("Failed to load user data: " + e.getMessage()));
    }

    private void onUserDataLoaded(DocumentSnapshot doc) {
        if (doc.exists()) {
            userInfor = new UserInformation(
                    doc.getString("name"),
                    doc.getString("dob"),
                    getEnumSafe(Gender.class, doc.getString("gender")),
                    doc.getString("address"),
                    doc.getString("phoneNum")
            );
            updateUIWithData(userInfor);
        } else {
            showToast("No user data found");
        }
    }

    private void updateUIWithData(UserInformation user) {
        userList.clear();
        userList.add(new UserInfo("Name", getOrDefault(user.getName())));
        userList.add(new UserInfo("Gender", user.getSex() != null ? user.getSex().toString() : "Not Set"));
        userList.add(new UserInfo("Date of birth", getOrDefault(user.getDate_of_birth())));
        userList.add(new UserInfo("Address", getOrDefault(user.getAddress())));
        userList.add(new UserInfo("Phone Number", getOrDefault(user.getPhone_number())));
        adapter.notifyDataSetChanged();
        userName.setText(getOrDefault(user.getName(), "USER").toUpperCase());
    }

    private void logoutUser() {
        mAuth.signOut();
        db.terminate().addOnCompleteListener(task ->
                FirebaseFirestore.getInstance().clearPersistence().addOnCompleteListener(clearTask -> {
                    startActivity(new Intent(this, LoginActivity.class)
                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                    showToast("Signed out successfully");
                    finish();
                })
        ).addOnFailureListener(e -> showToast("Error clearing Firestore data: " + e.getMessage()));
    }

    private void updateFirebaseData() {
        if (userInfor != null) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", safeTrim(userInfor.getName()));
            map.put("dob", safeTrim(userInfor.getDate_of_birth()));
            map.put("gender", userInfor.getSex() != null ? userInfor.getSex().toString() : "");
            map.put("address", safeTrim(userInfor.getAddress()));
            map.put("phoneNum", safeTrim(userInfor.getPhone_number()));

            db.collection("Users").document(userUID).set(map)
                    .addOnSuccessListener(v -> showToast("Information updated successfully"))
                    .addOnFailureListener(e -> showToast("Error updating information: " + e.getMessage()));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHANGE_USER_INFO && resultCode == RESULT_OK) {
            UserInformation updatedUser = (UserInformation) data.getSerializableExtra("updated_user_info");
            if (updatedUser != null) {
                userInfor = updatedUser;
                updateUIWithData(userInfor);
                updateFirebaseData();
            }
        }
    }

    private void createNotificationChannel() {
        NotificationManager manager = getSystemService(NotificationManager.class);
        if (manager != null) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "User Info Notifications",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            channel.setDescription("Channel for app notifications");
            manager.createNotificationChannel(channel);
        }
    }

    private <T extends Enum<T>> T getEnumSafe(Class<T> enumType, String value) {
        try {
            return value != null ? Enum.valueOf(enumType, value) : null;
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    private String getOrDefault(String value) {
        return getOrDefault(value, "Not Set");
    }

    private String getOrDefault(String value, String defaultValue) {
        return (value != null && !value.trim().isEmpty()) ? value : defaultValue;
    }

    private String safeTrim(String s) {
        return s != null ? s.trim() : "";
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
