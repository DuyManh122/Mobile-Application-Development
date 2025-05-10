package com.example.myapplication;


import android.graphics.Bitmap;

public class StudentInformation {
    private String name;
    private String mssv;
    private String lop;
    private String sdt;
    private String namHoc;
    private String chuyenNganh;
    private String nguyenVong;

    private Bitmap personalImage;

    public StudentInformation() {
    }

    public StudentInformation(String name, String mssv, String lop,
                              String sdt, String namHoc, String chuyenNganh, String nguyenVong, Bitmap personalImage) {
        this.name = name;
        this.mssv = mssv;
        this.lop = lop;
        this.sdt = sdt;
        this.namHoc = namHoc;
        this.chuyenNganh = chuyenNganh;
        this.nguyenVong = nguyenVong;
        this.personalImage = personalImage;
    }

    public Bitmap getPersonalImage() {
        return  personalImage;
    }

    public void setPersonalImage(Bitmap personalImage) {
        this.personalImage = personalImage;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMssv() {
        return mssv;
    }

    public void setMssv(String mssv) {
        this.mssv = mssv;
    }

    public String getLop() {
        return lop;
    }

    public void setLop(String lop) {
        this.lop = lop;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getNamHoc() {
        return namHoc;
    }

    public void setNamHoc(String namHoc) {
        this.namHoc = namHoc;
    }

    public String getChuyenNganh() {
        return chuyenNganh;
    }

    public void setChuyenNganh(String chuyenNganh) {
        this.chuyenNganh = chuyenNganh;
    }

    public void setNguyenVong(String nguyenVong) {
        this.nguyenVong = nguyenVong;
    }

    public String getNguyenVong() {
        return nguyenVong;
    }

    @Override
    public String toString() {
        return  "Họ và tên: " + name + '\n' +
                "MSSV: " + mssv + '\n' +
                "Lớp: " + lop + '\n' +
                "SDT: " + sdt + '\n' +
                "Sinh viên " + namHoc + '\n' +
                "Chuyên ngành: " + chuyenNganh + '\n' +
                "Nguyện vọng: " + nguyenVong + '\n';
    }
}