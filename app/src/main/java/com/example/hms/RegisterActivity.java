package com.example.hms;

import
        androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;


public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    String userGender;
    private FirebaseAuth mAuth;
    private EditText name, email, pass, age, fName, address, phone;
    Button register;
    private RadioButton male, female;
    TextView banner;
    ProgressBar bar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        //Assigning object their ID's
        name = findViewById(R.id.txtUser);
        fName = findViewById(R.id.txtFName);
        email = findViewById(R.id.txtEmail);
        pass = findViewById(R.id.txtPass);
        phone = findViewById(R.id.txtPhone);
        age = findViewById(R.id.txtAge);
        address = findViewById(R.id.txtAddress);
        banner = findViewById(R.id.signIn);
        register = findViewById(R.id.Register_btn);
        male = findViewById(R.id.male);
        female = findViewById(R.id.female);
        bar=findViewById(R.id.pBar);

        //function implements

        banner.setOnClickListener(this);
        register.setOnClickListener(this);


    }

    @SuppressLint("NonConstantResourceId")
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.male:
                if (checked) {
                    female.setChecked(false);
                    userGender = "Male";

                    break;
                } else {
                    female.setError("Select Gender");
                }

            case R.id.female:
                if (checked) {
                    male.setChecked(false);
                    userGender = "female";
                    break;
                } else {
                    female.setError("Select Gender");
                }
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signIn:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.Register_btn:
                registerUser();
                break;

        }
    }

    private void registerUser() {

        String Name, FName, Email, Pass, Phone, Address, Age, uGender;
        Name = name.getText().toString().trim();
        FName = fName.getText().toString().trim();
        Email = email.getText().toString().trim();
        Age = age.getText().toString().trim();
        Pass = pass.getText().toString().trim();
        uGender = userGender;
        Address = address.getText().toString().trim();
        Phone = phone.getText().toString().trim();
        if (Name.isEmpty()) {
            name.setError("Enter Full Name");
        }

        else if (FName.isEmpty()) {
            fName.setError("Enter Full Name");
        }
        else if (Email.isEmpty()) {
            email.setError("Email required");
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            email.setError("Enter valid Email");

        }
        else if (Pass.isEmpty()) {
            pass.setError("Password Required ");
        }
        else if (pass.length() <= 8) {
            pass.setError("Password must be grater than 8 character");
        }
        else if (Address.isEmpty()) {
            address.setError("Address required");
        }
        else if (Age.isEmpty()) {
            age.setError("Age required");
        }
        else if (Phone.isEmpty()) {
            phone.setError("Phone no required");
        }
        else {
            bar.setVisibility(View.VISIBLE);
            mAuth.createUserWithEmailAndPassword(Email, Pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {


                        UserRegistration user = new UserRegistration(Name, FName, Email, Phone, Age, uGender, Address);

                        FirebaseDatabase.getInstance().getReference("User Registration").child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_LONG).show();
                                    bar.setVisibility(View.GONE);
                                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));


                                } else {
                                    Toast.makeText(RegisterActivity.this, "Registration Failed", Toast.LENGTH_LONG).show();
                                    bar.setVisibility(View.GONE);

                                }
                            }
                        });


                    } else {

                        Toast.makeText(RegisterActivity.this, "Registration Failed ", Toast.LENGTH_LONG).show();
                        bar.setVisibility(View.GONE);

                    }
                }
            });
        }


    }


}
