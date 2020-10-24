package com.example.projectapp;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.view.LayoutInflater;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView navigationView;
    FirebaseAuth fAuth;
    MenuItem in;
    MenuItem out;

    @Override
    protected void onCreate(Bundle savedInstanceState) {




        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDrawerLayout=(DrawerLayout)findViewById(R.id.drawer);
        navigationView=findViewById(R.id.navView);
        Menu menu=navigationView.getMenu();
        in=menu.findItem(R.id.In);
        out=menu.findItem(R.id.Out);
        fAuth=FirebaseAuth.getInstance();
        mToggle=new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open,R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        updateUI();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.reg:
                        startActivity(new Intent(getApplicationContext(), Registration.class));
                        return true;

                    case R.id.In:
                        startActivity(new Intent(getApplicationContext(), LogIn.class));
                         return true;

                    case R.id.Out:
                        fAuth.signOut();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));

                        return true;

                    case R.id.donAll:
                    startActivity(new Intent(getApplicationContext(), Admin.class));
                    return true;

                    case R.id.don:

                        if(fAuth.getCurrentUser()!=null) {
                            startActivity(new Intent(getApplicationContext(), DonationList.class));
                        } else
                        {
                            startActivity(new Intent(getApplicationContext(), LogIn.class));
                        }
                        return true;

                    case R.id.add:
                        if(fAuth.getCurrentUser()!=null) {
                            startActivity(new Intent(getApplicationContext(), NewDonation.class));
                        } else
                        {
                            startActivity(new Intent(getApplicationContext(), LogIn.class));
                        }
                        return true;

                    case R.id.user:
                    {
                        startActivity(new Intent(getApplicationContext(), Donators.class));


                    }

                        return true;



                }
                return false;
            }

        });


    }

    private void updDelUser() {

        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_user, null);
        dialogBuilder.setView(dialogView);

        final EditText firstName = (EditText) dialogView.findViewById(R.id.eTFirstName);
        final EditText lastName = (EditText) dialogView.findViewById(R.id.eTNumOfMeals);
        final EditText email = (EditText) dialogView.findViewById(R.id.eTemail);
        final EditText password = (EditText) dialogView.findViewById(R.id.eTpassword);
        final ImageView btnUpdate = dialogView.findViewById(R.id.update);
        final ImageView btnDelete = dialogView.findViewById(R.id.delete);
        FirebaseFirestore fStore= FirebaseFirestore.getInstance();

        FirebaseUser u=fAuth.getCurrentUser();

        String userID=u.getUid();

        DocumentReference dRef=fStore.collection("users").document(userID);




          String firstName1=firstName.getText().toString();

        final AlertDialog b = dialogBuilder.create();
        b.show();
        dRef.update("firstName",firstName1);
    }

    private void updateUI() {


        if(fAuth.getCurrentUser()!=null){

            in.setVisible(false);
            out.setVisible(true);

        } else if(fAuth.getCurrentUser()==null) {
            in.setVisible(true);
            out.setVisible(false);
        }

    }


    public boolean onOptionsItemSelected(MenuItem item) {

        if(mToggle.onOptionsItemSelected(item))
        {
            return true;
        }
            return super.onOptionsItemSelected(item);

    }


}