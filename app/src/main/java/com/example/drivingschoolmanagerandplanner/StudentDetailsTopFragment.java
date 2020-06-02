// StudentDetailsTopFragment displays the student details
// Receives the data from other activities and fragments
/* Currently, receiving data from:
        - StudentDashboardActivity
*/
package com.example.drivingschoolmanagerandplanner;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.drivingschoolmanagerandplanner.customclasses.DbHelper;
import com.example.drivingschoolmanagerandplanner.customclasses.StaticHelpers;
import com.example.drivingschoolmanagerandplanner.models.Student;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import java.util.prefs.PreferencesFactory;


public class StudentDetailsTopFragment extends Fragment {

    // region Declarations

    private static final String TAG = "StudentDetailsTopFragm";
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 2;
    private static final String STATE = "State";
    static final String FAVORITES = "Favorites";
    private ToggleButton favouriteToggleButton;
    private TextView fullNameTopTextView, mobileTextView, emailTextView, addressTextView;
    private String name, email, address;
    private int mobile;
    private long studentId;
    SharedPreferences sharedPreferences;
    Set<String> favorites; // for the shared pref to store the values
    //endregion Declarations

    // Constructor
    public StudentDetailsTopFragment() {
        // Required empty public constructor
    }

    // region Overrides

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_student_details_top, container, false);
        favouriteToggleButton = (ToggleButton)view.findViewById(R.id.favouriteToggleButton);
        favorites = new HashSet<String>();
        //sharedPreferences = getActivity().getSharedPreferences("Favourite", Context.MODE_PRIVATE);

        SetStudentDetailsFromActivity();
        InitialiseWidgets(view);
        setStudentDetailsToTextView();
        readState();
    //   favorites.clear();
    //saveFavorites();

Boolean contain =false;
        if(favorites != null) {
            if (favorites.size() > 0) {
                contain = true;
                for (String id : favorites) {
                    Integer idP = Integer.parseInt(id);

                    //  String i = String.valueOf(s);
//                        if (id.contains(i)) {
                    // favouriteToggleButton.setChecked(true);


                    if (idP.equals(Integer.valueOf((String.valueOf(studentId))))) {
                        favouriteToggleButton.setBackgroundDrawable(ContextCompat.getDrawable(Objects.requireNonNull(getContext()), R.drawable.favorited));
                        break;
                    }
                }
                if (!contain)
                    favorites.clear();
            } else {
                favouriteToggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.favorite));
                //   favouriteToggleButton.setChecked(false);
            }
        }

        //https://stackoverflow.com/questions/34980309/favourite-button-android

//        favouriteToggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.favorite));
        favouriteToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    favouriteToggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.favorited));
                    favorites.add(String.valueOf(studentId));
                    saveFavorites();
                } else {
                    favouriteToggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.favorite));

                    for (String id : favorites) {
                        String i = String.valueOf(studentId);
                        if(id.contains(i)){
                            favorites.remove(i);
                            saveFavorites();
                        }
                    }
                }
            }
        });

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

        OpenMap(addressTextView.getText().toString());

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
                    Log.d(TAG, "onRequestPermissionsResult: permission was granted  ");
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

    // endregion overrides

    // region private methods
    private void InitialiseWidgets(View view) {
        fullNameTopTextView = StaticHelpers.initialiseTextView(view, R.id.fullNameTopTextView);
        mobileTextView = StaticHelpers.initialiseTextView(view, R.id.mobileTopTextView);
        emailTextView = StaticHelpers.initialiseTextView(view, R.id.emailTopTextView);
        addressTextView = StaticHelpers.initialiseTextView(view, R.id.addressTopTextView);
    }

    private void SetStudentDetailsFromActivity() {
        name = getArguments().getString(StudentDashboardActivity.FULL_NAME);
        Log.d(TAG, "onCreateView: student name  " + name);
        mobile = getArguments().getInt(StudentDashboardActivity.MOBILE);
        email = getArguments().getString(StudentDashboardActivity.EMAIL);
        address = getArguments().getString(StudentDashboardActivity.ADDRESS);
        studentId = getArguments().getLong(StudentDashboardActivity.STUDENT_ID);

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

    private void OpenMap(final String address) {
        addressTextView.setOnClickListener(new View.OnClickListener() {
            String studentAddress = address;
            @Override
            public void onClick(View view) {
                //code based at https://stackoverflow.com/questions/3574644/how-can-i-find-the-latitude-and-longitude-from-address
                Intent searchAddress = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=" + studentAddress));
                startActivity(searchAddress);
                // end idea how-can-i-find-the-latitude-and-longitude-from-address
            }
        });
    }

    // idea based at https://stackoverflow.com/questions/6186123/android-how-do-i-get-sharedpreferences-from-another-activity
    SharedPreferences preferences;
    private void readState() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        favorites = preferences.getStringSet(FAVORITES, favorites);
    }

    private void saveFavorites(){
        preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putStringSet(FAVORITES, favorites).apply();
        //sharedPreferences.edit().putStringSet("Favorites", favorites).apply();
    }

    // end idea https://stackoverflow.com/questions/6186123/android-how-do-i-get-sharedpreferences-from-another-activity

    // endregion private methods
}
