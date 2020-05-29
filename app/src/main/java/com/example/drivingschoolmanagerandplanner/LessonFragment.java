package com.example.drivingschoolmanagerandplanner;

import android.app.ActionBar;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.example.drivingschoolmanagerandplanner.customclasses.DbHelper;
import com.example.drivingschoolmanagerandplanner.customclasses.StaticHelpers;
import com.example.drivingschoolmanagerandplanner.data.DbHandler;
import com.example.drivingschoolmanagerandplanner.models.Lesson;
import com.example.drivingschoolmanagerandplanner.models.Student;

import java.util.Calendar;
import java.util.Objects;

public class LessonFragment extends Fragment {

    private static final String TAG = "LessonFragment" ;
    public static final String LESSON_ID = "id" ;
    public static final String STUDENT_ID = "student_id";
    int id;

    Button timeStartButton, timeEndButton, saveLesson, dayLessonButton;
    EditText studentLessonEditText, meetingLocationEditText, amountEditText, commentsEditText ;
    TimePickerDialog picker;
    DatePickerDialog datePicker;


    public LessonFragment() {
        // Required empty public constructor
    }


   @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_lesson, container, false);

        ActionBar actionBar = getActivity().getActionBar();
        Log.d(TAG, "onCreateView: " + actionBar);

        initialiseWidgets(view);


        if (getArguments() != null) {
            if (getArguments().getInt("id") > 0) {

                // we have a value from the studentDashboard
                id = getArguments().getInt("id");

                // Get the student from the database
                Student student = DbHelper.getStudentById(getActivity(), id);

                // populate the student name
                studentLessonEditText.setText(student.getFullName());

                Log.d(TAG, "onCreateView: getArguments() id -> " + id);
            }
        }


        Log.d(TAG, "onCreateView: id returned when student was saved -> " + id);


//        timeStartButton.setInputType(InputType.TYPE_NULL);


        //region onClickListeners
        timeStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetUserTime(timeStartButton, "Start ");
            }
        });

        timeEndButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetUserTime(timeEndButton, "End ");
            }
        });

        dayLessonButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                datePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int mYear, int mMonth, int mDay) {
                        dayLessonButton.setText(mDay + "/" + (mMonth+1) + "/" + mYear);
                    }
                }, year, month, day);
                datePicker.show();
            }
        });

        saveLesson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Lesson l = GetLessonFromInput(view);
                // save the student to database
                GetLessonFromInput(view);

                String[] values = {commentsEditText.getText().toString(),
                        timeStartButton.getText().toString(),
                        timeEndButton.getText().toString(),
                        studentLessonEditText.getText().toString(),
                        meetingLocationEditText.getText().toString(),
                };

                if (StaticHelpers.validate(values)) {
                    if (id > 0) {
                        int rowId = (int) saveLessonToDb(l.getNotes(), l.getAmount(), l.getDay(), l.getStartTime(), l.getEndTime(), l.getMeetingAddress(), l.getIsPackageLesson(), (int) id);
                        StaticHelpers.displayToastMessage(getContext(), "Student Successfully Saved");
                        Bundle args = new Bundle();
                        args.putLong(LESSON_ID, rowId);
                        args.putLong(STUDENT_ID, id);

                        Intent intent = StaticHelpers.LoadActivityWithBundle(getContext(), StudentDashboardActivity.class, args);
                        Objects.requireNonNull(getContext()).startActivity(intent);

                    } else
                        StaticHelpers.displayToastMessage(getContext(), "The student cannot be saved");
                } else
                    StaticHelpers.displayToastMessage(getContext(), "All fields must be filled in");
            }
        });

        //endregion onClickListeners

        return view;

    }

    private void GetUserTime(final Button buttonText, final String startEnd ) {
        //https://www.tutlane.com/tutorial/android/android-timepicker-with-examples
        final Calendar cldr = Calendar.getInstance();
        int hour = cldr.get(Calendar.HOUR_OF_DAY);
        int minutes = cldr.get(Calendar.MINUTE);
        // time picker dialog

        picker = new TimePickerDialog(getActivity(),
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                        String am_pm;
                        if(sHour > 12) {
                            am_pm = "PM";
                            sHour = sHour - 12;
                        }
                        else
                        {
                            am_pm="AM";
                        }
                        buttonText.setText(startEnd + sHour + ":" + sMinute + " "+ am_pm);
                    }
                }, hour, minutes, true);
        picker.show();
    }

    private void initialiseWidgets(View view) {
        dayLessonButton = StaticHelpers.initialiseButton(view, R.id.dayLessonButton);
        studentLessonEditText = StaticHelpers.initialiseEditText(view, R.id.studentLessonEditText);
        meetingLocationEditText = StaticHelpers.initialiseEditText(view, R.id.meetingLocationEditText);
        amountEditText = StaticHelpers.initialiseEditText(view, R.id.performanceRatingBar);
        commentsEditText = StaticHelpers.initialiseEditText(view, R.id.commentsEditText);
        timeStartButton = StaticHelpers.initialiseButton(view, R.id.timeStartLessonButton);
        timeEndButton = StaticHelpers.initialiseButton(view, R.id.timeEndLessonButton);
        saveLesson = StaticHelpers.initialiseButton(view, R.id.saveLesson);
    }

    private Lesson GetLessonFromInput(View view) {
        return new Lesson(
                commentsEditText.getText().toString(),
                StaticHelpers.tryParse(amountEditText.getText().toString()),
                dayLessonButton.getText().toString(),
                timeStartButton.getText().toString(),
                timeEndButton.getText().toString(),
                meetingLocationEditText.getText().toString(),
                0,
                id
        );
    }

    private long saveLessonToDb(String notes, float amount, String day, String startTime, String endTime, String meetingAddress, int isPackageLesson, int studentId) {
        DbHandler dbHandler = new DbHandler(getActivity());
        long rowId = dbHandler.insertLessonDetails(notes, amount, day, startTime, endTime, meetingAddress, isPackageLesson, studentId);
        Log.d(TAG, "getStudentsFromDB: row Id " + rowId);

       // StaticHelpers.

        return rowId;
    }

}
