package com.example.hms;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class AppointmentFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "AppointmentFragment";
    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_appointments, container, false);

        mDisplayDate = (TextView) v.findViewById(R.id.date_picker);
        mDisplayDate.setOnClickListener(this);


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


        DatePickerDialog Dialog = new DatePickerDialog(getActivity(), android.R.style.Theme_Holo_Light,mDateSetListener,year,month,day);
        Dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Dialog.show();
    }
}
