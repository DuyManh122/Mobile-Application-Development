package com.example.myapplication;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<ListViewContentPractice1> arrayList;
    ListViewCustomAdapterPractice1 adapter;
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

        listView = findViewById(R.id.listView);
        arrayList = new ArrayList<>();

        arrayList.add(new ListViewContentPractice1(R.drawable.ic_cupcake, "Android Cupcake", "Version 1.5"));
        arrayList.add(new ListViewContentPractice1(R.drawable.ic_donut, "Android Donut", "Version 1.6"));
        arrayList.add(new ListViewContentPractice1(R.drawable.ic_eclair, "Android Eclair", "Version 2.0 - 2.1"));
        arrayList.add(new ListViewContentPractice1(R.drawable.ic_froyo, "Android Froyo", "Version 2.2"));
        arrayList.add(new ListViewContentPractice1(R.drawable.ic_gingerbread, "Android Gingerbread", "Version 2.3"));
        arrayList.add(new ListViewContentPractice1(R.drawable.ic_honeycomb, "Android Honeycomb", "Version 3.0"));
        arrayList.add(new ListViewContentPractice1(R.drawable.ic_icecream, "Android Ice Cream Sandwich", "Version 4.0"));
        arrayList.add(new ListViewContentPractice1(R.drawable.ic_jellybean, "Android Jelly Bean", "Version 4.1 – 4.3"));
        arrayList.add(new ListViewContentPractice1(R.drawable.ic_marshmallow, "Android Marshmallow", "Version 6.0"));

        adapter = new ListViewCustomAdapterPractice1(this, arrayList);
        listView.setAdapter(adapter);
    }
}