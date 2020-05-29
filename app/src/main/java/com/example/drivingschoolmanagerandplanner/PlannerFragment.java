package com.example.drivingschoolmanagerandplanner;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.drivingschoolmanagerandplanner.customclasses.DbHelper;
import com.example.drivingschoolmanagerandplanner.customclasses.StaticHelpers;
import com.example.drivingschoolmanagerandplanner.models.Lesson;


public class PlannerFragment extends Fragment {
    private static final String TAG = "PlannerFragment" ;

    private RatingBar performanceRatingBar;
    private TextView studentTextView, lessonTextView;
    private EditText topicEditText;
    private Button startMapButton;

    int lessonId;
    String studentName;


    public PlannerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            lessonId = getArguments().getInt(ListItemsFragment.POSITION);
            studentName = getArguments().getString(ListItemsFragment.STUDENT_NAME);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_planner, container, false);

        initialiseWidgets(view);


        //https://www.tutlane.com/tutorial/android/android-ratingbar-with-examples
        int noofstars = performanceRatingBar.getNumStars();
        float getrating = performanceRatingBar.getRating();


        if (lessonId > 0) {
            // we have the lesson id therefore retrieve the lesson from the database
            Log.d(TAG, "onCreateView: lesson id is: " + lessonId);

            Lesson lesson = DbHelper.getLessonById(getActivity(), lessonId);

            lessonTextView.setText(new StringBuilder().append(lesson.getDay()).append(" ").append(lesson.getStartTime()).append(" ").append(lesson.getEndTime()).toString());
            studentTextView.setText(studentName);
//            StaticHelpers.displayToastMessage(getContext(),"");
        }



        return view;
    }

    private void initialiseWidgets(View view) {
        performanceRatingBar = (RatingBar) view.findViewById(R.id.performanceRatingBar);
        studentTextView = StaticHelpers.initialiseTextView(view, R.id.studentTextView);
        lessonTextView = StaticHelpers.initialiseTextView(view, R.id.lessonTextView);
        topicEditText = StaticHelpers.initialiseEditText(view, R.id.topicEditText);
        startMapButton = StaticHelpers.initialiseButton(view, R.id.startMapButton);
    }


}
