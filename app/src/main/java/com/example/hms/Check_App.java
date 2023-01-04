package com.example.hms;

public class Check_App {
    public String name,age,gender,docName,specialist,date;

    public Check_App()
    {}

    public Check_App(String name, String age, String gender, String docName, String specialist, String date){
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.docName = docName;
        this.specialist = specialist;
        this.date = date;
    }
    public String toString()
    {
        return "\n Patient Name: " + this.name + "\n Patient Age: " + age + "\n Patient Gender: " + gender + "\n Doctor Name: " + docName + "\n Speciality: " + specialist + "\n Appointment Date: " + date;
    }
}
