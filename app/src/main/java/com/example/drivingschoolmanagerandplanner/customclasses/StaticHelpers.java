package com.example.drivingschoolmanagerandplanner.customclasses;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.Objects;

public class StaticHelpers {

    public static Intent LoadActivityWithStudentId(Context context, Class classToLoad, long id) {
        //https://www.codexpedia.com/android/passing-data-to-activity-and-fragment-in-android/
        Intent intent = new Intent(context, classToLoad);
        Bundle args = new Bundle();
        args.putLong("studentId", id);
        intent.putExtras(args);
        return intent;
    }

    public static long RetrieveStudentIdFromBundle(Intent intent){
      return Objects.requireNonNull(intent.getExtras()).getLong("studentId");
    }


    public static TextView initialiseTextView(View view, int resourceId){
        return (TextView)view.findViewById(resourceId);
    }

    public static ImageButton initialiseImageButton(View view, int resourceId){
        return (ImageButton)view.findViewById(resourceId);
    }

    public static EditText initialiseEditText(View view, int resourceId){
        return (EditText) view.findViewById(resourceId);
    }

    public static Button initialiseButton(View view, int resourceId){
        return (Button) view.findViewById(resourceId);
    }

    public static void displayToastMessage(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_LONG ).show();
    }

    public static void LoadFragmentWithStudentId(Fragment fragment, int resourceId, int id) {
        Bundle args = new Bundle();
        args.putLong("studentId", id);
        fragment.setArguments(args);
        FragmentManager manager = Objects.requireNonNull(fragment.getActivity()).getSupportFragmentManager();
        FragmentTransaction transaction =manager.beginTransaction();

//             Replace the fragment_container view with this fragment,
        transaction.replace(resourceId, fragment);
//        transaction.addToBackStack(null);
//         Commit the transaction
        transaction.commit();
    }


    public static Integer tryParse(String text) {
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
