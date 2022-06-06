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
import com.uit.TripTicketSaler.Model.User;

public class SignUp extends AppCompatActivity {

    private TextView email;
    private TextView password;
    private TextView retypepassword;
    private Button next;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        email = findViewById(R.id.email1);
        password = findViewById(R.id.password1);
        retypepassword = findViewById(R.id.retypepassword);
        next = findViewById(R.id.signupbtn);

        mAuth = FirebaseAuth.getInstance();

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              check();
            }
        });
    }
    private void check() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
            Toast.makeText(SignUp.this, "Invalid email format", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(email.getText().toString())) {
            Toast.makeText(SignUp.this, "Enter email", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(password.getText().toString())) {
            Toast.makeText(SignUp.this, "Enter password", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(retypepassword.getText().toString())) {
            Toast.makeText(SignUp.this, "Enter retype password ", Toast.LENGTH_SHORT).show();
        } else if (password.getText().toString().length() < 6) {
            Toast.makeText(SignUp.this, "Passwoed must atleast 6 characters long", Toast.LENGTH_SHORT).show();
        } else if (password.getText().toString().equals(retypepassword.getText().toString()) == false) {
            Toast.makeText(SignUp.this, "Retype wrong password", Toast.LENGTH_SHORT).show();
        } else {
            signup();
        }
    }

    private void signup() {
        mAuth.createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(SignUp.this, "Create Account Success", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SignUp.this,UserInfo.class));
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(SignUp.this, "Username:" +user.getEmail(), Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(SignUp.this, "This email already exists", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

}