package com.example.drivingschoolmanagerandplanner;

import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TimePicker;

import com.example.drivingschoolmanagerandplanner.customclasses.StaticHelpers;

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DrivingTestFragment#} factory method to
 * create an instance of this fragment.
 */
public class DrivingTestFragment extends Fragment {
    EditText timeStartEditText;
    TimePickerDialog picker;

    public DrivingTestFragment() {
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
         final View view = inflater.inflate(R.layout.fragment_driving_test, container, false);

        timeStartEditText = StaticHelpers.initialiseEditText(view, R.id.timeStartTestEditText);
        timeStartEditText.setInputType(InputType.TYPE_NULL);
        timeStartEditText.setOnClickListener(new View.OnClickListener() {
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
                                timeStartEditText.setText("Start:" + sHour + ":" + sMinute);
                            }
                        }, hour, minutes, true);
                picker.show();
            }
        });
         return view;
    }
}
