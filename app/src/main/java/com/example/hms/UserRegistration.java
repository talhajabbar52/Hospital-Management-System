package com.example.hms;

public class UserRegistration {
    public String   Name,FName,Email,Male,Phone,Address,Age,Gender;

    public UserRegistration(){

    }
    public UserRegistration( String Name,String FName,String Email,String Phone,String Age,String Gender,String Address){

        this.Name = Name;
        this.FName = FName;
        this.Email = Email;
        this.Gender = Gender;
        this.Address = Address;
        this.Phone = Phone;
        this.Age = Age;

    }



}
