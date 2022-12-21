package com.example.hms;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class AppointmentFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "AppointmentFragment";
    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    EditText PName,PAge,PGender;
    Spinner dName;
    TextView DSpecialist;
    Button bookApp;
    ValueEventListener listener;
    ArrayList<String> list;
    ArrayAdapter<String> adapter;
    DatabaseReference dbref;


    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_appointments, container, false);

        PName = v.findViewById(R.id.P_name);
        PAge = v.findViewById(R.id.P_age);
        PGender = v.findViewById(R.id.P_gender);
        dName = (Spinner) v.findViewById(R.id.spin);
        DSpecialist = v.findViewById(R.id.d_special);
        bookApp = v.findViewById(R.id.book);
        dbref = FirebaseDatabase.getInstance().getReference("Doctor Info");



        list = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item,list);
        dName.setAdapter(adapter);


        fetchData();

        mDisplayDate = (TextView) v.findViewById(R.id.date_picker);
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
            case R.id.book:
                    SaveDetails();
                    break;
        }
    }



    private void SelectDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog Dialog = new DatePickerDialog(getActivity(), android.R.style.Theme_Holo_Light,mDateSetListener,year,month,day);
        Dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Dialog.show();
    }

    private void fetchData() {
        listener = dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot docNames : snapshot.getChildren()){
                    list.add(String.valueOf(docNames.child("name").getValue()));
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void SaveDetails() {

    }
}
