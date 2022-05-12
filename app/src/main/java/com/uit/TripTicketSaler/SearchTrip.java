package com.uit.TripTicketSaler;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.uit.TripTicketSaler.Model.Station;

import java.util.ArrayList;
import java.util.Calendar;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class SearchTrip extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Spinner citySpn;
    private Spinner citySpn1;
    private ImageButton datePicker;
    private TextView numPeopleTextview;
    private TextView numChildrenTextview;
    private TextView dateTextview;
    private ArrayList<Station> lStation = new ArrayList<>();
    private ArrayList<String> lCity = new ArrayList<>();

    private DatePickerDialog datePickerDialog;

    public static int numPeople = 0;
    public static int numChildren = 0;
    private int year;
    private int month;
    private int day;
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_trip);

        citySpn = findViewById(R.id.citySpinner1);
        citySpn1 = findViewById(R.id.citySpinner2);
        numPeopleTextview = findViewById(R.id.numPeopleTextview);
        numChildrenTextview = findViewById(R.id.numChildrenTextview);
        dateTextview = findViewById(R.id.dateTextview);
        datePicker = findViewById(R.id.datePickerActions);

        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(SearchTrip.this,
                        (datePicker, year, month, day) -> {
                    dateTextview.setText(day+"/"+(month+1)+"/"+year);
                        },2022,5,1);
                datePickerDialog.show();

            }
        });

        loadDataSpinner();

    }
    private void loadDataSpinner(){
        db.collection("Station").get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot doc : task.getResult()) {
                            Station station = doc.toObject(Station.class);
                            lStation.add(station);
                            if (!lCity.contains(station.getCity())) {
                                lCity.add(station.getCity());

                            }
                            ArrayAdapter<String> aaC = new ArrayAdapter<String>(this,
                                    android.R.layout.simple_spinner_dropdown_item, lCity);
                            citySpn.setAdapter(aaC);
                            citySpn1.setAdapter(aaC);
                        }
                    }
                });
    }


    public void btnReduce(View view) {
        if(numPeople!=0) {
            numPeople--;
            numPeopleTextview.setText(String.valueOf(numPeople));
        }
    }

    public void btnIncrease(View view) {
        numPeople++;
        numPeopleTextview.setText(String.valueOf(numPeople));
    }

    public void btnChildIncrease(View view) {
        numChildren++;
        numChildrenTextview.setText(String.valueOf(numChildren));
    }

    public void btnChildReduce(View view) {
        if(numChildren!=0) {
            numChildren--;
            numChildrenTextview.setText(String.valueOf(numChildren));
        }
    }
}
