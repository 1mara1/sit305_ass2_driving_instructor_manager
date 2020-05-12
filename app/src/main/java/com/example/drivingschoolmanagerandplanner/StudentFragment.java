// Student fragment is used to take student details from input boxes
// saves the details to the database
// loads the student details fragment passing in the row number

package com.example.drivingschoolmanagerandplanner;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.drivingschoolmanagerandplanner.customclasses.StaticHelpers;
import com.example.drivingschoolmanagerandplanner.data.DbHandler;
import com.example.drivingschoolmanagerandplanner.models.Student;

import java.util.Objects;


///**
// * A simple {@link Fragment} subclass.
// * Use the {@link StudentFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
public class StudentFragment extends Fragment {


    Button saveLessonButton;
    EditText lastnameEditText, firstNameEditText, mobileEditText, emailEditText, addressLineEditText, suburbEditText, stateEditText, postcodeEditText, countryEditText;
    long row;

    private static final String TAG = "StudentFragment";

    public StudentFragment() {
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
        final View view = inflater.inflate(R.layout.fragment_student, container, false);
        InitialiseEditTexts(view);

        saveLessonButton = (Button) view.findViewById(R.id.saveLessonButton);
        saveLessonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d(TAG, "onClick: trying to save student");

                if (Validate()) {
                    // we have all fields filled in
                    // create a student object from the editText
                    Student s = SaveStudent(view);
                    // save the student to database
                    row = saveStudentToDb(s.getFirstName(), s.getLastName(), s.getPhone(), s.getEmail(), s.getAddressLine(), s.getSuburb(), s.getState(), s.getPostcode(), s.getCountry());
                    DisplayMessage("Student saved!");

                    Log.d(TAG, "onClick: student row in db " + row);

                    Intent intent = StaticHelpers.LoadActivityWithStudentId(getContext(), StudentDashboardActivity.class, row);
                    Objects.requireNonNull(getContext()).startActivity(intent);

                    return;
                }
                DisplayMessage("Please fill in all fields!");
            }
        });
        return view;
    }



    // region private methods
    private void InitialiseEditTexts(View view) {
        firstNameEditText = (EditText) view.findViewById(R.id.firstNameEditText);
        lastnameEditText = (EditText) view.findViewById(R.id.lastnameEditText);
        mobileEditText = (EditText) view.findViewById(R.id.mobileEditText);
        emailEditText = (EditText) view.findViewById(R.id.emailEditText);
        addressLineEditText = (EditText) view.findViewById(R.id.addressLineEditText);
        suburbEditText = (EditText) view.findViewById(R.id.suburbEditText);
        stateEditText = (EditText) view.findViewById(R.id.stateEditText);
        postcodeEditText = (EditText) view.findViewById(R.id.postcodeEditText);
        countryEditText = (EditText) view.findViewById(R.id.countryEditText);
    }


    private Student SaveStudent(View view) {
        return new Student(
                firstNameEditText.getText().toString(),
                lastnameEditText.getText().toString(),
                Integer.parseInt(mobileEditText.getText().toString()),
                emailEditText.getText().toString(),
                addressLineEditText.getText().toString(),
                suburbEditText.getText().toString(),
                stateEditText.getText().toString(),
                Integer.parseInt(postcodeEditText.getText().toString()),
                countryEditText.getText().toString()
        );
    }


    private long saveStudentToDb(String firstName, String lastName, int phone, String email, String addressLine, String suburb, String state, int postcode, String country) {
        DbHandler dbHandler = new DbHandler(getActivity());
        long rowId = dbHandler.insertStudentDetails(firstName, lastName, phone, email, addressLine, suburb, state, postcode, country);
        Log.d(TAG, "getStudentsFromDB: row Id " + rowId);

        return rowId;
    }


    private void DisplayMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }


    private Boolean Validate() {

        String[] values = {firstNameEditText.getText().toString(),
                lastnameEditText.getText().toString(),
                mobileEditText.getText().toString(),
                emailEditText.getText().toString(),
                addressLineEditText.getText().toString(),
                suburbEditText.getText().toString(),
                stateEditText.getText().toString(),
                postcodeEditText.getText().toString(),
                countryEditText.getText().toString()
        };

        for (int i = 0; i < values.length; i++) {
            if (values[i].isEmpty())
                return false;
        }
        return true;
    }
    // endregion private methods
}
