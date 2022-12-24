package com.example.hms;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;

public class AppointmentFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "AppointmentFragment";
    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    EditText PName,PAge,PGender;
    Spinner dName;
    TextView DSpecialist;
    Button bookApp;
    ValueEventListener listener;
    ArrayList<String> DocName,specialityList;
    ArrayAdapter<String> adapter;
    DatabaseReference reference;


    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_appointments, container, false);

        PName = v.findViewById(R.id.P_name);
        PAge = v.findViewById(R.id.P_age);
        PGender = v.findViewById(R.id.P_gender);
        dName = v.findViewById(R.id.spin);
        DSpecialist = v.findViewById(R.id.d_special);
        bookApp = v.findViewById(R.id.book);
        reference = FirebaseDatabase.getInstance().getReference("Doctor Info");

        DocName = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item,DocName);
        dName.setAdapter(adapter);

        fetchData();

        mDisplayDate = v.findViewById(R.id.date_picker);
        mDisplayDate.setOnClickListener(this);
        bookApp.setOnClickListener(this);



        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet : dd/mm/yyy " + day + "/" + month + "/" + year);

                String date =  day + "/" + month + "/" + year;
                mDisplayDate.setText(date);
            }
        };
            return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.date_picker:
                SelectDate();
                break;
        }
    }

    private void SelectDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog Dialog = new DatePickerDialog(getActivity(), android.R.style.Theme_Holo_Dialog,mDateSetListener,year,month,day);
        Dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Dialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        Dialog.show();

    }

    private void fetchData()
    {
        specialityList = new ArrayList<>();

        listener = reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot items : snapshot.getChildren()){
                    DocName.add(String.valueOf(items.child("name").getValue()));
                    specialityList.add(String.valueOf(items.child("Specialist").getValue()));
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        dName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String Selected = DocName.get(position).toString();
                Log.e("Clicked:","" + Selected);
                String Speciality = specialityList.get(position).toString();
                DSpecialist.setText(Speciality);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private void SaveDetails() {

    }
}
