package com.example.drivingschoolmanagerandplanner;

import android.content.Intent;
import android.os.Bundle;

import com.example.drivingschoolmanagerandplanner.data.DbHandler;
import com.example.drivingschoolmanagerandplanner.models.DrivingTest;
import com.example.drivingschoolmanagerandplanner.models.Lesson;
import com.example.drivingschoolmanagerandplanner.models.Package;
import com.example.drivingschoolmanagerandplanner.models.Student;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.ListFragment;

import android.util.Log;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

public class ListActivity extends AppCompatActivity implements DashboardFragment.UpdateFrag {
    private static final String TAG = "ListActivity";
    String fragStr;
    final ArrayList<DrivingTest> tests = new ArrayList<>();
    final ArrayList<Student> students = new ArrayList<>();
    final ArrayList<Package> packages = new ArrayList<>();
    final ArrayList<Lesson> lessons = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        //TODO: get records from database

        Calendar s = Calendar.getInstance();
        s.getTime();
        s.add(Calendar.HOUR, 1);

        Calendar e = Calendar.getInstance();
        e.getTime();
        e.add(Calendar.HOUR, 2);


        updatefrag();
    }


    public void updatefrag() {
       Intent intent = getIntent();
       fragStr =  intent.getStringExtra("STARTACTIVITY");
        Fragment frag = new ListRecordsFragment<DrivingTest>(tests);

        if(fragStr.equals("tests"))
            frag = new ListRecordsFragment<DrivingTest>(tests);

//        if(fragStr.equals("students"))
//            frag = new ListRecordsFragment<Student>(getStudentsFromDB());

        if(fragStr.equals("lessons"))
            frag = new ListRecordsFragment<Lesson>(lessons);

        if(fragStr.equals("packages"))
            frag = new ListRecordsFragment<Package>(packages);

            FragmentManager manager = this.getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = manager.beginTransaction();
            fragmentTransaction.replace(R.id.ListFrameLayout, frag).addToBackStack(null);
            fragmentTransaction.commit();
    }


//    private ArrayList<Student> getStudentsFromDB(){
//        DbHandler db = new DbHandler(this);
//        ArrayList<Student> userList = db.GetStudents();
//        Log.d(TAG, "getStudentsFromDB: number of records " + userList.size());
//        db.close();
//        return  userList;
//
//    }




}
