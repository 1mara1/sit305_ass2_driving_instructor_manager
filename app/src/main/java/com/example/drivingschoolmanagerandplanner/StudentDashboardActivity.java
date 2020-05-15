// StudentDashboardActivity holds two fragments for the student details
// Receives the data from other activities and fragments
/* Currently, receiving data from:
        - ListRecordsFragment
*/
// Posses data to ListRecordsFragment

package com.example.drivingschoolmanagerandplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.drivingschoolmanagerandplanner.customclasses.DbHelper;
import com.example.drivingschoolmanagerandplanner.customclasses.StaticHelpers;
import com.example.drivingschoolmanagerandplanner.models.Lesson;
import com.example.drivingschoolmanagerandplanner.models.Student;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class StudentDashboardActivity extends AppCompatActivity {

    private static final String TAG = "StudentDashboardActivi";
    public static final String FULL_NAME = "full_name";
    public static final String MOBILE = "mobile";
    public static final String EMAIL = "email" ;
    public static final String ADDRESS = "address";
    public static final String NUMBER_LESSONS = "number_lessons";
    public static final String NUMBER_TESTS = "number_tests" ;
    public static final String NUMBER_PACKAGES = "number_packages";

    private TabLayout studentDashboardTabs;
    long id = -1;
    Student student;
    long lessonId = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dashboard);

      //  InitialiseWidgets();

//       Retrieve the id from the bundle and query the database by studentId


        id = StaticHelpers.RetrieveIdFromBundle(getIntent(), ListRecordsFragment.STUDENT_ID);

        student = DbHelper.getStudentById(this, id);

        LoadTopFragmentWithBundle(student);

        long[] keyValues = StaticHelpers.RetrieveIdsFromBundle(getIntent(), LessonFragment.LESSON_ID, LessonFragment.STUDENT_ID);
        ArrayList<Integer> studentLessons = new ArrayList<>();

        if(keyValues != null){
            lessonId = keyValues[0];
            id = keyValues[1];
            
            ArrayList<Lesson> lessons = DbHelper.getLessons(this);


            for (Lesson lesson : lessons) {
                if (lesson.getStudentId() == id) {
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

                if(tabName.contains("Add Lesson")) {
                    StaticHelpers.LoadFragmentWithId(getSupportFragmentManager().beginTransaction(), new LessonFragment(), R.id.studentDetailsTop, (int) id);
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
        bottomFragment.setArguments(args);

        StaticHelpers.LoadFragment(getSupportFragmentManager().beginTransaction(), R.id.studentDetailsBottom,  bottomFragment) ;
    }


}
