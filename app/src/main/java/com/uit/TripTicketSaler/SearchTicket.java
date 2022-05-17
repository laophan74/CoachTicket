package com.uit.TripTicketSaler;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

public class SearchTicket extends Fragment {

    private Spinner citySpn;
    private Spinner citySpn1;
    private ImageButton datePicker;
    private TextView numPeopleTextview;
    private TextView numChildrenTextview;
    private TextView dateTextview;

    public static int numPeople = 0;
    public static int numChildren = 0;
    private int y;
    private int m;
    private int d;

    public SearchTicket() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search_ticket, container, false);

        citySpn = v.findViewById(R.id.citySpinner1);
        citySpn1 = v.findViewById(R.id.citySpinner2);
        numPeopleTextview = v.findViewById(R.id.numPeopleTextview);
        numChildrenTextview = v.findViewById(R.id.numChildrenTextview);
        dateTextview = v.findViewById(R.id.dateTextview);
        datePicker = v.findViewById(R.id.datePickerActions);
        Button adultA = v.findViewById(R.id.adultA);
        Button adultM = v.findViewById(R.id.adultM);
        Button childA = v.findViewById(R.id.childA);
        Button childM = v.findViewById(R.id.childM);

        adultA.setOnClickListener(view -> {
            numPeople++;
            numPeopleTextview.setText(String.valueOf(numPeople));
        });
        adultM.setOnClickListener(view -> {
            if(numPeople!=0) {
                numPeople--;
                numPeopleTextview.setText(String.valueOf(numPeople));
            }
        });
        childA.setOnClickListener(view -> {
            numChildren++;
            numChildrenTextview.setText(String.valueOf(numChildren));
        });
        childM.setOnClickListener(view -> {
            if(numChildren!=0) {
                numChildren--;
                numChildrenTextview.setText(String.valueOf(numChildren));
            }
        });

        Calendar cal = Calendar.getInstance();
        y = cal.get(Calendar.YEAR);
        m = cal.get(Calendar.MONTH);
        d = cal.get(Calendar.DAY_OF_MONTH);

        datePicker.setOnClickListener(view -> {
            DatePickerDialog pickerDialog = new DatePickerDialog(getActivity(),
                    (datePicker, i, i1, i2) -> {
                        dateTextview.setText(i2 + "/" + (i1+1) + "/" + i);
                        y = i; m =i1; d=i2;
                    }, y, m, d);
            pickerDialog.show();
        });

        return v;
    }

    private void LoadSpinner(ArrayList<String> list){
        ArrayAdapter<String> aaC = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, list);
        citySpn.setAdapter(aaC);
        citySpn1.setAdapter(aaC);
    }
}