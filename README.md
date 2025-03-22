# Mobile Application Development
Weekly Reports for Mobile Application Development University Course.

This repository for small group projects from the **Mobile Application Development** course at the **University of Science - HCMUS**. We need to develop a new Android application each week based on the concepts we learn during that time. It serves as a collection of our weekly Android applications, showcasing our knowledge throughout the course.

## Group 5 Members

| Name |Student ID         | Github account                        |
|------|-------------------|---------------------------------------|
| Nguyễn Đặng Duy Mạnh    |   21200312   | [DuyManh122](https://github.com/DuyManh122) |
| Mai Thị Cẩm Ly          |   21200374   | [CamLy2003](https://github.com/CamLy2003)   |


## Week3 Projects
***Description:** Text Corrector Android App using Explicit Intent and Bundle*

<img width="615" alt="Untitled" src="https://github.com/user-attachments/assets/7e750069-02f9-4fe7-be32-4a4153c38aaf" />


## MainActivity

### Feature:
  - An EditText that allows the student enters text into an EditText.
  - Submit button:
	  + Pass the text inside a Bundle and sent to SecondaryActivity via a Intent.
    + Start SecondaryActivity with request code is 99.

  - Clear button:
	  + Clear both student input text and teacher result.
    + Use Toast to show a message about the deletion.
      
  - onActivityResult():
    + Listen for results from SecondaryActivity.
    + If request code is 99 and result code is 33 (true).
    + Update the teacher text from the bundle to TextView.

### Function:
  - ```studentSubmitText()``` to send student's text to SecondaryActivity:

     ```
     private void studentSubmitText() {
        String text = student_text.getText().toString();
        Bundle bundle = new Bundle();
        bundle.putString("student_text", text);
        Intent intent = new Intent(MainActivity.this, SecondaryActivity.class);
        intent.putExtra("student_data", bundle);
        MainActivity.this.startActivityForResult(intent, 99);
    }
    ```
  - ```onActivityResult()``` is automatically called when SecondaryActivity completes its task and sends data back:
    ```
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 99 && resultCode == 33) {
            Bundle result_bundle = data.getBundleExtra("teacher_data");
            String result_teacher_str = result_bundle.getString("teacher_text");
            Log.d("result", result_teacher_str);

            teacher_text.setText(result_teacher_str);
        }
    }
    ```

## SecondaryActivity

### Feature:

  - Extracts the text from the Bundle sent by MainActivity.
  - Display it in EditText to allow teacher modify student's text.
  - Change Button:
    + Get the updated text from EditText.
    + Stores the text in a Bundle, attachs it to an Intent.
    + Send back the result to MainActivity with result code is 33.
  -	Restore Button: Restore the text in EditText to its original student text.

### Function:
  - ```updateStudentChange()``` send back the result to MainActivity
    ```
    protected void updateStudentChange() {
        Bundle bundle = new Bundle();
        bundle.putString("teacher_text", student.getText().toString());
        intent.putExtra("teacher_data",bundle);
        setResult(33, intent);
        finish();
    }
    ```

	
