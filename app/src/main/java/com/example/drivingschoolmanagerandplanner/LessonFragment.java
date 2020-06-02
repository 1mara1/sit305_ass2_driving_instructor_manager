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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.drivingschoolmanagerandplanner.customclasses.DbHelper;
import com.example.drivingschoolmanagerandplanner.customclasses.StaticHelpers;
import com.example.drivingschoolmanagerandplanner.data.DbHandler;
import com.example.drivingschoolmanagerandplanner.models.Lesson;
import com.example.drivingschoolmanagerandplanner.models.Student;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

public class LessonFragment extends Fragment {

    private static final String TAG = "LessonFragment" ;
    public static final String LESSON_ID = "id" ;
    public static final String STUDENT_ID = "student_id";
    private int lessonId = -1;
    private int studentId = -1; // to populate the lesson record foreign key and display student details
    private int positionFromSpinner = -1;
    private int studentfromLesson;

    private Button timeStartButton, timeEndButton, saveLesson, dayLessonButton;
    private EditText  meetingLocationEditText, amountEditText, commentsEditText ;
    private TextView lessonTitleTextView;
    private TimePickerDialog picker;
    private DatePickerDialog datePicker;
    private Spinner  lessonSpinner;


    public LessonFragment() {
        // Required empty public constructor
    }

   @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Check where the bundle was populated
        if(getArguments() != null){
            if(Objects.equals(getArguments().getString(StudentDashboardActivity.TAG), "StudentDashboardActivi")){
                // from the student dashboard when the user clicks to trigger the lesson.
                studentId = getArguments().getInt(StudentDashboardActivity.STUDENT_ID);
            }else if(Objects.equals(getArguments().getString(ListItemsFragment.TAG), "ListFragment")){
                // from the generic list when student clicks on a record from the lessons
                lessonId = getArguments().getInt(ListItemsFragment.LESSON_ID);
                studentId = getArguments().getInt(ListItemsFragment.STUDENT_ID);
            }
        }

       setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_lesson, container, false);

        ActionBar actionBar = Objects.requireNonNull(getActivity()).getActionBar();
        Log.d(TAG, "onCreateView: " + actionBar);

        lessonSpinner = (Spinner)view.findViewById(R.id.lessonSpinner);
        initialiseWidgets(view);

        final ArrayList<String> names = new ArrayList<>();

        if(lessonId > -1){
            // we have a request for a lesson details
            Lesson lesson = DbHelper.getLessonById(getActivity(), lessonId);
            if(lesson.getStudentId() > -1) {
                // there is a student for this lesson
                Student student = DbHelper.getStudentById(getActivity(), lesson.getStudentId());
                if (student != null) {
                    names.add(student.getFullName());
                }
                else {
                    // load the spinner with all the student since there is none for this lesson
                    ArrayList<Student> students = DbHelper.getStudents(getActivity());
                    int size = DbHelper.getStudents(getActivity()).size();
                    names.add("--Select --"); //  first item since there is no student for the lesson
                    // we populate all the available students
                    for (int i = 0; i < size; i++) {
                        names.add(students.get(i).getFullName());
                    }
                }
            }

            SetlessonDetails(lesson);

            lessonTitleTextView.setText("Update Lesson");


        }else if(studentId >= 0) {
            // we have student already
            Student student = DbHelper.getStudentById(getActivity(), studentId);
            // populate the student name
            names.add(student.getFullName());
            Log.d(TAG, "onCreateView: getArguments() id from activity-> " + studentId);
        }
        else if(studentId == -1) {
            // we do not have a student selection from an activity
            // get all students from the database
            ArrayList<Student> students = DbHelper.getStudents(getActivity());
            int size = DbHelper.getStudents(getActivity()).size();

            // we populate all the available students
            for(int i = 0; i < size; i++) {
                names.add(students.get(i).getFullName());
            }
        }

        // create an adapter to display the student names
        ArrayAdapter<String> ad = new ArrayAdapter<>(getActivity(), R.layout.support_simple_spinner_dropdown_item, names);
        lessonSpinner.setAdapter(ad);


        //region Listeners

        lessonSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                // we can set the student id from the position the item was selected
                 positionFromSpinner = position;
                //StaticHelpers.displayToastMessage(getContext(), "int position " + position );
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

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
                datePicker = new DatePickerDialog(Objects.requireNonNull(getActivity()), new DatePickerDialog.OnDateSetListener() {
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
                //  GetLessonFromInput(view);
                String name = "";
                if(studentId != -1) {
                    // it always has one item since we know the student
                    name = names.get(0); // the student name
                }

                if(lessonId != -1){
                    name = names.get(positionFromSpinner); // the student name
                }

                if(positionFromSpinner >= 0) {
                    name = names.get((int) positionFromSpinner); // the student name
                }

                String[] values = {commentsEditText.getText().toString(),
                        timeStartButton.getText().toString(),
                        timeEndButton.getText().toString(),
                        name,
                        meetingLocationEditText.getText().toString(),
                };

                if (StaticHelpers.validate(values)) {
                    if (studentId != -1 && lessonTitleTextView.getText() != "Update Lesson") {
                        int rowId = (int) saveLessonToDb(l.getNotes(), l.getAmount(), l.getDay(), l.getStartTime(), l.getEndTime(), l.getMeetingAddress(), l.getIsPackageLesson(), (int) studentId);
                        StaticHelpers.displayToastMessage(getContext(), "Lesson Successfully Saved");
                        Bundle args = new Bundle();
                        args.putLong(LESSON_ID, rowId);
                        args.putLong(STUDENT_ID, studentId);

                        Intent intent = StaticHelpers.LoadActivityWithBundle(getContext(), StudentDashboardActivity.class, args);
                        Objects.requireNonNull(getContext()).startActivity(intent);

                    } else if(lessonTitleTextView.getText() != "Update Lesson"){
                        // we are saving a new lesson
                        int rowId = (int) saveLessonToDb(l.getNotes(), l.getAmount(), l.getDay(), l.getStartTime(), l.getEndTime(), l.getMeetingAddress(), l.getIsPackageLesson(), (int) studentId);
                        StaticHelpers.displayToastMessage(getContext(), "Lesson Successfully Saved");
                        Bundle args = new Bundle();
                        args.putLong(LESSON_ID, rowId);
                        args.putLong(STUDENT_ID, positionFromSpinner + 1);

                        Intent intent = StaticHelpers.LoadActivityWithBundle(getContext(), MainActivity.class, args);
                        Objects.requireNonNull(getContext()).startActivity(intent);

                    }

                    if(lessonTitleTextView.getText() == "Update Lesson"){
                        // we saving an updated lesson
                        if(studentfromLesson >= 0 ) {
                            if(positionFromSpinner!=0)
                                updateLessonToDb(l.getNotes(), l.getAmount(), l.getDay(), l.getStartTime(), l.getEndTime(), l.getMeetingAddress(), l.getIsPackageLesson(), positionFromSpinner, lessonId);
                            else
                                updateLessonToDb(l.getNotes(), l.getAmount(), l.getDay(), l.getStartTime(), l.getEndTime(), l.getMeetingAddress(), l.getIsPackageLesson(), studentId, lessonId);

                            StaticHelpers.displayToastMessage(getContext(), "Lesson Updated Successfully");
                            StaticHelpers.LoadFragment(getParentFragmentManager().beginTransaction(), R.id.FormsFrameLayout, new DashboardFragment());
                        }else
                        {
                            StaticHelpers.displayToastMessage(getContext(), "Lesson was Not Saved");
                        }

                    }

                }   else
                    StaticHelpers.displayToastMessage(getContext(), "All fields must be filled in");
        }});

        //endregion onClickListeners

        return view;

    }

    private void updateLessonToDb(String notes, float amount, String day, String startTime, String endTime, String meetingAddress, int isPackageLesson, int studentId, int lessonId) {
        DbHandler dbHandler = new DbHandler(getActivity());
       dbHandler.updateLessonDetails(notes, amount, day, startTime, endTime, meetingAddress, isPackageLesson, studentId, lessonId);
    }

    private void SetlessonDetails(Lesson lesson) {
        timeStartButton.setText(lesson.getStartTime());
        timeEndButton.setText(lesson.getEndTime());
        dayLessonButton.setText(lesson.getDay());
        amountEditText.setText(String.valueOf(lesson.getAmount()));
        commentsEditText.setText(lesson.getNotes());
        meetingLocationEditText.setText(lesson.getMeetingAddress());
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
        meetingLocationEditText = StaticHelpers.initialiseEditText(view, R.id.meetingLocationEditText);
        amountEditText = StaticHelpers.initialiseEditText(view, R.id.performanceRatingBar);
        commentsEditText = StaticHelpers.initialiseEditText(view, R.id.commentsEditText);
        timeStartButton = StaticHelpers.initialiseButton(view, R.id.timeStartLessonButton);
        timeEndButton = StaticHelpers.initialiseButton(view, R.id.timeEndLessonButton);
        saveLesson = StaticHelpers.initialiseButton(view, R.id.saveLesson);
        lessonTitleTextView = StaticHelpers.initialiseTextView(view, R.id.lessonTitleTextView);
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
                (int)studentId
        );
    }

    private long saveLessonToDb(String notes, float amount, String day, String startTime, String endTime, String meetingAddress, int isPackageLesson, int studentId) {
        DbHandler dbHandler = new DbHandler(getActivity());
        long rowId = dbHandler.insertLessonDetails(notes, amount, day, startTime, endTime, meetingAddress, isPackageLesson, studentId);
        Log.d(TAG, "getStudentsFromDB: row Id " + rowId);
        return rowId;
    }
}
