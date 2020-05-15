package com.example.drivingschoolmanagerandplanner;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.drivingschoolmanagerandplanner.customclasses.StaticHelpers;

public class StudentDetailsBottomFragment extends Fragment {

    TextView numberLessonsTextView, numberPackagesTextView, numberTestsTextView;
    int lessons, tests, packages;

    public StudentDetailsBottomFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_student_details_bottom, container, false);

        numberLessonsTextView = StaticHelpers.initialiseTextView(view, R.id.numberLessonsTextView);
        numberPackagesTextView = StaticHelpers.initialiseTextView(view, R.id.numberPackagesTextView);
        numberTestsTextView = StaticHelpers.initialiseTextView(view, R.id.numberTestsTextView);
        setLessonDetailsFromActivity();
        setLessonsDetailsToTextView();

        return view;
    }

    private void setLessonDetailsFromActivity(){
        lessons = getArguments().getInt(StudentDashboardActivity.NUMBER_LESSONS);
        tests = getArguments().getInt(StudentDashboardActivity.NUMBER_TESTS);
        packages = getArguments().getInt(StudentDashboardActivity.NUMBER_PACKAGES);
    }

    private void setLessonsDetailsToTextView(){
        numberLessonsTextView.setText(String.valueOf(lessons));
        numberTestsTextView.setText(String.valueOf(tests));
        numberPackagesTextView.setText(String.valueOf(tests));
    }


}
