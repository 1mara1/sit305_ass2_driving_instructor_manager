package com.example.drivingschoolmanagerandplanner.customclasses;

import android.app.Activity;
import com.example.drivingschoolmanagerandplanner.data.DbHandler;
import com.example.drivingschoolmanagerandplanner.models.DrivingTest;
import com.example.drivingschoolmanagerandplanner.models.Lesson;
import com.example.drivingschoolmanagerandplanner.models.Student;

import java.util.ArrayList;

public class DbHelper {

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

    public static Lesson getLessonById(Activity activity, int id){
        DbHandler db = getDbHandler(activity);
        return db.GetLessonById(id);
    }

    public static int updateStudent(Activity activity, String firstName, String lastName, int phone, String email, String addressLine, String suburb, String state, int postcode, String country, int studentId){
        DbHandler db = getDbHandler(activity);
      return  db.updateStudentDetails(firstName, lastName,  String.valueOf(phone), email, addressLine, suburb, state, String.valueOf(postcode), country,  studentId);
    }

    public static void deleteStudent(Activity activity, int studentId){
        DbHandler db = getDbHandler(activity);
        db.deleteStudent(studentId);
        db.close();
    }
}
