package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CategoryActivity extends AppCompatActivity {

    private static final int IMAGE_PICK_CODE = 1001;

    EditText editTextCategoryName;
    Button btnSubmit, btnPickImage;
    ImageView imagePreview;

    private Bitmap selectedBitmap;

    private FirebaseFirestore firestore;
    private FirebaseStorage storage;
    private StorageReference storageRef;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        editTextCategoryName = findViewById(R.id.txt_c_categoryName);
        btnSubmit = findViewById(R.id.btn_c_submit);
        btnPickImage = findViewById(R.id.btn_c_pick_image);
        imagePreview = findViewById(R.id.img_c_preview);

        // Initialize Firebase
        firestore = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference().child("category_icons");
        auth = FirebaseAuth.getInstance();

        // Ensure user is logged in
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser == null) {
            Toast.makeText(this, "You must be logged in", Toast.LENGTH_SHORT).show();
            finish(); // Close activity if not authenticated
            return;
        }

        btnPickImage.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, IMAGE_PICK_CODE);
        });

        btnSubmit.setOnClickListener(view -> {
            String name = editTextCategoryName.getText().toString().trim();

            if (name.isEmpty() || selectedBitmap == null) {
                Toast.makeText(this, "Please enter name and select an image", Toast.LENGTH_SHORT).show();
                return;
            }

            uploadImageAndSaveCategory(name, selectedBitmap);
        });

        // OPTIONAL: Auto insert default categories
         AddSomeDefaultCategories();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_PICK_CODE && resultCode == Activity.RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            try {
                selectedBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                imagePreview.setImageBitmap(selectedBitmap); // Show preview
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Failed to load image", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void uploadImageAndSaveCategory(String name, Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] imageData = baos.toByteArray();

        String fileName = UUID.randomUUID().toString() + ".png";
        StorageReference imageRef = storageRef.child(fileName);

        UploadTask uploadTask = imageRef.putBytes(imageData);
        uploadTask
                .addOnSuccessListener(taskSnapshot -> imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                    String imageUrl = uri.toString();

                    Map<String, Object> categoryData = new HashMap<>();
                    categoryData.put("name", name);
                    categoryData.put("iconUrl", imageUrl);

                    firestore.collection("Categories").add(categoryData)
                            .addOnSuccessListener(documentReference -> {
                                Toast.makeText(this, "Category added successfully", Toast.LENGTH_SHORT).show();
                                finish();
                            })
                            .addOnFailureListener(e -> Toast.makeText(this, "Failed to save category", Toast.LENGTH_SHORT).show());
                }))
                .addOnFailureListener(e -> Toast.makeText(this, "Image upload failed", Toast.LENGTH_SHORT).show());
    }

    // Optional helper to add default categories
    private void AddSomeDefaultCategories() {
        addCategoryWithBitmap("Spring", R.drawable.ic_flower_vase_spring);
        addCategoryWithBitmap("Summer", R.drawable.ic_flower_vase_summer);
        addCategoryWithBitmap("Autumn", R.drawable.ic_flower_vase_autumn);
        addCategoryWithBitmap("Winter", R.drawable.ic_flower_vase_winter);
    }

    private void addCategoryWithBitmap(String name, int drawableResId) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), drawableResId);
        uploadImageAndSaveCategory(name, bitmap);
    }
}
