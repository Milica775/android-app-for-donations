package com.example.projectapp;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;


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