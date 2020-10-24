package com.example.projectapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LogIn extends AppCompatActivity {

    EditText email, password;
    Button btnLog;
    ProgressBar progressBar;
    FirebaseAuth firebaseAuth;
   // Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        btnLog = findViewById(R.id.btnLog);
        progressBar = findViewById(R.id.progressBar);
       // spinner=findViewById(R.id.spinner);

        firebaseAuth = FirebaseAuth.getInstance();

       // ArrayAdapter<CharSequence> adapter= ArrayAdapter.createFromResource(this, R.array.userType, R.layout.support_simple_spinner_dropdown_item);
        //spinner.setAdapter(adapter);

        btnLog.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                String emailString = email.getText().toString().trim();
                String passwordString = password.getText().toString().trim();

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

                firebaseAuth.signInWithEmailAndPassword(emailString,passwordString).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                       // String item= spinner.getSelectedItem().toString();

                        if(task.isSuccessful()) {
                            Toast.makeText(LogIn.this, "Uspjesno ste se ulogovali!", Toast.LENGTH_SHORT).show();


                                startActivity(new Intent(LogIn.this,MainActivity.class));



                        } else {
                            Toast.makeText(LogIn.this, "Greska!", Toast.LENGTH_SHORT).show();

                        }
                    }
                });

            }
        });

    }
}