package com.example.hms;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
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
import com.google.firebase.database.ValueEventListener;


public class AddDoctorsFragment extends Fragment implements View.OnClickListener {
    EditText name, email, phone, age;
    RadioButton male, female;
    Spinner Specialist;
    String uGender;
    Button btnSave;
    String DocSpecialist;
    private int availableUser = 0;
    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_doctors, container, false);

        name = v.findViewById(R.id.txtDocName);
        email = v.findViewById(R.id.DocEmail);
        phone = v.findViewById(R.id.DocPhone);
        age = v.findViewById(R.id.DocAge);
        male = v.findViewById(R.id.male);
        female = v.findViewById(R.id.female);
        Specialist = v.findViewById(R.id.DocSpecialist);
        btnSave = v.findViewById(R.id.btnAddDco);

        RadioGroup gender= v.findViewById(R.id.docGen);

        gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
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

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.Specialist, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        btnSave.setOnClickListener(this);
        Specialist.setAdapter(adapter);
        Specialist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                DocSpecialist = parent.getItemAtPosition(position).toString();
                DocSpecialist = Specialist.getSelectedItem().toString().trim();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild("Doctor Info")) {

                    availableUser = (int) snapshot.child("Doctor Info").getChildrenCount();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return v;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnAddDco) {
            saveDetails();
        }
    }

    private void saveDetails() {
        String Name, Email, Gender, specialist, Age, Phone;
        Name = name.getText().toString().trim();
        Email = email.getText().toString().trim();
        Phone = phone.getText().toString().trim();
        Age = age.getText().toString().trim();
        Gender = uGender.trim();
        specialist = Specialist.getSelectedItem().toString().trim();
        Integer Ages=Integer.parseInt(Age);

        if (Name.isEmpty()) {
            name.setError("Provide Name");
        } else if (Email.isEmpty()) {
            email.setError("Provide Email");
        } else if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            email.setError("Enter valid Email");

        } else if (Phone.isEmpty()) {
            phone.setError("Provide Email");

        } else if (Age.isEmpty()) {
            age.setError("Provide Age");
        }

        else if (Ages<=25){
            age.setError("invalid Age");

        }

        else {

            AddDoctors addDoc = new AddDoctors(Name, Email, Phone, Age, Gender, specialist);

            myRef.child("Doctor Info").child(String.valueOf(availableUser+1)).setValue(addDoc).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(getActivity(), "Data Inserted ", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}


