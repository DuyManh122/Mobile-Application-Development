package com.example.myapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<ListViewContent> arrayList;

    ImageButton buttonAddStudent;
    ListViewCustomAdapter adapter;
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

        buttonAddStudent = findViewById(R.id.buttonMainAddStudent);
        listView = findViewById(R.id.listMain);
        arrayList = new ArrayList<>();
        adapter = new ListViewCustomAdapter(this, arrayList);
        listView.setAdapter(adapter);


        buttonAddStudent.setOnClickListener(v -> addStudentToList());
    }


    Bundle createStudentInformationBundle(String bundlename) {
        Bundle bundle = new Bundle();
        bundle.
    }
    void addStudentToList () {

    }
}