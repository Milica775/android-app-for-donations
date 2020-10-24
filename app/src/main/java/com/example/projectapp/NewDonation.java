package com.example.projectapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NewDonation extends AppCompatActivity {

    EditText numOfMeal,address,date;
    Button btnAddDon;
     FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference myRef;
    Button btnCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_donation);

        numOfMeal=findViewById(R.id.numOfMeal);
        address=findViewById(R.id.address);
        btnAddDon=findViewById(R.id.btnAddDon);
        date=findViewById(R.id.date);
        btnCalendar=findViewById(R.id.btnCalendar);



        Intent incomingIntent=getIntent();
        String dateS=incomingIntent.getStringExtra("date");
        date.setText(dateS);


        btnCalendar.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                Intent intent=new Intent(NewDonation.this,CalendarsActivity.class);
                startActivity(intent);
            }
        });

        insertData();
    }

    private void insertData() {
        database=FirebaseDatabase.getInstance();
        myRef=database.getReference( "Donation");
        auth=FirebaseAuth.getInstance();

        btnAddDon.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                String addressString= address.getText().toString().trim();
                int numOfMealString = Integer.parseInt(numOfMeal.getText().toString().trim());
                String dateString = date.getText().toString();
                String id=myRef.push().getKey();

                String email=auth.getCurrentUser().getEmail();
                Donation donation= new Donation(addressString,numOfMealString,email,dateString,id);

                /*long dataTime= 999999999-System.currentTimeMillis();
                String orderTime=String.valueOf(dataTime);*/
                myRef.child(id).setValue(donation).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(NewDonation.this, "Uspjesno ste dodali donaciju!", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(NewDonation.this, "Greska prilikom dodavanja!", Toast.LENGTH_SHORT).show();
                    }
                });

                startActivity(new Intent(getApplicationContext(), MainActivity.class));

                createNotificationChannel();
                notifyDon();

            }
        });
  }

    private void createNotificationChannel() {

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O)
        {
            CharSequence name="studentChannel";
            String description="Channel";
            int impotance= NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel=new NotificationChannel("bl",name,impotance);
            channel.setDescription(description);

            NotificationManager notMan=getSystemService(NotificationManager.class);
            notMan.createNotificationChannel(channel);
        }
    }

    private void notifyDon() {

        NotificationCompat.Builder builder= new NotificationCompat.Builder(this,"bl")
                .setSmallIcon(R.drawable.ic_baseline_notifications_none_24)
                .setContentTitle("Nova donacija")
                .setContentText("Pristigla je nova donacija putem aplikacije !")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notManager=NotificationManagerCompat.from(this);

        notManager.notify(100,builder.build());

    }




}
