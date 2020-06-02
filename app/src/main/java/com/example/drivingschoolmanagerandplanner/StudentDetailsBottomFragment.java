// The fragment is used to display the lesson for a specific student

package com.example.drivingschoolmanagerandplanner;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.drivingschoolmanagerandplanner.customclasses.DbHelper;
import com.example.drivingschoolmanagerandplanner.customclasses.StaticHelpers;
import com.example.drivingschoolmanagerandplanner.models.Lesson;

import java.util.ArrayList;
import java.util.Objects;

public class StudentDetailsBottomFragment extends Fragment {

    static final String TITLE = "lessons";
    private TextView numberLessonsTextView;
    private int numberOfLessons;
    private long studentId;

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
        ImageButton lessonsForStudentButton = StaticHelpers.initialiseImageButton(view, R.id.lessonsForStudentButton);
        ImageButton lesson2AddImageButton = StaticHelpers.initialiseImageButton(view, R.id.lesson2AddImageButton);
        setLessonDetailsFromActivity();
        setLessonsDetailsToTextView();
        final ArrayList<Lesson> filteredLessons = new ArrayList<>();
        final ArrayList<Lesson> lessons = DbHelper.getLessons(getActivity());
        if(lessons!= null) {
            for (Lesson lesson : lessons) {
                if (lesson.getStudentId() == studentId) {
                    filteredLessons.add(lesson);
                }
            }
        }

        lessonsForStudentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new ListItemsFragment<Lesson>(filteredLessons),"Lessons");
            }
        });

        lesson2AddImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new LessonFragment(),"New Lesson");
            }
        });

        return view;
    }

    private void setLessonDetailsFromActivity(){
        numberOfLessons = getArguments().getInt(StudentDashboardActivity.NUMBER_LESSONS);
        studentId = getArguments().getLong(StudentDashboardActivity.STUDENT_ID);
    }

    private void setLessonsDetailsToTextView(){
        numberLessonsTextView.setText(String.valueOf(numberOfLessons));
    }

    private void setFragment(Fragment fragment, String title){
        Bundle args = new Bundle();

        FragmentManager manager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        fragmentTransaction.replace(R.id.studentDetailsTop, fragment).addToBackStack(null);
        args.putString(TITLE, title);
        fragment.setArguments(args);
        fragmentTransaction.commit();
    }
}
