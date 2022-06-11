package com.uit.TripTicketSaler;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.uit.TripTicketSaler.Model.AppUser;

public class ClientAuth extends AppCompatActivity {
    public static AppUser Client;
    public static FirebaseAuth mAuth;
    public static FirebaseUser mClient;
    private static ClientAuth instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authetication);

        mAuth = FirebaseAuth.getInstance();
    }

    public static ClientAuth Instance(){
        if(instance==null) instance = new ClientAuth();
        return instance;
    }

}
