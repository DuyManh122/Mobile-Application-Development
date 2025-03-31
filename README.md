# Mobile Application Development
Weekly Reports for Mobile Application Development University Course.

This repository for small group projects from the **Mobile Application Development** course at the **University of Science - HCMUS**. We need to develop a new Android application each week based on the concepts we learn during that time. It serves as a collection of our weekly Android applications, showcasing our knowledge throughout the course.

## Student name

| Name |Student ID         | Github account                        |
|------|-------------------|---------------------------------------|
| Nguyễn Đặng Duy Mạnh    |   21200312   | [DuyManh122](https://github.com/DuyManh122) |


## Lab1 Projects
***Description:** Collect Student Information Android App using Intent and Bundle*

## MainActivity

![image](https://github.com/user-attachments/assets/09a2cc8f-8c58-4717-b43b-c86b04f9e857)


### Feature:

  - EditTexts for collecting name, phone number, class, student ID, and aspirations.
  - Checkboxes for academic year (1st-4th year) and specialization (DT, VT, MT).
  - Using virtual device camera to take a student photo via ACTION_IMAGE_CAPTURE intent.
  - Direct phone call functionality (with CALL_PHONE permission handling).
  - SMS sending capability with pre-filled student information.
  - Using StudentInformation class to collect data and sent to SecondaryActivity through intent and bundle.
      +  Text information.
      +  Captured image (compressed as JPEG).
  - Saves and restores activity state (including image data) during configuration changes through savedInstanceState Bundle.

### Function:
  - ```sentStudentInformation()``` to send student's data to SecondaryActivity:

     ```
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
    ```

  - ```sentSMS()``` to send a message to student with pre-filled student information:

     ```
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
    ```

  - ```callStudent()``` to call to student via the phone number via student phone number entered

     ```
    private void callStudent() {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+ SDT.getText().toString()));
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new
                    String[]{android.Manifest.permission.CALL_PHONE},11);
            return;
        }
        startActivity(intent);
    }
    ```

  - ```capturePicture()``` and ```onActivityResult()``` to capture image and store into studentInformation objecet

     ```
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
    ```
     
     ```
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
    ```

  - Using overriden method ```onSaveInstanceState()``` and method ```restoreState()``` call at onCreate() to restore the input.

     ```
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
    ```
     
     ```
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
    ```
     
## SecondaryActivity

  ![image](https://github.com/user-attachments/assets/f41ce993-d8e8-4875-a7e8-38174ae74a1b)

### Feature:

  - TextView and ImageView to display the student information.
  - Get student information from MainActivity through intent and bundle.
  - Back to MainActivity through ```finish()```.

### Function:

  - Collect user information and display.

  ```
        intent = getIntent();
        Bundle bundle = intent.getBundleExtra("student_data");
        studentText = bundle.getString("student_text");
        student.setText(studentText);

        byte[] byteArray = bundle.getByteArray("picture_bytes");
        Bitmap personalImage = null;
        if (byteArray != null) {
            personalImage = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        } else {
            Log.e("Error", "Null bitmap");
        }

        imageView.setImageBitmap(personalImage);
        backButton.setOnClickListener(v -> {
                finish();
        });
  ```
