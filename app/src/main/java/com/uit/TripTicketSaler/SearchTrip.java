package com.uit.TripTicketSaler;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import java.util.List;

public class SearchTrip extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Spinner citySpn;
    private Spinner addrSpn;
    private ArrayList<Station> lStation = new ArrayList<>();
    private ArrayList<String> lCity = new ArrayList<>();
    private ArrayList<String> lAddr = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_trip);

        citySpn = findViewById(R.id.citySpinner);
        addrSpn = findViewById(R.id.addressSpinner);

        LoadDataSpinner();
    }
    private void LoadDataSpinner(){
        db.collection("Station").get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        for(QueryDocumentSnapshot doc : task.getResult()){
                            Station station = doc.toObject(Station.class);
                            lStation.add(station);
                            lCity.add(station.getCity());
                        }
                        ArrayAdapter<String> aaC = new ArrayAdapter<String>(this,
                                android.R.layout.simple_spinner_dropdown_item, lCity);
                        citySpn.setAdapter(aaC);

                       citySpn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                           @Override
                           public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                               lAddr.clear();
                               for (Station s : lStation) {
                                   if(s.getCity()==adapterView.getItemAtPosition(i)){
                                       lAddr.add(s.getAddress());
                                   }
                               }
                               ArrayAdapter<String> aaA = new ArrayAdapter<String>(SearchTrip.this, android.R.layout.simple_spinner_dropdown_item, lAddr);
                               addrSpn.setAdapter(aaA);
                           }

                           @Override
                           public void onNothingSelected(AdapterView<?> adapterView) {

                           }
                       });
                    }
                });
    }
}