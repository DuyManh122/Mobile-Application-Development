package com.example.myapplication;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity {
    CheckBox checkBoxNam1, checkBoxNam2, checkBoxNam3, checkBoxNam4, checkedYearBox;
    CheckBox checkBoxDT,  checkBoxVT, checkBoxMT, checkedSpecializationBox;
    EditText HoVaTen, SDT, Lop, MSSV, PTBT;
    StudentInformation studentInformation = new StudentInformation();
    Button buttonTruyen, buttonCall, buttonSMS, buttonImage;

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
        buttonCall   = findViewById(R.id.ButtonCall);
        buttonSMS    = findViewById(R.id.ButtonSMS);
        buttonImage  = findViewById(R.id.ButtonImage);

        checkBoxNam1.setOnClickListener(v -> CheckYearOfTheStudent(checkBoxNam1));
        checkBoxNam2.setOnClickListener(v -> CheckYearOfTheStudent(checkBoxNam2));
        checkBoxNam3.setOnClickListener(v -> CheckYearOfTheStudent(checkBoxNam3));
        checkBoxNam4.setOnClickListener(v -> CheckYearOfTheStudent(checkBoxNam4));
        checkBoxDT.setOnClickListener(v -> CheckSpecialization(checkBoxDT));
        checkBoxVT.setOnClickListener(v -> CheckSpecialization(checkBoxVT));
        checkBoxMT.setOnClickListener(v -> CheckSpecialization(checkBoxMT));
        buttonCall.setOnClickListener(v -> callStudent());
        buttonSMS.setOnClickListener(v -> sentSMS());
        buttonTruyen.setOnClickListener(v -> sentStudentInformation());
        buttonImage.setOnClickListener(v -> capturePicture());

        if (savedInstanceState != null) {
            restoreState(savedInstanceState);
        }
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("HoVaTen", HoVaTen.getText().toString());
        outState.putString("SDT", SDT.getText().toString());
        outState.putString("Lop", Lop.getText().toString());
        outState.putString("MSSV", MSSV.getText().toString());
        outState.putString("PTBT", PTBT.getText().toString());

        outState.putBoolean("checkBoxNam1", checkBoxNam1.isChecked());
        outState.putBoolean("checkBoxNam2", checkBoxNam2.isChecked());
        outState.putBoolean("checkBoxNam3", checkBoxNam3.isChecked());
        outState.putBoolean("checkBoxNam4", checkBoxNam4.isChecked());
        outState.putBoolean("checkBoxDT", checkBoxDT.isChecked());
        outState.putBoolean("checkBoxVT", checkBoxVT.isChecked());
        outState.putBoolean("checkBoxMT", checkBoxMT.isChecked());

        if (studentInformation != null) {
            Bitmap personalImage = studentInformation.getPersonalImage();
            if (personalImage != null) {
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                personalImage.compress(Bitmap.CompressFormat.JPEG, 70, stream);
                outState.putByteArray("personalImage", stream.toByteArray());
            }
        }
    }

    private void restoreState(Bundle savedInstanceState) {
        HoVaTen.setText(savedInstanceState.getString("HoVaTen"));
        SDT.setText(savedInstanceState.getString("SDT"));
        Lop.setText(savedInstanceState.getString("Lop"));
        MSSV.setText(savedInstanceState.getString("MSSV"));
        PTBT.setText(savedInstanceState.getString("PTBT"));

        checkBoxNam1.setChecked(savedInstanceState.getBoolean("checkBoxNam1"));
        checkBoxNam2.setChecked(savedInstanceState.getBoolean("checkBoxNam2"));
        checkBoxNam3.setChecked(savedInstanceState.getBoolean("checkBoxNam3"));
        checkBoxNam4.setChecked(savedInstanceState.getBoolean("checkBoxNam4"));
        checkBoxDT.setChecked(savedInstanceState.getBoolean("checkBoxDT"));
        checkBoxVT.setChecked(savedInstanceState.getBoolean("checkBoxVT"));
        checkBoxMT.setChecked(savedInstanceState.getBoolean("checkBoxMT"));

        if (savedInstanceState.containsKey("personalImage")) {
            byte[] byteArray = savedInstanceState.getByteArray("personalImage");
            Bitmap personalImage = null;
            if (byteArray != null) {
                personalImage = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                studentInformation.setPersonalImage(personalImage);
            } else {
                Log.e("Error", "Null bitmap");
            }
        }
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

    private void callStudent() {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+ SDT.getText().toString()));
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new
                    String[]{android.Manifest.permission.CALL_PHONE},11);
            return;
        }
        startActivity(intent);
    }

    private void sentSMS() {
        try {
            getStudentInformation();
            Intent smsIntent=new Intent(Intent.ACTION_SENDTO,Uri.parse("smsto:"+ SDT.getText().toString()));
            //Sent message
            String message = studentInformation.toString();
            smsIntent.putExtra("sms_body", message);
            startActivity(smsIntent);
        }   catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_LONG).show();
        }
    }

    private void sentStudentInformation() {
        try {
            getStudentInformation();
            String text = studentInformation.toString();
            Bundle bundle = new Bundle();
            bundle.putString("student_text", text);
            Intent intent = new Intent(MainActivity.this, SecondaryActivity.class);

            Bitmap personalImage = studentInformation.getPersonalImage();
            if (personalImage != null) {
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                if (personalImage.compress(Bitmap.CompressFormat.JPEG, 70, stream)) {
                    bundle.putByteArray("picture_bytes", stream.toByteArray());
                } else {
                    Log.w("Bitmap", "Failed to compress bitmap");
                }
            } else {
                Log.e("Bitmap_MainActivity", "Failed to get bitmap");
            }

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

}