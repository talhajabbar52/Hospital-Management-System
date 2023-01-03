package com.example.hms;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;


public class AppointmentFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "AppointmentFragment";
    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private RadioButton male,female;

    private EditText PName,PAge;
    private Spinner DSpecialist;
    private  TextView dName;
    Button bookApp;
    ValueEventListener listener;
    private ArrayList<String> DocName,specialityList;
    private ArrayAdapter<String> adapter;
    private DatabaseReference reference;
    FirebaseDatabase rootNode;
     DatabaseReference myRef;

    private String uGender;




    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_appointments, container, false);


        PName = v.findViewById(R.id.P_name);
        PAge = v.findViewById(R.id.P_age);
        DSpecialist = v.findViewById(R.id.spin);
        dName = v.findViewById(R.id.doc_name);
        bookApp = v.findViewById(R.id.book);
        male = v.findViewById(R.id.male);
        female = v.findViewById(R.id.female);
        reference = FirebaseDatabase.getInstance().getReference("Doctor Info");
        rootNode= FirebaseDatabase.getInstance();

        myRef =rootNode.getReference("Appointment");
        specialityList = new ArrayList<>();
        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item,specialityList);
        DSpecialist.setAdapter(adapter);


        fetchData();

        mDisplayDate = v.findViewById(R.id.date_picker);
        mDisplayDate.setOnClickListener(this);
        bookApp.setOnClickListener(this);

        final RadioGroup[] gender = {(RadioGroup) v.findViewById(R.id.P_gender)};
        gender[0].setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.male:
                        female.setChecked(false);
                        uGender = "Male";
                        break;
                    case R.id.female:
                        male.setChecked(false);
                        uGender="Female";

                }
            }
        });

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

    @SuppressLint("NonConstantResourceId")
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


        DatePickerDialog Dialog = new DatePickerDialog(getActivity(), android.R.style.Theme_Holo_Dialog,mDateSetListener,year,month,day);
        Dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Dialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        Dialog.show();

    }

    private void fetchData()
    {

        DocName = new ArrayList<>();

        listener = reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot items : snapshot.getChildren()){
                    specialityList.add(String.valueOf(items.child("Specialist").getValue()));
                    DocName.add(String.valueOf(items.child("name").getValue()));
                    adapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DSpecialist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String Selected = specialityList.get(position);
                Log.e("Clicked:","" + Selected);
                String doctor_name = DocName.get(position);
                dName.setText(doctor_name);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private void SaveDetails() {


        String name,age,gender,date,specialist,docName ;

        name = PName.getText().toString().trim();
        age = PAge.getText().toString().trim();
        gender = uGender.trim();
        docName = dName.getText().toString().trim();
        specialist = DSpecialist.getSelectedItem().toString().trim();
        date = mDisplayDate.getText().toString().trim();

        Appointment appointment = new Appointment(name,age,gender,docName,specialist,date);

        if (name.isEmpty()){
            PName.setError("Enter Patient Name");
        }
        else if (age.isEmpty()) {
            PAge.setError("Enter Patient Age");
        }
        else {
            myRef.child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()).child(String.valueOf(System.currentTimeMillis())).setValue(appointment).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(getActivity(), "Appointment Booked", Toast.LENGTH_SHORT).show();
                    }
                    else    {
                        Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
