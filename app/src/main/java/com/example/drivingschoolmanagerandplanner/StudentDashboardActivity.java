// StudentDashboardActivity holds two fragments for the student details
// Receives the data from other activities and fragments
/* Currently, receiving data from:
        - ListRecordsFragment
*/
// Posses data to ListRecordsFragment

package com.example.drivingschoolmanagerandplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.drivingschoolmanagerandplanner.customclasses.DbHelper;
import com.example.drivingschoolmanagerandplanner.customclasses.StaticHelpers;
import com.example.drivingschoolmanagerandplanner.models.Lesson;
import com.example.drivingschoolmanagerandplanner.models.Student;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class StudentDashboardActivity extends AppCompatActivity {

    private static final String TAG = "StudentDashboardActivi";
    public static final String FULL_NAME = "full_name";
    public static final String FIRST_NAME = "first_name";
    public static final String LAST_NAME = "last_name";
    public static final String MOBILE = "mobile";
    public static final String EMAIL = "email" ;
    public static final String ADDRESS = "address";
    public static final String ADDRESS_LINE = "address_line";
    public static final String STATE = "state";
    public static final String SUBURB = "suburb";
    public static final String POSTCODE = "postcode";
    public static final String COUNTRY = "country";
    public static final String TITLE = "title";
    public static final String STUDENT_ID = "student_id";
    public static final String NUMBER_LESSONS = "number_lessons";
    public static final String NUMBER_TESTS = "number_tests" ;
    public static final String NUMBER_PACKAGES = "number_packages";

    private TabLayout studentDashboardTabs;
    long studentId = -1;
    Student student;
    long lessonId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dashboard);

//       Retrieve the id from the bundle and query the database by studentId
        studentId = StaticHelpers.RetrieveIdFromBundle(getIntent(), ListItemsFragment.STUDENT_ID);

        student = DbHelper.getStudentById(this, studentId);

        LoadTopFragmentWithBundle(student);

        long[] keyValues = StaticHelpers.RetrieveIdsFromBundle(getIntent(), LessonFragment.LESSON_ID, LessonFragment.STUDENT_ID);
        ArrayList<Integer> studentLessons = new ArrayList<>();

        if(keyValues != null){
            lessonId = keyValues[0];
            studentId = keyValues[1];
            
            ArrayList<Lesson> lessons = DbHelper.getLessons(this);


            for (Lesson lesson : lessons) {
                if (lesson.getStudentId() == studentId) {
                    studentLessons.add(lesson.getStudentId());
                }
            }


        }

        Log.d(TAG, "onCreate: keyValues" + keyValues[0] + " " + keyValues[1]);

        Log.d(TAG, "onCreate: lessonId "+ lessonId);




        //StaticHelpers.LoadFragment(getSupportFragmentManager().beginTransaction(), R.id.studentDetailsBottom, new StudentDetailsBottomFragment());

        LoadBottomFragmentWithBundle(studentLessons.size(),0,0);

        studentDashboardTabs = (TabLayout) findViewById(R.id.studentDashboardTabs);
        Log.d(TAG, "onCreate: studentDashboardTabs" + studentDashboardTabs);


//        https://stackoverflow.com/questions/44899784/android-tabitem-onclick-doesnt-work
        studentDashboardTabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                String tabName = tab.getText().toString();
                Log.d(TAG, "onTabSelected: tabName " + tabName);

                if(tabName.contains("Student"))
                    LoadTopFragmentWithBundle(student);
                if(tabName.contains("Lessons")) {
                    StaticHelpers.LoadFragmentWithId(getSupportFragmentManager().beginTransaction(), new LessonFragment(), R.id.studentDetailsTop, (int) studentId);
                }


//                Add Package
//                Add Test
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                Log.d(TAG, "onTabUnselected: " + tab.getText());
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                Log.d(TAG, "onTabReselected: " + tab.getText());
            }
        });
    }


    // code based on https://stackoverflow.com/questions/6439085/android-how-to-create-option-menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @SuppressLint("ResourceType")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
//String firstName, String lastName, int phone, String email, String addressLine, String suburb, String state, int postcode, String country, int studentId

            case R.id.viewDashboard:
                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);
                break;
            case R.id.edit_student:
                Log.d(TAG, "onOptionsItemSelected: edit clicked and lesson selected as tab");
                Bundle bundle = new Bundle();
                bundle.putString(TITLE, "Update Student");
                bundle.putString(FIRST_NAME, student.getFirstName());
                bundle.putString(LAST_NAME, student.getLastName());
                bundle.putInt(MOBILE, student.getPhone());
                bundle.putString(EMAIL, student.getEmail());
                bundle.putString(ADDRESS_LINE, student.getAddressLine());
                bundle.putString(SUBURB, student.getSuburb());
                bundle.putString(STATE, student.getState());
                bundle.putInt(POSTCODE, student.getPostcode());
                bundle.putString(COUNTRY, student.getCountry());
                bundle.putLong(STUDENT_ID, studentId);

                StaticHelpers.LoadFragmentWithBundle(getSupportFragmentManager().beginTransaction(), new StudentFragment(), R.id.studentDetailsTop, bundle);

                break;
            case R.id.delete:
                Log.d(TAG, "onOptionsItemSelected: delete clicked ");

                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra(TITLE, "Student deleted successfully");
                startActivity(intent);
                DbHelper.deleteStudent(this, (int) studentId);
                break;
        }
        return true;
    }

    // end idea https://stackoverflow.com/questions/6439085/android-how-to-create-option-menu


    private void LoadTopFragmentWithBundle(Student student) {
        //         Create fragment for the top part of the student dashboard activity
//         create a bundle to pass the student values
        StudentDetailsTopFragment topFragment = new StudentDetailsTopFragment();
        Bundle args = new Bundle();
        args.putString(FULL_NAME, student.getFullName());
        args.putInt(MOBILE, student.getPhone());
        args.putString(EMAIL, student.getEmail());
        args.putString(ADDRESS, student.getAddress());
        topFragment.setArguments(args);

        StaticHelpers.LoadFragment(getSupportFragmentManager().beginTransaction(), R.id.studentDetailsTop,  topFragment) ;
    }

    private void LoadBottomFragmentWithBundle(int lessons, int test, int packages) {
        //         Create fragment for the top part of the student dashboard activity
//         create a bundle to pass the student values
        StudentDetailsBottomFragment bottomFragment = new StudentDetailsBottomFragment();
        Bundle args = new Bundle();
        args.putInt(NUMBER_LESSONS, lessons);
        args.putInt(NUMBER_PACKAGES, packages);
        args.putInt(NUMBER_TESTS, test);
        args.putLong(STUDENT_ID, studentId);
        bottomFragment.setArguments(args);

        StaticHelpers.LoadFragment(getSupportFragmentManager().beginTransaction(), R.id.studentDetailsBottom,  bottomFragment) ;
    }
}
