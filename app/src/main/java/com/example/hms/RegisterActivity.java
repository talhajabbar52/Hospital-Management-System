package com.example.hms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    private EditText name,email,pass,age,fName,address,phone,gender;
    private Button register;
    private RadioButton male,female;
    private TextView banner;
    String userGender="";
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        //Assigning objet their ID's
        name = (EditText) findViewById(R.id.txtUser);
        fName = (EditText) findViewById(R.id.txtFName);
        email = (EditText) findViewById(R.id.txtEmail);
        pass = (EditText) findViewById(R.id.txtPass);
        phone = (EditText) findViewById(R.id.txtPhone);
        age = (EditText) findViewById(R.id.txtAge);
        address = (EditText) findViewById(R.id.txtAddress);
        banner = (TextView) findViewById(R.id.signIn);
        register = (Button) findViewById(R.id.Register_btn);
        gender=(EditText) findViewById(R.id.gender);
        //function implements
        
        banner.setOnClickListener(this);
        register.setOnClickListener(this);



    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.signIn:
                startActivity(new Intent(this,MainActivity.class));
                break;
            case R.id.Register_btn:
                registerUser();
                break;

        }
    }

    private void registerUser() {

        String Name,FName,Email,Pass,Phone,Address,Age, Gender;
        Name = name.getText().toString().trim();
        FName = fName.getText().toString().trim();
        Email = email.getText().toString().trim();
        Age = age.getText().toString().trim();
        Pass = pass.getText().toString().trim();
        Gender=gender.getText().toString().trim();
        Address = address.getText().toString().trim();
        Phone = phone.getText().toString().trim();


        if (Name.isEmpty())
        {
            name.setError("Enter Full Name");
            name.requestFocus();

        }
        if (FName.isEmpty())
        {
            fName.setError("Enter Full Name");
            fName.requestFocus();

        }
        if (Email.isEmpty())
        {
            email.setError("Email required");
            email.requestFocus();

        }
        if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()){
            email.setError("Enter valid Email");
            email.requestFocus();
        }

        if (Pass.isEmpty())
        {
            pass.setError("Enter Full Name");
            name.requestFocus();

        }
        if (pass.length()<=8)
        {
            pass.setError("Password must be grater than 8 character");
            pass.requestFocus();
        }

        if (Gender.isEmpty())
        {
            male.setError("Gender required");
            male.requestFocus();

        } if (Gender.isEmpty())
        {
            female.setError("Gender required");
            female.requestFocus();

        } if (Address.isEmpty())
        {
            address.setError("Address required");
            address.requestFocus();

        }
        if (Age.isEmpty())
        {
            age.setError("Age required");
            age.requestFocus();

        }

        if (Phone.isEmpty())
        {
            phone.setError("Phone no required");
            phone.requestFocus();

        }

        mAuth.createUserWithEmailAndPassword(Email,Pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){

                   UserRegistration user = new UserRegistration(Name,FName,Email,Phone,Age,Gender,Address);

                    FirebaseDatabase.getInstance().getReference("User Registration").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_LONG).show();
                                 startActivity(new Intent(RegisterActivity.this,MainActivity.class));


                            }
                            else {
                                Toast.makeText(RegisterActivity.this, "Registration Failed", Toast.LENGTH_LONG).show();

                            }
                        }
                    });

                }
                else {
                    Toast.makeText(RegisterActivity.this, "Registration Failed abc", Toast.LENGTH_LONG).show();

                }
            }
        });












    }



    }
