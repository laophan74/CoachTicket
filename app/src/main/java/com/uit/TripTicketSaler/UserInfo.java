package com.uit.TripTicketSaler;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;

import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.uit.TripTicketSaler.Model.User;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class UserInfo extends AppCompatActivity {
    private ImageButton datePicker;
    private TextView address, fullname, phone;
    private TextView dob;
    private Button finish;
    boolean valid = true;
    private int y;
    private int m;
    private int d;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        datePicker = findViewById(R.id.datePickerActions1);
        dob = findViewById(R.id.dob1);
        phone = findViewById(R.id.phone1);
        address = findViewById(R.id.address1);
        fullname = findViewById(R.id.fullname1);
        finish = findViewById(R.id.finish);

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    check();
            }
        });


        Calendar cal = Calendar.getInstance();
        y = cal.get(Calendar.YEAR);
        m = cal.get(Calendar.MONTH);
        d = cal.get(Calendar.DAY_OF_MONTH);


        datePicker.setOnClickListener(view -> {
            DatePickerDialog pickerDialog = new DatePickerDialog(UserInfo.this,
                    (datePicker, i, i1, i2) -> {
                        dob.setText(i2 + "/" + (i1 + 1) + "/" + i);
                        y = i;
                        m = i1;
                        d = i2;
                    }, y, m, d);
            pickerDialog.getDatePicker().setMinDate(cal.getTimeInMillis());
            pickerDialog.show();
            dob.setText(y + "/" + (m + 1) + "/" + d);
        });
    }
    private void check() {
        if (TextUtils.isEmpty(fullname.getText().toString())) {
            Toast.makeText(UserInfo.this, "Enter Fullname", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(dob.getText().toString())) {
            Toast.makeText(UserInfo.this, "Enter DayOfBirth", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(address.getText().toString())) {
            Toast.makeText(UserInfo.this, "Enter Address", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(phone.getText().toString())) {
            Toast.makeText(UserInfo.this, "Enter Phone", Toast.LENGTH_SHORT).show();
        } else if (phone.getText().toString().length() != 10) {
            Toast.makeText(UserInfo.this, "Phone must 10 characters long", Toast.LENGTH_SHORT).show();
        } else if (!Patterns.PHONE.matcher(phone.getText().toString()).matches()  ) {
            Toast.makeText(UserInfo.this, "Invalid Phone format", Toast.LENGTH_SHORT).show();
        } else {
            userinfo();
        }
    }

    private void userinfo() {
        FirebaseUser user = mAuth.getCurrentUser();
        Toast.makeText(UserInfo.this, "Welcome", Toast.LENGTH_SHORT).show();
        DocumentReference df = db.collection("User").document(user.getUid());
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("UserEmail", user.getEmail().toString());
        userInfo.put("Fullname", fullname.getText().toString());
        userInfo.put("DOB", dob.getText().toString());
        userInfo.put("Address", address.getText().toString());
        userInfo.put("Phone", phone.getText().toString());
        df.set(userInfo);
        startActivity(new Intent(UserInfo.this, MainActivity.class));
    }

}