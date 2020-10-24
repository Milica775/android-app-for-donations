package com.example.projectapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.CalendarView;

public class CalendarsActivity extends AppCompatActivity {

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

                Intent intent =new Intent(CalendarsActivity.this,NewDonation.class);
                intent.putExtra("date",date);
                startActivity(intent);
            }
        });

    }


}