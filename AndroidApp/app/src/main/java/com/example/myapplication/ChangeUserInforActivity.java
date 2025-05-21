package com.example.myapplication;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;

public class ChangeUserInforActivity extends AppCompatActivity {
    private DatePickerDialog datePickerDialog;
    private Button dateButton;
    private Button genderButton;
    private EditText nameEditText, addressEditText, phoneEditText;
    private ImageButton backButton;

    private UserInformation userInfor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_change_user_infor);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_change_user_infor), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initDatePicker();

        nameEditText = findViewById(R.id.nameEditText);
        genderButton = findViewById(R.id.GenderPickerButton);
        dateButton = findViewById(R.id.datePickerButton);
        addressEditText = findViewById(R.id.addressEditText);
        phoneEditText = findViewById(R.id.phoneEditText);
        backButton = findViewById(R.id.backAccountFragmentButton);

        setFilter();

        userInfor = (UserInformation) getIntent().getSerializableExtra("user_info");
        if (userInfor != null) {
            nameEditText.setText(userInfor.getName());
            genderButton.setText(userInfor.getSex().toString());
            dateButton.setText(userInfor.getDate_of_birth());
            addressEditText.setText(userInfor.getAddress());
            phoneEditText.setText(userInfor.getPhone_number());
        } else {
            userInfor = new UserInformation();
        }

        genderButton.setOnClickListener(v -> chooseGender());
        dateButton.setOnClickListener(v -> openDatePicker(v));

        backButton.setOnClickListener(v -> {
            hideKeyboard(v);
            if (isInputValid()) {
                userInfor.setName(nameEditText.getText().toString());
                userInfor.setSex(Gender.valueOf(getGender(genderButton.getText().toString()).toString()));
                userInfor.setDate_of_birth(dateButton.getText().toString());
                userInfor.setAddress(addressEditText.getText().toString());
                userInfor.setPhone_number(phoneEditText.getText().toString());

                Intent resultIntent = new Intent();
                resultIntent.putExtra("updated_user_info", userInfor);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
    }

    private void setFilter() {
        nameEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(30)});
        phoneEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});
        addressEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(30)});
    }

    private void chooseGender() {
        Gender[] genderOption = {Gender.Male, Gender.Female, Gender.Other};
        String[] genderNames = new String[genderOption.length];
        for (int i = 0; i < genderOption.length; i++) {
            genderNames[i] = genderOption[i].toString();
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Gender")
                .setItems(genderNames, (dialog, which) -> genderButton.setText(genderOption[which].toString()))
                .show();
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = (datePicker, year, month, day) -> {
            month++;
            String date = makeDateString(day, month, year);
            dateButton.setText(date);
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR), month = cal.get(Calendar.MONTH), day = cal.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(this, AlertDialog.THEME_HOLO_LIGHT, dateSetListener, year, month, day);
    }

    public void openDatePicker(View view) {
        datePickerDialog.show();
    }

    private String makeDateString(int day, int month, int year) {
        return getMonthFormat(month) + " " + day + " " + year;
    }

    private String getMonthFormat(int month) {
        String[] months = {"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};
        return months[month - 1];
    }

    private Gender getGender(String gender) {
        switch (gender.toLowerCase()) {
            case "male": return Gender.Male;
            case "female": return Gender.Female;
            default: return Gender.Other;
        }
    }

    private boolean isInputValid() {
        return !nameEditText.getText().toString().isEmpty()
                && !genderButton.getText().toString().isEmpty()
                && !addressEditText.getText().toString().isEmpty()
                && !phoneEditText.getText().toString().isEmpty()
                && !dateButton.getText().toString().isEmpty();
    }

    private void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}