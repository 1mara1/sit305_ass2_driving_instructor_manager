// StudentDashboardActivity holds two fragments for the student details
// Receives the data from other activities and fragments
/* Currently, receiving data from:
        - ListRecordsFragment
*/
// Posses data to ListRecordsFragment

package com.example.drivingschoolmanagerandplanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;

import com.example.drivingschoolmanagerandplanner.customclasses.DbHelper;
import com.example.drivingschoolmanagerandplanner.customclasses.StaticHelpers;
import com.example.drivingschoolmanagerandplanner.models.Student;
import com.google.android.material.tabs.TabLayout;

public class StudentDashboardActivity extends AppCompatActivity {

    private static final String TAG = "StudentDashboardActivi";
    public static final String FULL_NAME = "full_name";
    public static final String MOBILE = "mobile";
    public static final String EMAIL = "email" ;
    public static final String ADDRESS = "address";
    private TabLayout studentDashboardTabs;
    long id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dashboard);

      //  InitialiseWidgets();

//       Retrieve the id from the bundle and query the database by studentId
        id = StaticHelpers.RetrieveStudentIdFromBundle(getIntent());
        Student student = DbHelper.getStudentById(this, id);

        LoadTopFragmentWithBundle(student);

//         region Bottom Fragment
        FragmentTransaction fragmentTransactionBottom = getSupportFragmentManager().beginTransaction();
        fragmentTransactionBottom.replace(R.id.studentDetailsBottom, new StudentDetailsBottomFragment());
        fragmentTransactionBottom.commit();
//         endregion Bottom Fragment



        studentDashboardTabs = (TabLayout) findViewById(R.id.studentDashboardTabs);
        Log.d(TAG, "onCreate: studentDashboardTabs" + studentDashboardTabs);


//        https://stackoverflow.com/questions/44899784/android-tabitem-onclick-doesnt-work
        studentDashboardTabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                StaticHelpers.displayToastMessage(getApplicationContext(), "tab clicked: text -> " +  tab.getText() + " tag -> " + tab.getTag());
                StaticHelpers.LoadActivityWithStudentId(getApplicationContext(), LessonFragment.class, id);

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

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
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

//         Replace the fragment_container view with this fragment,
        transaction.replace(R.id.studentDetailsTop, topFragment);
        //transaction.addToBackStack(null);
        // Commit the transaction
        transaction.commit();
    }

}
