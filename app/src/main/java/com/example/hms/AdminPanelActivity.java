package com.example.hms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;


public class AdminPanelActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout drawer;
    TextView adminPanel;




    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);
        setContentView(R.layout.nav_admin_header);

        Toolbar Admin_toolbar = findViewById(R.id.Admin_toolbar);
        setSupportActionBar(Admin_toolbar);

        drawer = findViewById(R.id.admin_drawer_layout);
        NavigationView navigationView = findViewById(R.id.admin_nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header= navigationView.getHeaderView(0);
        adminPanel= header.findViewById(R.id.email);
        Intent intent=getIntent();
        String emails = intent.getStringExtra("user");
        adminPanel.setText(emails);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, Admin_toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.Admin_fragment_container, new AddDoctorsFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_add_doctors);
            Intent i = getIntent();
            i.getExtras();
            if (i.hasExtra("SignIn"))
            {
                Toast.makeText(this, "Welcome", Toast.LENGTH_SHORT).show();
            }
        }

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_add_doctors:
                getSupportFragmentManager().beginTransaction().replace(R.id.Admin_fragment_container, new AddDoctorsFragment()).commit();
                break;

            case R.id.nav_appointment_check:
                getSupportFragmentManager().beginTransaction().replace(R.id.Admin_fragment_container, new AppointmentCheckFragment()).commit();
                break;
            case R.id.nav_Admin_Logout:
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(AdminPanelActivity.this, MainActivity.class);
                String out = "Signed Out";
                intent.putExtra("Signed Out", out);
                startActivity(intent);
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed(){
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }
    }


}