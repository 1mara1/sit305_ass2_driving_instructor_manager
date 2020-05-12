// StudentDetailsTopFragment displays the student details
// Receives the data from other activities and fragments
/* Currently, receiving data from:
        - StudentDashboardActivity
*/
package com.example.drivingschoolmanagerandplanner;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.drivingschoolmanagerandplanner.customclasses.StaticHelpers;

import java.security.Permission;
import java.util.Objects;


public class StudentDetailsTopFragment extends Fragment {

    private static final String TAG = "StudentDetailsTopFragm" ;
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 2;

    TextView fullNameTopTextView, mobileTextView, emailTextView, addressTextView;
    String name, email, address;
    int mobile;

    public StudentDetailsTopFragment() {
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
        View view = inflater.inflate(R.layout.fragment_student_details_top, container, false);

        InitialiseWidgets(view);
        SetStudentDetailsFromActivity();
        setStudentDetailsToTextView();


        mobileTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mobile = (String) mobileTextView.getText();
                MakePhoneCall(mobile);
            }
        });

        emailTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendMessage();
            }
        });
        return view;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: requestCode" + requestCode);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CALL_PHONE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    Log.d(TAG, "onRequestPermissionsResult: permission was granted  " );
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mobile));
                    startActivity(intent);
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

    // region private methods
    private void InitialiseWidgets(View view) {
        fullNameTopTextView = StaticHelpers.initialiseTextView(view, R.id.fullNameTopTextView);
        mobileTextView = StaticHelpers.initialiseTextView(view, R.id.mobileTopTextView);
        emailTextView = StaticHelpers.initialiseTextView(view, R.id.emailTopTextView);
        addressTextView = StaticHelpers.initialiseTextView(view, R.id.addressTopTextView);
    }

    private void SetStudentDetailsFromActivity(){
        name = getArguments().getString(StudentDashboardActivity.FULL_NAME);
        Log.d(TAG, "onCreateView: student name  " + name);
        mobile = getArguments().getInt(StudentDashboardActivity.MOBILE);
        email = getArguments().getString(StudentDashboardActivity.EMAIL);
        address = getArguments().getString(StudentDashboardActivity.ADDRESS);
    }

    private void setStudentDetailsToTextView(){
        fullNameTopTextView.setText(name);
        mobileTextView.setText(String.valueOf(mobile));
        emailTextView.setText(email);
        addressTextView.setText(address);
    }

    private void MakePhoneCall(String mobile) {
        // https://stackoverflow.com/questions/4275678/how-to-make-a-phone-call-using-intent-in-android
        //https://developer.android.com/training/permissions/requesting.html

        if (ContextCompat.checkSelfPermission(Objects.requireNonNull(getActivity()), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            StaticHelpers.displayToastMessage(getContext(), "Making phone calls needs your permission");

            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.CALL_PHONE)) {
                // try to get permission from the user when a dialog box appears
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.CALL_PHONE},
                        MY_PERMISSIONS_REQUEST_CALL_PHONE);

                // MY_PERMISSIONS_REQUEST_CALL_PHONE is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
        else {
            // Permission has already been granted
            Log.d(TAG, "onClick: Permission has already been granted ");
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mobile));
            startActivity(intent);
        }
    }

    private void SendMessage() {
        Log.d(TAG, "onClick: email ");
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Hello ");
        sendIntent.putExtra(Intent.EXTRA_TITLE, "Send message");
        sendIntent.setType("text/plain");

        Intent shareIntent = Intent.createChooser(sendIntent, null);
        startActivity(shareIntent);
    }
    // endregion private methods
}
