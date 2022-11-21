package com.example.hms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
EditText edt_Username,edt_pass;
Button btn_user,btn_admin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edt_Username = findViewById(R.id.edt_txt_user);
        edt_pass = findViewById(R.id.edt_txt_user_pass);
        btn_user = findViewById(R.id.user_btn);
        btn_admin=findViewById(R.id.admin_btn);


        btn_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edt_Username.getText().toString().equals("Talha")&&edt_pass.getText().toString().equals("abc"))
                {
                    Toast.makeText(MainActivity.this, "Login Successfully", Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(MainActivity.this,UserPanel.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_LONG).show();
                    edt_Username.setError("Username Incorrect");
                    edt_pass.setError("password Incorrect");
                }
            }
        });




    }
}