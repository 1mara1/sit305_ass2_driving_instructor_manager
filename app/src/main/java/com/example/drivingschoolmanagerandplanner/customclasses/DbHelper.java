package com.example.drivingschoolmanagerandplanner.customclasses;

import android.app.Activity;
import androidx.fragment.app.Fragment;
import com.example.drivingschoolmanagerandplanner.data.DbHandler;
import com.example.drivingschoolmanagerandplanner.models.DrivingTest;
import com.example.drivingschoolmanagerandplanner.models.Lesson;
import com.example.drivingschoolmanagerandplanner.models.Student;
import java.util.ArrayList;

public class DbHelper {

    public static DbHandler getDbHandler(Fragment activity){
        return new DbHandler(activity.getActivity());
    }

    public static DbHandler getDbHandler(Activity activity){
        return new DbHandler(activity.getApplicationContext());
    }

    public static Student getStudentById(Activity activity, long id){
        DbHandler db = getDbHandler(activity);
        return db.GetStudentById((int)id);
    }

    public static ArrayList<Student> getStudents(Activity activity){
        DbHandler db = getDbHandler(activity);
        return db.getStudents();
    }

    public static ArrayList<Lesson> getLessons(Activity activity){
        DbHandler db = getDbHandler(activity);
        return db.getLessons();
    }

    public static ArrayList<DrivingTest> getTests(Activity activity){
        DbHandler db = getDbHandler(activity);
        return db.getDrivingTests();
    }
}
