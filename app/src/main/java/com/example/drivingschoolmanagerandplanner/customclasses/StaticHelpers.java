package com.example.drivingschoolmanagerandplanner.customclasses;

import android.app.Activity;
import android.app.ActivityManager;
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
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.drivingschoolmanagerandplanner.LessonFragment;

import java.util.Objects;

public class StaticHelpers {

    public static Intent LoadActivityWithBundle(Context context, Class classToLoad, Bundle args) {
        //https://www.codexpedia.com/android/passing-data-to-activity-and-fragment-in-android/
        Intent intent = new Intent(context, classToLoad);
        intent.putExtras(args);
        return intent;
    }

    public static long RetrieveIdFromBundle(Intent intent, String key){
      return Objects.requireNonNull(intent.getExtras()).getLong(key);
    }

    public static long[] RetrieveIdsFromBundle(Intent intent, String key, String key2){
        long i = Objects.requireNonNull(intent.getExtras()).getLong(key);
        long j = Objects.requireNonNull(intent.getExtras()).getLong(key2);
        long[] keyValues = {i, j};
        return keyValues;
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

    public static void LoadFragmentWithId(FragmentTransaction transaction, Fragment targetFragment, int resourceId, int id) {
        Bundle args = new Bundle();
        args.putInt("id", id);
        targetFragment.setArguments(args);

//         Replace the fragment_container view with this fragment,
        transaction.replace(resourceId, targetFragment);
        transaction.addToBackStack(null);
        // Commit the transaction
        transaction.commit();
    }

    public static void LoadFragment(FragmentTransaction transaction,  int resourceId, Fragment targetFragment) {
//         Replace the fragment_container view with this fragment,
        transaction.replace(resourceId, targetFragment);
        //transaction.addToBackStack(null);
        // Commit the transaction
        transaction.commit();
    }

    public static Boolean validate(String[] values) {
        for (int i = 0; i < values.length; i++) {
            if (values[i].isEmpty())
                return false;
        }
        return true;
    }

    public static Float tryParse(String text) {
        try {
            return Float.parseFloat(text);
        } catch (NumberFormatException e) {
            return null;
        }
    }


}
