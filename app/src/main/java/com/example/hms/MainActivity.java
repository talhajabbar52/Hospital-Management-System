package com.example.hms;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText edt_Username, edt_pass;
    Button btn_user, btn_admin;
    TextView register;
    ProgressBar bar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        edt_Username = findViewById(R.id.edt_txt_user);
        edt_pass = findViewById(R.id.edt_txt_user_pass);
        btn_user = findViewById(R.id.user_btn);
        btn_admin = findViewById(R.id.admin_btn);
        register = findViewById(R.id.newAccount);
        bar = findViewById(R.id.pBar);
        mAuth = FirebaseAuth.getInstance();
        btn_user.setOnClickListener(this);
        register.setOnClickListener(this);
        btn_admin.setOnClickListener(this);

        ImageView see = findViewById(R.id.see);
        see.setImageResource(R.drawable.ic_hidepass);
        see.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edt_pass.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())){
                    edt_pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    see.setImageResource(R.drawable.ic_hidepass);
                }
                else{
                    edt_pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    see.setImageResource(R.drawable.ic_seepass);
                }
            }
        });

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null ) {
            // User is signed in
            Intent i = new Intent(MainActivity.this, UserPanelActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            String cancel = "Go";
            i.putExtra("Logged In", cancel);
            startActivity(i);
        } else {
            // User is signed out
            Log.d(TAG, "onAuthStateChanged:signed_out");
        }
        Intent intent = getIntent();
        intent.getExtras();

        if(intent.hasExtra("Signed Out"))
        {
            Toast.makeText(this, "Signed Out", Toast.LENGTH_SHORT).show();
        }
        if (user != null ) {
            // User is signed in
            Intent i = new Intent(MainActivity.this, AdminPanelActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            String cancel = "Go";
            i.putExtra("SignIn", cancel);
            startActivity(i);
        } else {
            // User is signed out
            Log.d(TAG, "onAuthStateChanged:signed_out");
        }
        Intent intents = getIntent();
        intents.getExtras();

        if(intents.hasExtra("Signed Out"))
        {
            Toast.makeText(this, "Signed Out", Toast.LENGTH_SHORT).show();
        }


    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.newAccount:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            case R.id.user_btn:
                login();
                break;
            case R.id.admin_btn:
                adminLogin();
                break;

        }
    }

    private void adminLogin() {




        //admin email=admintalha@hms.com
        //admin pass=admin1234
        String adminEmail = edt_Username.getText().toString().trim();
        String adminPass = edt_Username.getText().toString().trim();

        if (adminEmail.isEmpty()) {
            edt_Username.setError("Invalid Email");
            edt_Username.requestFocus();

        } else if (adminPass.isEmpty()) {
            edt_pass.setError("Incorrect Password");
            edt_pass.requestFocus();
        } else {
            bar.setVisibility(View.VISIBLE);
            mAuth.signInWithEmailAndPassword(adminEmail, adminPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        bar.setVisibility(View.GONE);
                        startActivity(new Intent(MainActivity.this, AdminPanelActivity.class));
                    } else {
                        Toast.makeText(MainActivity.this, "Login Unsuccessful" + task.getException(), Toast.LENGTH_SHORT).show();
                        bar.setVisibility(View.GONE);
                    }


                }
            });
        }
    }

    private void login() {
        String user = edt_Username.getText().toString().trim();
        String pass = edt_pass.getText().toString().trim();
        if (user.isEmpty()) {
            edt_Username.setError("Email is Required");
        }
        if (pass.isEmpty()) {
            edt_pass.setError("Password is Required");
        } else {
            bar.setVisibility(View.VISIBLE);
            mAuth.signInWithEmailAndPassword(user, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        bar.setVisibility(View.GONE);
                        Intent intent = new Intent(MainActivity.this, UserPanelActivity.class);
                       intent.putExtra("user", user);
                        startActivity(intent);

                    } else {
                        Toast.makeText(MainActivity.this, "Login Failed" + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                        bar.setVisibility(View.GONE);
                    }
                }
            });
        }
    }
}