package com.example.drivingschoolmanagerandplanner;

import android.content.Intent;
import android.os.Bundle;

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

import android.view.View;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class ListActivity extends AppCompatActivity implements DashboardFragment.UpdateFrag {
    String fragStr;
    final ArrayList<DrivingTest> tests = new ArrayList<>();
    final ArrayList<Student> students = new ArrayList<>();
    final ArrayList<Package> packages = new ArrayList<>();
    final ArrayList<Lesson> lessons = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        //TODO: get records from database //Student studentDriver, String location, Date date, Date time, int bookingNumber, boolean result
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String dt = dateFormat.format(date);

        Calendar s = Calendar.getInstance();
        s.getTime();
        s.add(Calendar.HOUR, 1);

        Calendar e = Calendar.getInstance();
        e.getTime();
        e.add(Calendar.HOUR, 1);


        tests.add(new DrivingTest(new Student("Jeffey", "Smith"), "",  s.getTime(), e.getTime(),1223, true));

//        tests.add(new DrivingTest(new Student("Sonia", "Smith"), "March","12:00","Liverpool",1223, true));
//        tests.add(new DrivingTest(new Student("Bill", "Smith"), "March","12:00","Liverpool",1223, true));
//        tests.add(new DrivingTest(new Student("Anna", "Smith"), "March","12:00","Liverpool",1223, true));
//        tests.add(new DrivingTest(new Student("Jeffey", "Smith"), "March","12:00","Liverpool",1223, true));
//        tests.add(new DrivingTest(new Student("Elena", "Smith"), "March","12:00","Liverpool",1223, true));
//        tests.add(new DrivingTest(new Student("Fred", "Smith"), "March","12:00","Liverpool",1223, true));
//        tests.add(new DrivingTest(new Student("Jade", "Smith"), "March","12:00","Liverpool",1223, true));
//        tests.add(new DrivingTest(new Student("JS", "Smith"), "March","12:00","Liverpool",1223, true));
//        tests.add(new DrivingTest(new Student("So", "Smith"), "March","12:00","Liverpool",1223, true));
//        tests.add(new DrivingTest(new Student("Billian", "Smith"), "March","12:00","Liverpool",1223, true));
//        tests.add(new DrivingTest(new Student("Anneta", "Smith"), "March","12:00","Liverpool",1223, true));

        updatefrag();
    }

    @Override
    public void updatefrag() {
       Intent intent = getIntent();
       fragStr =  intent.getStringExtra("STARTACTIVITY");
        Fragment frag = new ListRecordsFragment<DrivingTest>(tests);

        if(fragStr.equals("tests"))
            frag = new ListRecordsFragment<DrivingTest>(tests);

        if(fragStr.equals("students"))
            frag = new ListRecordsFragment<Student>(students);

        if(fragStr.equals("lessons"))
            frag = new ListRecordsFragment<Lesson>(lessons);

        if(fragStr.equals("packages"))
            frag = new ListRecordsFragment<Package>(packages);

            FragmentManager manager = this.getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = manager.beginTransaction();
            fragmentTransaction.replace(R.id.ListFrameLayout, frag).addToBackStack(null);
            fragmentTransaction.commit();
    }

}
