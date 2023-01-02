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


    private String uid;
    private FirebaseUser user;
    private FirebaseDatabase firebaseDatabase;


    private DatabaseReference reference;
    private ListView listView;

    private ArrayList<String> arrayList;
    private ArrayAdapter<String> arrayAdapter;
    private int available_user = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_reports, container, false);


        listView = v.findViewById(R.id.ad1);

        showData();

        return v;
    }

    private void showData() {

        user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();

        reference = FirebaseDatabase.getInstance().getReference("Appointment").child(uid);

        arrayList = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(arrayAdapter);

        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//                for (DataSnapshot ds: snapshot.getChildren()){
                    arrayList.add(snapshot.getValue(Appointment.class).toString());
                    arrayAdapter.notifyDataSetChanged();
//                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}




