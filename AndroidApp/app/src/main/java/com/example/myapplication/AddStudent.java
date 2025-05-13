package com.example.myapplication;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.ByteArrayOutputStream;

public class AddStudent extends AppCompatActivity {
    CheckBox checkBoxNam1, checkBoxNam2, checkBoxNam3, checkBoxNam4, checkedYearBox;
    CheckBox checkBoxDT,  checkBoxVT, checkBoxMT, checkedSpecializationBox;
    EditText HoVaTen, SDT, Lop, MSSV, PTBT;
    StudentInformation studentInformation;
    Button buttonSave, buttonImage;

    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_student);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_add_student), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        studentInformation = new StudentInformation();

        checkBoxNam1 = findViewById(R.id.checkBoxNam1);
        checkBoxNam2 = findViewById(R.id.checkBoxNam2);
        checkBoxNam3 = findViewById(R.id.checkBoxNam3);
        checkBoxNam4 = findViewById(R.id.checkBoxNam4);
        checkBoxDT   = findViewById(R.id.checkBoxDT);
        checkBoxVT   = findViewById(R.id.checkBoxVT);
        checkBoxMT   = findViewById(R.id.checkBoxMT);
        HoVaTen      = findViewById(R.id.HoVaTen);
        SDT          = findViewById(R.id.SDT);
        Lop          = findViewById(R.id.Lop);
        MSSV         = findViewById(R.id.MSSV);
        PTBT         = findViewById(R.id.EditTextPTBT);
        buttonSave   = findViewById(R.id.ButtonAddStudentSave);
        buttonImage  = findViewById(R.id.ButtonImage);

        checkBoxNam1.setOnClickListener(v -> CheckYearOfTheStudent(checkBoxNam1));
        checkBoxNam2.setOnClickListener(v -> CheckYearOfTheStudent(checkBoxNam2));
        checkBoxNam3.setOnClickListener(v -> CheckYearOfTheStudent(checkBoxNam3));
        checkBoxNam4.setOnClickListener(v -> CheckYearOfTheStudent(checkBoxNam4));
        checkBoxDT.setOnClickListener(v -> CheckSpecialization(checkBoxDT));
        checkBoxVT.setOnClickListener(v -> CheckSpecialization(checkBoxVT));
        checkBoxMT.setOnClickListener(v -> CheckSpecialization(checkBoxMT));
        buttonSave.setOnClickListener(v -> saveStudentInformation());
        buttonImage.setOnClickListener(v -> capturePicture());

        intent = getIntent();
    }

        private void capturePicture() {
            Intent pic_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (ActivityCompat.checkSelfPermission(this,
                    android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(this,new
                        String[]{android.Manifest.permission.CAMERA}, 1);
                return;
            }
            startActivityForResult(pic_intent,22);
        }


    private void CheckYearOfTheStudent(CheckBox checkedBox) {
        checkBoxNam1.setChecked(false);
        checkBoxNam2.setChecked(false);
        checkBoxNam3.setChecked(false);
        checkBoxNam4.setChecked(false);
        checkedYearBox = checkedBox;
        checkedBox.setChecked(true);
    }

    private void getStudentInformation() {
        studentInformation.setSdt(SDT.getText().toString());
        studentInformation.setName(HoVaTen.getText().toString());
        studentInformation.setLop(Lop.getText().toString());
        studentInformation.setMssv(MSSV.getText().toString());
        studentInformation.setChuyenNganh(checkedSpecializationBox.getText().toString());
        studentInformation.setNamHoc(checkedYearBox.getText().toString());
        studentInformation.setNguyenVong(PTBT.getText().toString());
    }

    private void CheckSpecialization(CheckBox checkedBox) {
        checkBoxDT.setChecked(false);
        checkBoxVT.setChecked(false);
        checkBoxMT.setChecked(false);
        checkedSpecializationBox = checkedBox;
        checkedBox.setChecked(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 22 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            if (extras != null) {
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                if (imageBitmap != null) {
                    studentInformation.setPersonalImage(imageBitmap);
                }
            }
        }
    }


    private void saveStudentInformation() {
        try {
            getStudentInformation();
            StudentDatabaseHelper dbHelper = new StudentDatabaseHelper(getApplicationContext());
            dbHelper.insertStudent(studentInformation);
            Toast.makeText(getApplicationContext(), "Lưu thông tin thành công!", Toast.LENGTH_LONG).show();
            setResult(33, intent);
            finish();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_LONG).show();
            Log.e("Add Student Activity", "Add fail");
        }
    }
}





