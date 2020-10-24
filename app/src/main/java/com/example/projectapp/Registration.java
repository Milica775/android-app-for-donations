package com.example.projectapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.HashMap;
import java.util.Map;

public class Registration extends AppCompatActivity {

    EditText firstName, lastName, email, password;
    Button btnRegistration;
    ProgressBar progressBar;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore fStore;
    String userID;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        firstName = findViewById(R.id.fistName);
        lastName = findViewById(R.id.lastName);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        btnRegistration = findViewById(R.id.btnRegistration);
        progressBar = findViewById(R.id.progressBar);

        firebaseAuth = FirebaseAuth.getInstance();
        fStore= FirebaseFirestore.getInstance();

     /*   if (firebaseAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }*/
        btnRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String emailString = email.getText().toString().trim();
                final String passwordString = password.getText().toString().trim();
                final String firstNameString =firstName.getText().toString();
                final String lastNameString =lastName.getText().toString();


                if (TextUtils.isEmpty(emailString)) {
                    email.setError("Unos email-a je obavezan!");
                    return;
                }

                if (TextUtils.isEmpty(passwordString)) {
                    password.setError("Unos lozinke je obavezan!");
                    return;
                }

                if (passwordString.length() < 6) {
                    password.setError("Dužina lozinke mora biti veća od 6.karaktera!");
                    return;

                }

                progressBar.setVisibility(View.VISIBLE);

                firebaseAuth.createUserWithEmailAndPassword(emailString, passwordString).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {


                                Toast.makeText(Registration.this, "Korisnik je kreiran!", Toast.LENGTH_SHORT).show();
                            if (!emailString.equals("admin@gmail.com") && !passwordString.equals("adminadmin")) {

                                userID = firebaseAuth.getCurrentUser().getUid();
                                DocumentReference dRef = fStore.collection("users").document(userID);
                                Map<String, Object> user = new HashMap<>();
                                user.put("firstName", firstNameString);
                                user.put("lastName", lastNameString);
                                user.put("email", emailString);
                                dRef.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {

                                    }
                                });
                            }
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));

                        } else {

                            Toast.makeText(Registration.this, "Greska!", Toast.LENGTH_SHORT).show();
                        }

                    }


                });

            }
        });
    }
}