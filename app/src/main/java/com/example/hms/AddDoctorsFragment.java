package com.example.hms;

import android.annotation.SuppressLint;
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
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;


public class AddDoctorsFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    EditText name,email,phone,age, dcoGen;
    RadioButton male,female;
    Spinner Specialist;
    String gender;
    Button btnSave;




    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_doctors, container, false);
        name = v.findViewById(R.id.txtDocName);
        email= v.findViewById(R.id.DocEmail);
        phone = v.findViewById(R.id.DocPhone);
        age = v.findViewById(R.id.DocAge);
        male = v.findViewById(R.id.male);
        female = v.findViewById(R.id.female);
        Specialist = v.findViewById(R.id.DocSpecialist);
        btnSave=v.findViewById(R.id.btnAddDco);
        dcoGen=v.findViewById(R.id.txtDocGen);
        btnSave.setOnClickListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),R.array.Specialist, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Specialist.setAdapter(adapter);
        Specialist.setOnItemClickListener((AdapterView.OnItemClickListener) this);

        return v;
    }
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


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
                    gender = "Male";

                    break;
                } else {
                    female.setError("Select Gender");
                }

            case R.id.female:
                if (checked) {
                    male.setChecked(false);
                    gender = "female";
                    break;
                } else {
                    female.setError("Select Gender");
                }
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnAddDco:
                saveDetails();
                break;
        }
    }

    private void saveDetails() {

       String Name,Email, Gender ,specialist;
       Integer Age,Phone;

       Name=name.getText().toString().trim();
       Email=email.getText().toString().trim();
       Phone= Integer.parseInt(phone.getText().toString().trim());
       Age=Integer.parseInt(age.getText().toString().trim());
       Gender=dcoGen.getText().toString().trim();
       specialist=Specialist.toString().trim();

       if (Name.isEmpty()){
           name.setError("Provide Name");
       }
       else if(Email.isEmpty()){
           email.setError("Provide Email");
       }
       else if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
           email.setError("Enter valid Email");

       }
       else if(Phone.toString().isEmpty()){
           phone.setError("Provide Email");

       }
       else if(Age.toString().isEmpty()){
           age.setError("Provide Age");
       }
       else{
           AddDoctors addDcc=new AddDoctors(Name,Email,Phone,Age,Gender,specialist);
           FirebaseDatabase.getInstance().getReference("Doctor Info").child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()).setValue(addDcc).addOnCompleteListener(new OnCompleteListener<Void>() {
               @Override
               public void onComplete(@NonNull Task<Void> task) {
                   if (task.isSuccessful()){
                       Toast.makeText(getActivity(), "Data Inserted Successfully", Toast.LENGTH_SHORT).show();
                   }
                   else {
                       Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
                   }
               }
           });
       }



    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String specialist = parent.getItemAtPosition(position).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}