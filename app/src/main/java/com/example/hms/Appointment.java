package com.example.hms;

public class Appointment {
    public String name,age,gender,docName,specialist,date;


    public Appointment() {

    }

    public Appointment(String name, String age, String gender, String docName, String specialist, String date) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.docName = docName;
        this.specialist = specialist;
        this.date = date;
    }
}
