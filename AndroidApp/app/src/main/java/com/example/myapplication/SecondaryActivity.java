package com.example.myapplication;

import android.os.Bundle;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class SecondaryActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<ListViewContentPractice2> fruitList;
    ListViewCustomAdapterPractice2 fruitAdapter;
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
        listView = findViewById(R.id.listView2);
        fruitList = new ArrayList<>();

        fruitList.add(new ListViewContentPractice2(R.drawable.apple, "Apple", "Calories: 52 kcal"));
        fruitList.add(new ListViewContentPractice2(R.drawable.banana, "Banana", "Calories: 89 kcal"));
        fruitList.add(new ListViewContentPractice2(R.drawable.orange, "Orange", "Calories: 47 kcal"));
        fruitList.add(new ListViewContentPractice2(R.drawable.strawberry, "Strawberry", "Calories: 33 kcal"));
        fruitList.add(new ListViewContentPractice2(R.drawable.grape, "Grape", "Calories: 67 kcal"));
        fruitList.add(new ListViewContentPractice2(R.drawable.mango, "Mango", "Calories: 60 kcal"));
        fruitList.add(new ListViewContentPractice2(R.drawable.pineapple, "Pineapple", "Calories: 50 kcal"));
        fruitList.add(new ListViewContentPractice2(R.drawable.cherry, "Cherry", "Calories: 50 kcal"));
        fruitList.add(new ListViewContentPractice2(R.drawable.watermelon, "Watermelon", "Calories: 30 kcal"));
        fruitList.add(new ListViewContentPractice2(R.drawable.kiwi, "Kiwi", "Calories: 41 kcal"));
        fruitList.add(new ListViewContentPractice2(R.drawable.peach, "Peach", "Calories: 39 kcal"));
        fruitList.add(new ListViewContentPractice2(R.drawable.blueberry, "Blueberry", "Calories: 57 kcal"));



        fruitAdapter = new ListViewCustomAdapterPractice2(this, fruitList);
        listView.setAdapter(fruitAdapter);
    }
}