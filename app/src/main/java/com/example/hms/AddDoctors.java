package com.example.hms;

public class AddDoctors {
   public String name,email,gender,Specialist, age ,phone;

    public AddDoctors(){

    }
    public AddDoctors(String name,String email, String phone,String age,String gender,String Specialist)
    {
        this.name=name;
        this.email=email;
        this.phone=phone;
        this.age=age;
        this.gender=gender;
        this.Specialist=Specialist;

    }

    public String toString(){
        return "\n Patient Name: " + this.name + "\n Patient Age: " + email + "\n Patient Gender: " + phone + "\n Doctor Name: " + age + "\n Speciality: " + gender + "\n Appointment Date: " + Specialist;
    }
}
