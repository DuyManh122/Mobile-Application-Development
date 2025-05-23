package com.example.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class ProductActivity extends AppCompatActivity {

    FirebaseFirestore db;
    FirebaseAuth mAuth;
    EditText editTextProductDescription, editTextProductName, editTextPrice, editTextQuantity;
    Spinner spinnerCategoryName;
    Button buttonAddProduct, buttonPickImage;

    List<String> categoryNameList = new ArrayList<>();
    ArrayAdapter<String> adapter;

    ImageView imagePreview;

    private Bitmap selectedBitmap;
    private String resourceId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        editTextProductName =  findViewById(R.id.txt_P_ProductName);
        editTextPrice = findViewById(R.id.txt_P_Price);
        editTextQuantity = findViewById(R.id.txt_P_Quantity);
        editTextProductDescription = findViewById(R.id.txt_P_Description);

        buttonAddProduct = findViewById(R.id.btn_P_AddProduct);
        buttonPickImage  = findViewById(R.id.btn_P_AddImage);
        spinnerCategoryName = findViewById(R.id.sp_P_Category);
        imagePreview = findViewById(R.id.img_P_ProductImage);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        loadCategoriesFromFirestore();

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categoryNameList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategoryName.setAdapter(adapter);

        buttonAddProduct.setOnClickListener(v -> addProductToCategory());
        buttonPickImage.setOnClickListener(v -> showDrawablePickerDialog());

        addDefaultProducts();
    }


    private void loadCategoriesFromFirestore() {
        db.collection("Categories")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (DocumentSnapshot doc : queryDocumentSnapshots) {
                        String name = doc.getId();
                        categoryNameList.add(name);
                    }
                    adapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Failed to load categories: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                );

    }


    private void addProductToCategory() {
        String name = editTextProductName.getText().toString().trim();
        String description = editTextProductDescription.getText().toString().trim();
        String priceStr = editTextPrice.getText().toString().trim();
        String quantityStr = editTextQuantity.getText().toString().trim();
        String category = spinnerCategoryName.getSelectedItem().toString();

        if (name.isEmpty() || description.isEmpty() || priceStr.isEmpty() || quantityStr.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        int price;
        int quantity;

        try {
            price = Integer.parseInt(priceStr);
            quantity = Integer.parseInt(quantityStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid price or quantity", Toast.LENGTH_SHORT).show();
            return;
        }

        ProductClass product = new ProductClass(description, name, category, price, quantity, resourceId);

        db.collection("Categories")
                .document(category)
                .collection("Products")
                .document(name)
                .set(product)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(this, "Product added successfully", Toast.LENGTH_SHORT).show();
                    finish();
                })

                .addOnFailureListener(e ->
                        Toast.makeText(this, "Failed to add product: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    private void showDrawablePickerDialog() {
        final String[] items = {"Cyclamen", "iris", "narcissus", "tulip"};
        final int[] drawableIds = {
                R.drawable.ic_flower_custom_cyclamen,
                R.drawable.ic_flower_custom_iris,
                R.drawable.ic_flower_custom_nacissus,
                R.drawable.ic_flower_spring_tuilip
        };

        new android.app.AlertDialog.Builder(this)
                .setTitle("Choose an image")
                .setItems(items, (dialog, which) -> {
                    int selectedResId = drawableIds[which];

                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inJustDecodeBounds = true;
                    BitmapFactory.decodeResource(getResources(), selectedResId, options);

                    options.inSampleSize = calculateInSampleSize(options, 500, 500);
                    options.inJustDecodeBounds = false;
                    selectedBitmap = BitmapFactory.decodeResource(getResources(), selectedResId, options);

                    imagePreview.setImageBitmap(selectedBitmap);
                    resourceId =  getResources().getResourceEntryName(selectedResId);
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


    private void addDefaultProducts() {
        String defaultCategory = "DefaultCategory";

        List<ProductClass> defaultProducts = new ArrayList<>();
        defaultProducts.add(new ProductClass("Elegant and charming", "Hyacinth", "Spring", 15000, 20, "ic_flower_spring_hyancinth"));
        defaultProducts.add(new ProductClass("Delicate and graceful", "Sakura", "Spring", 20000, 15, "ic_flower_spring_sakura"));
        defaultProducts.add(new ProductClass("Bright and cheerful", "Tulip", "Spring", 12000, 30, "ic_flower_spring_tuilip"));

        defaultProducts.add(new ProductClass("Bold and exotic", "Hibiscus", "Summer", 15000, 20, "ic_flower_summer_hibiscus"));
        defaultProducts.add(new ProductClass("Calming and fragrant", "Lavender", "Summer", 20000, 15, "ic_flower_summer_lavender"));
        defaultProducts.add(new ProductClass("Radiant and happy", "Sunflower", "Summer", 12000, 30, "ic_flower_summer_sunflower"));

        defaultProducts.add(new ProductClass("Warm and rich", "Autumn joy", "Autumn", 15000, 20, "ic_flower_autumn_autumnjoy"));
        defaultProducts.add(new ProductClass("Classic and full", "Chrysanthemums", "Autumn", 20000, 15, "ic_flower_autumn_chrysanthemums"));
        defaultProducts.add(new ProductClass("Soft and airy", "Cosmos flower", "Autumn", 12000, 30, "ic_flower_autumn_cosmos"));

        defaultProducts.add(new ProductClass("Elegant winter bloom", "Christmas rose", "Winter", 15000, 20, "ic_flower_winter_christmasrose"));
        defaultProducts.add(new ProductClass("Festive and vibrant", "Poinsettias", "Winter", 20000, 15, "ic_flower_winter_poinsettias"));
        defaultProducts.add(new ProductClass("Delicate and frosty", "Snowflake", "Winter", 12000, 30, "ic_flower_winter_snowflake"));



        for (ProductClass product : defaultProducts) {
            db.collection("Categories")
                    .document(product.getcategoryName())
                    .collection("Products")
                    .document(product.getProductName())
                    .set(product)
                    .addOnSuccessListener(aVoid ->
                            Toast.makeText(this, product.getProductName() + " added.", Toast.LENGTH_SHORT).show())
                    .addOnFailureListener(e ->
                            Toast.makeText(this, "Failed to add " + product.getProductName() + ": " + e.getMessage(), Toast.LENGTH_SHORT).show());
        }
    }


}