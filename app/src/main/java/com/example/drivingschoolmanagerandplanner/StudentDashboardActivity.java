package com.example.drivingschoolmanagerandplanner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.drivingschoolmanagerandplanner.data.DbHandler;
import com.example.drivingschoolmanagerandplanner.models.Student;

import java.util.Objects;

public class StudentDashboardActivity extends AppCompatActivity  implements StudentDetailsTopFragment.OnHeadlineSelectedListener {

    private static final String TAG = "StudentDashboardActivi";
    long id = -1;


//    @Override
//    public void startActivityFromFragment(@NonNull Fragment fragment, Intent intent, int requestCode, @Nullable Bundle options) {
//
//
//        int id = intent.getIntExtra("studentId", -1);
//        super.startActivityFromFragment(fragment, intent, requestCode, options);
//        Log.d(TAG, "startActivityFromFragment: studentId" +id);
//
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dashboard);

        Log.d(TAG, "onCreate: studentId before " +id);
        // https://www.codexpedia.com/android/passing-data-to-activity-and-fragment-in-android/
       id = Objects.requireNonNull(getIntent().getExtras()).getLong("studentId");
        Log.d(TAG, "onCreate: studentId " +id);

        Student student = GetStudent(id);



        // Create fragment and give it an argument for the selected article
        StudentDetailsTopFragment topFragment = new StudentDetailsTopFragment();
        Bundle args = new Bundle();
        args.putString("fullName", student.getFullName());
        topFragment.setArguments(args);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.add(R.id.studentDetailsTop, topFragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();


//        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//        fragmentTransaction.add(R.id.studentDetailsTop, new StudentDetailsTopFragment());
//        fragmentTransaction.commit();

        FragmentTransaction fragmentTransactionBottom = getSupportFragmentManager().beginTransaction();
        fragmentTransactionBottom.add(R.id.studentDetailsBottom, new StudentDetailsBottomFragment());
        fragmentTransactionBottom.commit();

    }

    public Student GetStudent(long id ){
       DbHandler db = new DbHandler(this);
      return db.GetStudentById((int) id);
    }

    @Override
    public void onArticleSelected(int position) {

    }
}
