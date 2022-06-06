package com.uit.TripTicketSaler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ForgotPassword extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private Button finish1;
    private TextView email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        finish1 = findViewById(R.id.finish1);
        email= findViewById(R.id.email2);
        mAuth = FirebaseAuth.getInstance();

        finish1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check();
            }
        });

    }
    private void check() {
        if (TextUtils.isEmpty(email.getText().toString())) {
            Toast.makeText(ForgotPassword.this, "Enter Invalid email format", Toast.LENGTH_SHORT).show();
        }else {
            fogotpassword();
        }
    }
    private void fogotpassword() {
        mAuth.sendPasswordResetEmail(email.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ForgotPassword.this, "Check Email", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(ForgotPassword.this,LogIn.class));
                        }else {
                            Toast.makeText(ForgotPassword.this, "This email does not exists", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}