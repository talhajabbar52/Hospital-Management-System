package com.example.hms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText edt_Username,edt_pass;
    Button btn_user,btn_admin;
    TextView register;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edt_Username = findViewById(R.id.edt_txt_user);
        edt_pass = findViewById(R.id.edt_txt_user_pass);
        btn_user = findViewById(R.id.user_btn);
        btn_admin = findViewById(R.id.admin_btn);
        register = (TextView) findViewById(R.id.newAccount);
        mAuth = FirebaseAuth.getInstance();

        btn_user.setOnClickListener(this);
        register.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.newAccount:
                startActivity(new Intent(this,RegisterActivity.class));
                break;
            case R.id.user_btn:
                login();
        }
    }

    private void login() {
        String user=edt_Username.getText().toString().trim();
        String pass=edt_pass.getText().toString().trim();
        if (user.isEmpty()){
            edt_Username.setError("Email is Required");
        }if (pass.isEmpty()){
            edt_pass.setError("Password is Required");
        }
        else {
            mAuth.signInWithEmailAndPassword(user,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this, UserPanelActivity.class));

                    }else {
                        Toast.makeText(MainActivity.this, "Login Failed"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}