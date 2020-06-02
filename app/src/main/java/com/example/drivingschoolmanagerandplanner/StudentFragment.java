// Student fragment is used to take student details from input boxes
// saves the details to the database
// loads the student details fragment passing in the row number

package com.example.drivingschoolmanagerandplanner;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.drivingschoolmanagerandplanner.customclasses.DbHelper;
import com.example.drivingschoolmanagerandplanner.customclasses.StaticHelpers;
import com.example.drivingschoolmanagerandplanner.data.DbHandler;
import com.example.drivingschoolmanagerandplanner.models.Student;
import java.util.Objects;

public class StudentFragment extends Fragment {

    private static final String STUDENT_ID = "student_id";
    private Button saveLessonButton;
    private EditText lastnameEditText, firstNameEditText, mobileEditText, emailEditText, addressLineEditText, suburbEditText, stateEditText, postcodeEditText, countryEditText;
    private TextView studentFormTitleTextView;
    private long row;
    private static final String TAG = "StudentFragment";
    private String firstName, lastName, email, addressLine, suburb,state, country, title ;
    private int mobile, postcode;

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
        editStudentDetailsFromActivity();
        setEditText();
        //Log.d(TAG, "onCreateView: phone " + mobile + " email " + email);

        saveLessonButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View view) {
                String[] values = {
                        firstNameEditText.getText().toString(),
                        lastnameEditText.getText().toString(),
                        mobileEditText.getText().toString(),
                        emailEditText.getText().toString(),
                        addressLineEditText.getText().toString(),
                        suburbEditText.getText().toString(),
                        stateEditText.getText().toString(),
                        postcodeEditText.getText().toString(),
                        countryEditText.getText().toString()
                };

                Log.d(TAG, "onClick: trying to save student");

                if (StaticHelpers.validate(values)) {
                    // we have all fields filled in
                    // create a student object from the editText
                    Student s = SaveStudent(view);
                    // save the student to database

                    if(title.contains(getResources().getString(R.id.edit_student)))
                        Log.d(TAG, "onClick: ");

                    if(title.contains("Update Student")){
                        int count  = DbHelper.updateStudent(getActivity(),  s.getFirstName(), s.getLastName(),s.getPhone(), s.getEmail(),s.getAddressLine(),s.getSuburb(),s.getState(),s.getPostcode(), s.getCountry(),(int)row);
                        Log.d(TAG, "onClick: update student "+ count);
                        DisplayMessage("Student updated!" );
                    }
                    else {
                        row = saveStudentToDb(s.getFirstName(), s.getLastName(), s.getPhone(), s.getEmail(), s.getAddressLine(), s.getSuburb(), s.getState(), s.getPostcode(), s.getCountry());
                        DisplayMessage("Student saved!");
                    }

                    Log.d(TAG, "onClick: student row in db " + row);

                    Bundle args = new Bundle(); args.putLong(STUDENT_ID, row);
                    Intent intent = StaticHelpers.LoadActivityWithBundle(getContext(), StudentDashboardActivity.class, args);
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
        studentFormTitleTextView = (TextView) view.findViewById(R.id.studentFormTitleTextView);
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

    private void editStudentDetailsFromActivity(){
        firstName = getArguments().getString(StudentDashboardActivity.FIRST_NAME);
        lastName = getArguments().getString(StudentDashboardActivity.LAST_NAME);
        mobile = getArguments().getInt(StudentDashboardActivity.MOBILE);
        email = getArguments().getString(StudentDashboardActivity.EMAIL);
        addressLine = getArguments().getString(StudentDashboardActivity.ADDRESS_LINE);
        suburb = getArguments().getString(StudentDashboardActivity.SUBURB);
        state = getArguments().getString(StudentDashboardActivity.STATE);
        country = getArguments().getString(StudentDashboardActivity.COUNTRY);
        postcode = getArguments().getInt(StudentDashboardActivity.POSTCODE);
        row =  getArguments().getLong(StudentDashboardActivity.STUDENT_ID);
        title = getArguments().getString(StudentDashboardActivity.TITLE);
    }

    private void setEditText(){
        firstNameEditText.setText(firstName);
        lastnameEditText.setText(lastName);
        mobileEditText.setText(String.valueOf(mobile));
        emailEditText.setText(email);
        addressLineEditText.setText(addressLine);
        suburbEditText.setText(suburb);
        postcodeEditText.setText(String.valueOf(postcode));
        stateEditText.setText(state);
        countryEditText.setText(country);
        studentFormTitleTextView.setText(title);
    }
    // endregion private methods
}
