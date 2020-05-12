package com.example.drivingschoolmanagerandplanner;

import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.drivingschoolmanagerandplanner.customclasses.StaticHelpers;

import java.util.Calendar;


public class PackageFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARGUMENT_HOUR = "ARGUMENT_HOUR";
    private static final String ARGUMENT_MINUTE = "ARGUMENT_MINUTE";
    private static final String TAG = "PackageFragment" ;

    // TODO: Rename and change types of parameters

     TextView timeTextView;
    EditText timeEditText;
    TimePickerDialog picker;

    public PackageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_package, container, false);
        timeTextView = StaticHelpers.initialiseTextView(view, R.id.timeTextView);
       // timeEditText = (EditText)view.findViewById(R.id.timeEditText);
        timeEditText.setInputType(InputType.TYPE_NULL);
        timeEditText.setOnClickListener(new View.OnClickListener() {
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
                                    timeEditText.setText("Purchased" + sHour + ":" + sMinute);
                                }
                            }, hour, minutes, true);
                    picker.show();
                }
            });

        return view;
    }

//    @Override
//    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
//        getActivity().getMenuInflater().inflate(R.menu.main_menu, menu);
//    }
}
