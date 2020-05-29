package com.example.drivingschoolmanagerandplanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.drivingschoolmanagerandplanner.customclasses.StaticHelpers;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(getIntent().getExtras() != null) {
            if (getIntent().getExtras().getString(StudentDashboardActivity.TITLE) != null) {
                String deletedStudent = getIntent().getExtras().getString(StudentDashboardActivity.TITLE);
                if (deletedStudent != null) {
                    StaticHelpers.displayToastMessage(this, deletedStudent);
                }
            }
        }


        // Add the main dashboard - DashboardFragment
        StaticHelpers.LoadFragment(getSupportFragmentManager().beginTransaction(), R.id.FormsFrameLayout, new DashboardFragment());




    }
}
