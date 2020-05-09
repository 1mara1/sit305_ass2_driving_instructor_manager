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

import java.util.Calendar;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LessonFragment#} factory method to
 * create an instance of this fragment.
 */
public class LessonFragment extends Fragment {

    private static final String TAG = "LessonFragment" ;
    TextView timeTextView;
    Button timeStartButton;
    Button timeEndButton;
    TimePickerDialog picker;
//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;

    public LessonFragment() {
        // Required empty public constructor
    }

//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment LessonFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static LessonFragment newInstance(String param1, String param2) {
//        LessonFragment fragment = new LessonFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//



   @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setHasOptionsMenu(true);


        //        ActionBar ab = (getActivity()).getActionBar();
//        ab.setTitle("New Lesson");
//        if (getArguments() != null) {
////            mParam1 = getArguments().getString(ARG_PARAM1);
////            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        final View view =  inflater.inflate(R.layout.fragment_lesson, container, false);

        ActionBar actionBar = getActivity().getActionBar();
        Log.d(TAG, "onCreateView: " + actionBar);

        // Inflate the layout for this fragment

        timeStartButton = (Button)view.findViewById(R.id.timeStartLessonButton);
        timeEndButton = (Button)view.findViewById(R.id.timeEndLessonButton);
        timeStartButton.setInputType(InputType.TYPE_NULL);
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
