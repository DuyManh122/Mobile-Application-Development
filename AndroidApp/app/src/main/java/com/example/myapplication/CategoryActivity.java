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
    private int resourceId = 0;
    private FirebaseFirestore firestore;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        editTextCategoryName = findViewById(R.id.txt_c_categoryName);
        btnSubmit = findViewById(R.id.btn_c_submit);
        btnPickImage = findViewById(R.id.btn_c_pick_image);
        imagePreview = findViewById(R.id.img_c_preview);

        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser == null) {
            Toast.makeText(this, "You must be logged in", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        btnPickImage.setOnClickListener(view -> {
            showDrawablePickerDialog();
        });

        btnSubmit.setOnClickListener(view -> {
            String name = editTextCategoryName.getText().toString().trim();

            if (name.isEmpty() || selectedBitmap == null) {
                Toast.makeText(this, "Please enter name and select an image", Toast.LENGTH_SHORT).show();
                return;
            }

            updateFirebaseData(name, resourceId);
        });

//        AddSomeDefaultCategories();
    }


    private void showDrawablePickerDialog() {
        final String[] items = {"1", "2", "3", "4"};
        final int[] drawableIds = {
                R.drawable.ic_flower_vase_1,
                R.drawable.ic_flower_vase_2,
                R.drawable.ic_flower_vase_3,
                R.drawable.ic_flower_vase_4
        };

        new android.app.AlertDialog.Builder(this)
                .setTitle("Choose an image")
                .setItems(items, (dialog, which) -> {
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inJustDecodeBounds = true;
                    BitmapFactory.decodeResource(getResources(), drawableIds[which], options);

                    options.inSampleSize = calculateInSampleSize(options, 500, 500);
                    options.inJustDecodeBounds = false;
                    selectedBitmap = BitmapFactory.decodeResource(getResources(), drawableIds[which], options);

                    imagePreview.setImageBitmap(selectedBitmap);
                    resourceId =  drawableIds[which];
                })
                .show();
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {

        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = Math.min(heightRatio, widthRatio);
        }

        return inSampleSize;
    }

    private void AddSomeDefaultCategories() {
        updateFirebaseData("Spring", R.drawable.ic_flower_vase_spring);
        updateFirebaseData("Summer", R.drawable.ic_flower_vase_summer);
        updateFirebaseData("Autumn", R.drawable.ic_flower_vase_autumn);
        updateFirebaseData("Winter", R.drawable.ic_flower_vase_winter);
    }
    private void updateFirebaseData(String document, int id) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", id);

            firestore.collection("Categories").document(document).set(map)
                    .addOnSuccessListener(v -> showToast("Information updated successfully"))
                    .addOnFailureListener(e -> showToast("Error updating information: " + e.getMessage()));
    }

    void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
