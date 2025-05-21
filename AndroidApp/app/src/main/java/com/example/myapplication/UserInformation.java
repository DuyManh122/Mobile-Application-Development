package com.example.myapplication;

import java.io.Serializable;

public class UserInformation implements Serializable {
    private  String name;
    private  String date_of_birth;
    private  String address;
    private  String phone_number;
    private  Gender sex;

    public UserInformation() {
    }

    public UserInformation(String name, String date_of_birth, Gender sex, String address, String phone_number) {
        this.name = name;
        this.date_of_birth = date_of_birth;
        this.address = address;
        this.phone_number = phone_number;
        this.sex = sex;
    }
    public String getAddress() {
        return address;
    }

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public String getName() {
        return name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public Gender getSex() {
        return sex;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setDate_of_birth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public void setSex(Gender sex) {
        this.sex = sex;
    }

    public String toString() {
        return "UserInformation{" +
                "name='" + name + '\'' +
                ", date_of_birth='" + date_of_birth + '\'' +
                ", address='" + address + '\'' +
                ", phone_number='" + phone_number + '\'';
    }
}