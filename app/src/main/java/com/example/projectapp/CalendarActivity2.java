package com.example.projectapp;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.CalendarView;

public class CalendarActivity2 extends AppCompatActivity {

    private static final String TAG="CalendarActivity";

    private CalendarView mCalendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendars);
        mCalendarView=findViewById(R.id.calendarView);

        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                String date=i+"/" + i1 + "/" + i2;
                Log.d(TAG,"date"+ date);

                Intent intent =new Intent(CalendarActivity2.this,DonationList.class);
                intent.putExtra("date",date);
                startActivity(intent);
            }
        });

    }

}