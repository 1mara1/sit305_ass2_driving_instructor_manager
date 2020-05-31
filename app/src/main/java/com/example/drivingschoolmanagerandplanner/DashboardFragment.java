// DashboardFragment

package com.example.drivingschoolmanagerandplanner;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.drivingschoolmanagerandplanner.customclasses.DbHelper;
import com.example.drivingschoolmanagerandplanner.customclasses.StaticHelpers;
import com.example.drivingschoolmanagerandplanner.models.DrivingTest;
import com.example.drivingschoolmanagerandplanner.models.Lesson;
import com.example.drivingschoolmanagerandplanner.models.Student;
import java.util.ArrayList;
import java.util.Objects;


public class DashboardFragment extends Fragment {

    private static final String TAG = "DashboardFragment";
    public static final String TITLE = "title";
    private ArrayList<Student> students;
    private ArrayList<Lesson> lessons;
    private ImageButton lessonAddButton, studentAddButton, studentsListButton,  lessonsListButton;
    private TextView studentTotalTextView, lessonsTotalTextView;

    public DashboardFragment() {
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
        final View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        InitialiseDataList(view);
        initialiseWidgetReferences(view);
        setImageViewButtonsOncLickListeners();
        setTotals();

        return view;
    }

    // region private methods

    private void InitialiseDataList(View view){
        students = DbHelper.getStudents((Activity) getContext());
        lessons = DbHelper.getLessons((Activity) getContext());
    }

    private void initialiseWidgetReferences(View view) {
        studentAddButton = StaticHelpers.initialiseImageButton(view, R.id.studentsAddImageButton);
        lessonAddButton = StaticHelpers.initialiseImageButton(view, R.id.lessonAddImageButton);
        studentsListButton = StaticHelpers.initialiseImageButton(view, R.id.numberLessonsTextView);
        lessonsListButton = StaticHelpers.initialiseImageButton(view, R.id.lessonViewImageButton);
        studentTotalTextView = StaticHelpers.initialiseTextView(view, R.id.studentTotalTextView);
        lessonsTotalTextView = StaticHelpers.initialiseTextView(view, R.id.lessonsTotalTextView);
    }

    private void setImageViewButtonsOncLickListeners() {

        // Form fragments
        studentAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new StudentFragment(),"New Student");
            }
        });

        lessonAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new LessonFragment(),"New Lesson");
            }
        });

        studentsListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new ListItemsFragment<Student>(students),"Students");
            }
        });

        lessonsListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new ListItemsFragment<Lesson>(lessons),"Lessons");
            }
        });


    }

    private void setFragment(Fragment fragment, String title){
        Bundle args = new Bundle();

        FragmentManager manager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        fragmentTransaction.replace(R.id.FormsFrameLayout, fragment).addToBackStack(null);

        args.putString(TITLE, title);
        fragment.setArguments(args);

        fragmentTransaction.commit();
    }

    private void setTotals(){
        studentTotalTextView.setText(String.valueOf(students.size()));
        lessonsTotalTextView.setText(String.valueOf(lessons.size()));
    }
    //endregion private methods
}