package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    CheckBox checkBoxNam1, checkBoxNam2, checkBoxNam3, checkBoxNam4, checkedYearBox;
    CheckBox checkBoxDT,  checkBoxVT, checkBoxMT, checkedSpecializationBox;
    EditText HoVaTen, SDT, Lop, MSSV, PTBT;
    StudentInformation studentInformation = new StudentInformation();
    Button buttonTruyen;

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

        checkBoxNam1 = findViewById(R.id.checkBoxNam1);
        checkBoxNam2 = findViewById(R.id.checkBoxNam2);
        checkBoxNam3 = findViewById(R.id.checkBoxNam3);
        checkBoxNam4 = findViewById(R.id.checkBoxNam4);
        checkBoxDT = findViewById(R.id.checkBoxDT);
        checkBoxVT = findViewById(R.id.checkBoxVT);
        checkBoxMT = findViewById(R.id.checkBoxMT);
        HoVaTen    = findViewById(R.id.HoVaTen);
        SDT        = findViewById(R.id.SDT);
        Lop        = findViewById(R.id.Lop);
        MSSV       = findViewById(R.id.MSSV);
        PTBT       = findViewById(R.id.EditTextPTBT);
        buttonTruyen = findViewById(R.id.ButtonTruyen);

        checkBoxNam1.setOnClickListener(v -> CheckYearOfTheStudent(checkBoxNam1));
        checkBoxNam2.setOnClickListener(v -> CheckYearOfTheStudent(checkBoxNam2));
        checkBoxNam3.setOnClickListener(v -> CheckYearOfTheStudent(checkBoxNam3));
        checkBoxNam4.setOnClickListener(v -> CheckYearOfTheStudent(checkBoxNam4));
        checkBoxDT.setOnClickListener(v -> CheckSpecialization(checkBoxDT));
        checkBoxVT.setOnClickListener(v -> CheckSpecialization(checkBoxVT));
        checkBoxMT.setOnClickListener(v -> CheckSpecialization(checkBoxMT));

        buttonTruyen.setOnClickListener(v -> sentStudentInformation());
    }

    private void sentStudentInformation() {
        try {
            studentInformation.setSdt(SDT.getText().toString());
            studentInformation.setName(HoVaTen.getText().toString());
            studentInformation.setLop(Lop.getText().toString());
            studentInformation.setMssv(MSSV.getText().toString());
            studentInformation.setChuyenNganh(checkedSpecializationBox.getText().toString());
            studentInformation.setNamHoc(checkedYearBox.getText().toString());
            studentInformation.setNguyenVong(PTBT.getText().toString());
            String text = studentInformation.toString();
            Bundle bundle = new Bundle();
            bundle.putString("student_text", text);
            Intent intent = new Intent(MainActivity.this, SecondaryActivity.class);
            intent.putExtra("student_data", bundle);
            MainActivity.this.startActivityForResult(intent, 99);
            Toast.makeText(getApplicationContext(), "Gửi thông tin thành công!", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_LONG).show();
        }

    }
    private void CheckYearOfTheStudent(CheckBox checkedBox) {
        checkBoxNam1.setChecked(false);
        checkBoxNam2.setChecked(false);
        checkBoxNam3.setChecked(false);
        checkBoxNam4.setChecked(false);
        checkedYearBox = checkedBox;
        checkedBox.setChecked(true);
    }

    private void CheckSpecialization(CheckBox checkedBox) {
        checkBoxDT.setChecked(false);
        checkBoxVT.setChecked(false);
        checkBoxMT.setChecked(false);
        checkedSpecializationBox = checkedBox;
        checkedBox.setChecked(true);
    }
}