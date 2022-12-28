package com.example.hms;

public class Appointment {

   public String name,age,gender,date,specialist,docName ;

    public Appointment() {}

    public Appointment(String name, String age, String gender, String date, String specialist, String docName) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.date = date;
        this.specialist = specialist;
        this.docName = docName;
    }
}
