package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class StudentDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "student_info.db";
    public static final String TABLE_NAME = "students";

    public StudentDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                "mssv TEXT PRIMARY KEY, " +
                "name TEXT , " +
                "lop TEXT, " +
                "sdt TEXT, " +
                "namHoc TEXT, " +
                "chuyenNganh TEXT, " +
                "nguyenVong TEXT, " +
                "image BLOB)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    public void deleteAllStudents() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
        db.close();
    }
    public void insertStudent(StudentInformation student) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("name", student.getName());
        values.put("mssv", student.getMssv());
        values.put("lop", student.getLop());
        values.put("sdt", student.getSdt());
        values.put("namHoc", student.getNamHoc());
        values.put("chuyenNganh", student.getChuyenNganh());
        values.put("nguyenVong", student.getNguyenVong());
        values.put("image", bitmapToBytes(student.getPersonalImage()));

        db.insert(TABLE_NAME, null, values);
        db.close();
    }


    private byte[] bitmapToBytes(Bitmap bitmap) {
        if (bitmap == null) return null;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    public List<StudentInformation> getAllStudents() {
        List<StudentInformation> studentList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        if (cursor.moveToFirst()) {
            do {
                StudentInformation student = new StudentInformation();
                student.setMssv(cursor.getString(cursor.getColumnIndexOrThrow("mssv")));
                student.setName(cursor.getString(cursor.getColumnIndexOrThrow("name")));
                student.setLop(cursor.getString(cursor.getColumnIndexOrThrow("lop")));
                student.setSdt(cursor.getString(cursor.getColumnIndexOrThrow("sdt")));
                student.setNamHoc(cursor.getString(cursor.getColumnIndexOrThrow("namHoc")));
                student.setChuyenNganh(cursor.getString(cursor.getColumnIndexOrThrow("chuyenNganh")));
                student.setNguyenVong(cursor.getString(cursor.getColumnIndexOrThrow("nguyenVong")));

                byte[] imageBytes = cursor.getBlob(cursor.getColumnIndexOrThrow("image"));
                if (imageBytes != null) {
                    Bitmap image = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                    student.setPersonalImage(image);
                }

                studentList.add(student);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return studentList;
    }

    public StudentInformation getStudentByMssv(String mssv) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                TABLE_NAME,
                null,
                "mssv = ?",
                new String[]{mssv},
                null, null, null
        );

        StudentInformation student = null;

        if (cursor != null && cursor.moveToFirst()) {
            student = new StudentInformation();
            student.setMssv(cursor.getString(cursor.getColumnIndexOrThrow("mssv")));
            student.setName(cursor.getString(cursor.getColumnIndexOrThrow("name")));
            student.setLop(cursor.getString(cursor.getColumnIndexOrThrow("lop")));
            student.setSdt(cursor.getString(cursor.getColumnIndexOrThrow("sdt")));
            student.setNamHoc(cursor.getString(cursor.getColumnIndexOrThrow("namHoc")));
            student.setChuyenNganh(cursor.getString(cursor.getColumnIndexOrThrow("chuyenNganh")));
            student.setNguyenVong(cursor.getString(cursor.getColumnIndexOrThrow("nguyenVong")));
            byte[] imageBytes = cursor.getBlob(cursor.getColumnIndexOrThrow("image"));
            if (imageBytes != null) {
                Bitmap image = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                student.setPersonalImage(image);
            }

            cursor.close();
        }

        db.close();
        return student;
    }
}
