package com.example.projectapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class DonationList extends AppCompatActivity implements Adapter.OnNoteListener{

    private RecyclerView rView;
    private Adapter adapter;
    List<Donation> donations;
    private DatabaseReference dReference;
    private FirebaseAuth fAuth;
    SearchView sView;
    List<Donation> donList;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation_list);

        rView=findViewById(R.id.recyclerView);
        rView.setHasFixedSize(true);
        sView=findViewById(R.id.search);
        LinearLayoutManager LManager= new LinearLayoutManager(this, RecyclerView.VERTICAL,false);
        rView.setLayoutManager(LManager);
        donations=new ArrayList<>();

        if(sView!=null)
        {
            sView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    search(newText);
                    return true;
                }
            });
        }







        DisplayData();








    }

    private void search(String str) {

        donList = new ArrayList<>();
        for (Donation don : donations) {

            if (don.getAddress().toLowerCase().contains(str.toLowerCase())) {
                donList.add(don);
            }

        }
        adapter=new Adapter(getApplicationContext(), donList, this);
        rView.setAdapter(adapter);
    }



    private void DisplayData() {
        dReference= FirebaseDatabase.getInstance().getReference("Donation");
        fAuth = FirebaseAuth.getInstance();
        adapter=new Adapter(getApplicationContext(), donations, this);

        dReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                donations.clear();
                for(DataSnapshot snap : dataSnapshot.getChildren()){
                    Donation don=snap.getValue(Donation.class);
                    if(don.email.equals(fAuth.getCurrentUser().getEmail())) {
                        donations.add(don);
                   }
                }

                rView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    @Override
    public void onNoteClick(final int position) {


        final Donation don = donations.get(position);
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_donation, null);
        dialogBuilder.setView(dialogView);

        final EditText address = (EditText) dialogView.findViewById(R.id.eTAddress);
        final EditText numOfMeal = (EditText) dialogView.findViewById(R.id.eTNumOfMeals);
        final EditText date = (EditText) dialogView.findViewById(R.id.eTDate);
        final Button btnCal = (Button) dialogView.findViewById(R.id.button4);
        final ImageView btnUpdate = dialogView.findViewById(R.id.update);
        final ImageView btnDelete = dialogView.findViewById(R.id.delete);

        date.setText(don.getDate());
        address.setText(don.getAddress());
        numOfMeal.setText(String.valueOf(don.getNumOfDonation()));
        btnCal.setVisibility(View.INVISIBLE);

        final AlertDialog b = dialogBuilder.create();
        b.show();




        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {






                AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                        DonationList.this);

                alertDialog.setTitle("Brisanje");
                alertDialog.setMessage("Da li ste sigurni da zelite da obrisete donaciju ?");
                alertDialog.setPositiveButton("Da", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {

                        String id = don.getId();
                        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("Donation").child(id);
                        dR.removeValue();
                        b.dismiss();

                    }
                });
                alertDialog.setNegativeButton("Ne", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // close dialog
                        dialog.cancel();
                    }
                });
                alertDialog.show();




            }

        });


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                String dateString = date.getText().toString().trim();
               // String dateString = date.getText().toString().trim();

                String addressString = address.getText().toString().trim();
                int numOfMealString = Integer.parseInt(numOfMeal.getText().toString().trim());
                String id=don.getId();
                String email1=don.getEmail();

                if (!TextUtils.isEmpty(addressString)// && !TextUtils.isEmpty(numOfMealString)
                         && !TextUtils.isEmpty(dateString)) {
                    updateArtist(id, addressString, email1, numOfMealString, dateString);
                     b.dismiss();
                }

            }
        });

    }



    private void updateArtist(String id,  String addressString, String email, int numOfMealString, String date) {
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("Donation").child(id);
        Donation don=new Donation(addressString,numOfMealString,email,date,id);
        dR.setValue(don);


    }
  }
