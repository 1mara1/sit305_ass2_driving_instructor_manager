package com.example.drivingschoolmanagerandplanner;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.drivingschoolmanagerandplanner.customclasses.StaticHelpers;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getIntent().getExtras() != null) {
            if (getIntent().getExtras().getString(StudentDashboardActivity.TITLE) != null) {
                String deletedStudent = getIntent().getExtras().getString(StudentDashboardActivity.TITLE);
                if (deletedStudent != null) {
                    // display message when an item was deleted
                    StaticHelpers.displayToastMessage(this, deletedStudent);
                }
            }
        }


        // Add the main dashboard - DashboardFragment
        StaticHelpers.LoadFragment(getSupportFragmentManager().beginTransaction(), R.id.FormsFrameLayout, new DashboardFragment());


    }
}
