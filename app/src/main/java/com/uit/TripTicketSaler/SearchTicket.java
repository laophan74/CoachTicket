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
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.uit.TripTicketSaler.Model.City;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

public class SearchTicket extends Fragment {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private Spinner citySpn;
    private Spinner citySpn1;
    private ImageButton datePicker;
    private TextView numPeopleTextview;
    private TextView numChildrenTextview;
    private TextView dateTextview;

    private ArrayList<City> lCity = new ArrayList<>();
    private ArrayList<String> lDist = new ArrayList<>();
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
        ImageButton adultA = v.findViewById(R.id.adultA);
        ImageButton adultM = v.findViewById(R.id.adultM);
        ImageButton childA = v.findViewById(R.id.childA);
        ImageButton childM = v.findViewById(R.id.childM);

        LoadCities();

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

    private void LoadCities(){
        db.collection("Cities").get()
                .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot doc : task.getResult()) {
                                    City city = doc.toObject(City.class);
                                    lCity.add(city);
                                }
                                Collections.sort(lCity);
                                for(City item : lCity){
                                    lDist.add(item.getCname());
                                }
                                ArrayAdapter<String> aaC = new ArrayAdapter<String>(getActivity(),
                                        android.R.layout.simple_spinner_dropdown_item, lDist);
                                citySpn.setAdapter(aaC);
                                citySpn1.setAdapter(aaC);
                            }
                        }
                );
    }
}