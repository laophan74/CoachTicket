package com.uit.TripTicketSaler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Bundle;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.uit.TripTicketSaler.Model.City;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private NavController navController;
    private ArrayList<City> lCity = new ArrayList<>();
    private ArrayList<String> lDist = new ArrayList<>();
    public Bundle bundle = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navController = Navigation.findNavController(this, R.id.navHostFrag);
        loadCities();
        bundle.putSerializable("lDist", lDist);
        navController.navigate(R.id.searchTicket, bundle);
    }

    private void loadCities(){
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
                    }
                }
            );
    }
}