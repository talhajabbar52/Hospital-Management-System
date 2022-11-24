package com.example.hms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
EditText edt_Username,edt_pass;
Button btn_user,btn_admin;
TextView register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edt_Username = findViewById(R.id.edt_txt_user);
        edt_pass = findViewById(R.id.edt_txt_user_pass);
        btn_user = findViewById(R.id.user_btn);
        btn_admin=findViewById(R.id.admin_btn);
        register = (TextView) findViewById(R.id.newAccount);


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
                startActivity(new Intent(this,UserPanelActivity.class));
        }

    }
}