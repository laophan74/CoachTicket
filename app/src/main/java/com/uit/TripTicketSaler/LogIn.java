package com.uit.TripTicketSaler;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthRecentLoginRequiredException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class LogIn extends AppCompatActivity {
    private TextView email;
    private TextView password;
    private TextView signup;
    private TextView forgotpassword;
    private Button signin;
    FirebaseAuth mAuth;
    private ImageView googlebtn;
    private ImageView facebookbtn;
    private ImageView twitterbtn;
    boolean valid = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        forgotpassword = findViewById(R.id.forgotpass);
        facebookbtn = findViewById(R.id.facebook_btn);
        twitterbtn = findViewById(R.id.twitter_btn);
        googlebtn = findViewById(R.id.google_btn);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        signup = findViewById(R.id.signup);
        signin = findViewById(R.id.loginbtn);
        mAuth = FirebaseAuth.getInstance();


        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check();

            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(LogIn.this, SignUp.class));
            }
        });


        twitterbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogIn.this, Twitter.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });
        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LogIn.this, ForgotPassword.class));
            }
        });
    }

    private void check() {
        if (!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
            Toast.makeText(LogIn.this, "Invalid email format", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(email.getText().toString())) {
            Toast.makeText(LogIn.this, "Enter email", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(password.getText().toString())) {
            Toast.makeText(LogIn.this, "Enter password", Toast.LENGTH_SHORT).show();
        } else if (password.getText().toString().length() < 6) {
            Toast.makeText(LogIn.this, "Passwoed must atleast 6 characters long", Toast.LENGTH_SHORT).show();
        } else {
            signin();
        }
    }

    private void signin() {
        mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(LogIn.this, "Importing successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LogIn.this, MainActivity.class));
                        }
                    }
                });
    }
}




