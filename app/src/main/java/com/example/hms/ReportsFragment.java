package com.example.hms;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ReportsFragment extends Fragment{

    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private DatabaseReference reference;
    private String uid;
    private ListView p_name,p_age,p_gender, d_name,d_specialist,app_date;
    private ValueEventListener listener;
    private FirebaseUser user;


    private ArrayList<String> PName;
    private ArrayList<String> PAge;
    private ArrayList<String> PGender;
    private ArrayList<String> DName;
    private ArrayList<String> DSpecialist;
    private ArrayList<String> App_Date;
    private ArrayAdapter<String> adapter1,adapter2,adapter3,adapter4,adapter5,adapter6;
    private int availableUser = 0;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_reports, container, false);

        p_name = v.findViewById(R.id.ad1);
        p_age = v.findViewById(R.id.ad2);
        p_gender = v.findViewById(R.id.ad3);
        d_name = v.findViewById(R.id.ad4);
        d_specialist = v.findViewById(R.id.ad5);
        app_date = v.findViewById(R.id.ad6);

        showData();

        return v;
    }

    private void showData() {

        user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();
        reference = FirebaseDatabase.getInstance().getReference("Appointment");

        PName = new ArrayList<>();
        adapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,PName);
        p_name.setAdapter(adapter1);

        PAge = new ArrayList<>();
        adapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,PAge);
        p_age.setAdapter(adapter2);

        PGender = new ArrayList<>();
        adapter3 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,PGender);
        p_gender.setAdapter(adapter3);

        DName = new ArrayList<>();
        adapter4 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,DName);
        d_name.setAdapter(adapter4);

        DSpecialist = new ArrayList<>();
        adapter5 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,DSpecialist);
        d_specialist.setAdapter(adapter5);

        App_Date = new ArrayList<>();
        adapter6 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,App_Date);
        app_date.setAdapter(adapter6);

        listener = reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()){
                    PName.add(String.valueOf(snapshot.child(uid).child("name").getValue()));
                    PAge.add(String.valueOf(snapshot.child(uid).child("age").getValue()));
                    PGender.add(String.valueOf(snapshot.child(uid).child("gender").getValue()));
                    DName.add(String.valueOf(snapshot.child(uid).child("docName").getValue()));
                    DSpecialist.add(String.valueOf(snapshot.child(uid).child("specialist").getValue()));
                    App_Date.add(String.valueOf(snapshot.child(uid).child("date").getValue()));

                    adapter2.notifyDataSetChanged();
                    adapter6.notifyDataSetChanged();
                    adapter4.notifyDataSetChanged();
                    adapter3.notifyDataSetChanged();
                    adapter1.notifyDataSetChanged();
                    adapter5.notifyDataSetChanged();

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}




