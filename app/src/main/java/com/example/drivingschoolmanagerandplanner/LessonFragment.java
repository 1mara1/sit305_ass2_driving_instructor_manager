package com.example.drivingschoolmanagerandplanner;

import android.app.ActionBar;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.drivingschoolmanagerandplanner.customclasses.StaticHelpers;

import java.util.Calendar;
import java.util.Objects;

public class LessonFragment extends Fragment {

    private static final String TAG = "LessonFragment" ;

    TextView timeTextView;
    Button timeStartButton;
    Button timeEndButton;
    TimePickerDialog picker;

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
        final View view =  inflater.inflate(R.layout.fragment_lesson, container, false);

        ActionBar actionBar = getActivity().getActionBar();
        Log.d(TAG, "onCreateView: " + actionBar);

        // Inflate the layout for this fragment

        timeStartButton = StaticHelpers.initialiseButton(view, R.id.timeStartLessonButton);
        timeEndButton = StaticHelpers.initialiseButton(view, R.id.timeEndLessonButton);

//        timeStartButton.setInputType(InputType.TYPE_NULL);
        timeStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//https://www.tutlane.com/tutorial/android/android-timepicker-with-examples
                final Calendar cldr = Calendar.getInstance();
                int hour = cldr.get(Calendar.HOUR_OF_DAY);
                int minutes = cldr.get(Calendar.MINUTE);
                // time picker dialog

                picker = new TimePickerDialog(getActivity(),
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                timeStartButton.setText("Start "+ sHour + ":" + sMinute);
                            }
                        }, hour, minutes, true);
                picker.show();
            }
        });

        timeEndButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//https://www.tutlane.com/tutorial/android/android-timepicker-with-examples
                final Calendar cldr = Calendar.getInstance();
                int hour = cldr.get(Calendar.HOUR_OF_DAY);
                int minutes = cldr.get(Calendar.MINUTE);
                // time picker dialog

                picker = new TimePickerDialog(getActivity(),
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                timeEndButton.setText("End "+sHour + ":" + sMinute);
                            }
                        }, hour, minutes, true);
                picker.show();
            }
        });



        return view;

    }
}
