package com.example.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    StudentDatabaseHelper dbHelper;
    ListView listView;
    ArrayList<ListViewContent> arrayList;

    ImageButton buttonAddStudent, buttonDeleteStudent;
    ListViewCustomAdapter adapter;
    List<StudentInformation> studentList;
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

        buttonDeleteStudent = findViewById(R.id.buttonClearData);
        buttonAddStudent = findViewById(R.id.buttonMainAddStudent);
        listView = findViewById(R.id.listMain);
        arrayList = new ArrayList<>();
        dbHelper = new StudentDatabaseHelper(this);
        arrayList.clear();
        studentList = dbHelper.getAllStudents();
        for (StudentInformation student : studentList) {
            arrayList.add(new ListViewContent(
                    student.getMssv(),
                    student.getName(),
                    student.getPersonalImage()
            ));
        }
        adapter = new ListViewCustomAdapter(this, arrayList);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        buttonAddStudent.setOnClickListener(v -> addStudentToList());
        buttonDeleteStudent.setOnClickListener(v -> deleteStudentList());
        listView.setOnItemClickListener((parent, view, position, id) -> {
            ListViewContent selectedStudent = arrayList.get(position);
            Intent intent = new Intent(MainActivity.this, DisplayStudentInformation.class);
            intent.putExtra("mssv", selectedStudent.getMssv());
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        arrayList.clear();
        List<StudentInformation> studentList = dbHelper.getAllStudents();
        for (StudentInformation student : studentList) {
            arrayList.add(new ListViewContent(
                    student.getMssv(),
                    student.getName(),
                    student.getPersonalImage()
            ));
        }
        adapter.notifyDataSetChanged();
    }




    void deleteStudentList() {
        dbHelper.deleteAllStudents();
        arrayList.clear();
        adapter.notifyDataSetChanged();
        Toast.makeText(getApplicationContext(), "Clear Database successfully!", Toast.LENGTH_LONG).show();
    }

    void addStudentToList () {
        Intent intent = new Intent(MainActivity.this, AddStudent.class);
        MainActivity.this.startActivity(
                intent
        );
    }

}