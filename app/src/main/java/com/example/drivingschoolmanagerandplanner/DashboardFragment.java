// DashboardFragment and also the main entry to the application
// The fragment is used to display main information and navigate to other screens

package com.example.drivingschoolmanagerandplanner;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.drivingschoolmanagerandplanner.customclasses.DbHelper;
import com.example.drivingschoolmanagerandplanner.customclasses.StaticHelpers;
import com.example.drivingschoolmanagerandplanner.data.DbHandler;
import com.example.drivingschoolmanagerandplanner.models.Lesson;
import com.example.drivingschoolmanagerandplanner.models.Student;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.xml.sax.DTDHandler;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Set;


public class DashboardFragment extends Fragment {

    private static final String TAG = "DashboardFragment";
    public static final String TITLE = "title";
    private ArrayList<Student> students;
    private ArrayList<Lesson> lessons;
    private ImageButton lessonAddButton, studentAddButton, studentsListButton,  lessonsListButton;
    private TextView studentTotalTextView, lessonsTotalTextView;
    private BottomNavigationView bac;
    private Set<String> favorites;

    DashboardFragment() {
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
        final View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        bac = (BottomNavigationView) view.findViewById(R.id.bac);

        InitialiseDataList(view);
        initialiseWidgetReferences(view);
        setImageViewButtonsOncLickListeners();
        setTotals();

        setNavigationItemListener();

        return view;
    }

    // region private methods

    private void setNavigationItemListener() {
        bac.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // id: 2131231037 Favorites
                // id  2131231036 Add Student
                // id: 2131231035 Add Lesson

                Log.d(TAG, "onNavigationItemSelected: item id " + item.getItemId());

                if(item.getItemId() == 2131231037){
                    // favourites
                    // retrieve the favorite from the shared preferences
                    // Instantiate the adapter for listview
                    Log.d(TAG, "onNavigationItemSelected: favorite ");
                    favorites = readFavorites();

                    if(favorites != null) {
                        ArrayList<String> str = new ArrayList<>();
                        if(favorites.size()>0) {
                            Log.d(TAG, "onNavigationItemSelected: favorites " + favorites.size());
                            for (String s : favorites) {
                                str.add(s);
                            }
                            StaticHelpers.setFragment(new ListItemsFragment<String>(str), getFragmentManger(), TITLE, "Favorites");
                        }else{
                            StaticHelpers.displayToastMessage(getContext(), "There is nothing to display.");
                        }
                    }else{
                        StaticHelpers.displayToastMessage(getContext(), "There is nothing to display.");
                    }
                }

                if (item.getItemId() == 2131231036){
                    // add student
                    StaticHelpers.setFragment(new StudentFragment(), getFragmentManger(), TITLE,"New Student");
                    Log.d(TAG, "onNavigationItemSelected: add student");
                }

                if(item.getItemId() == 2131231035) {
                    StaticHelpers.setFragment(new LessonFragment(), getFragmentManger(), TITLE, "New Lesson");
                    Log.d(TAG, "onNavigationItemSelected: New Lesson");
                }

                Log.d(TAG, "onNavigationItemSelected:  item.getItemId() " + item.getItemId());
                //StaticHelpers.displayToastMessage(getContext(), " " + String.valueOf(item.getItemId()));
                return false;
            }
        });
    }

    private void InitialiseDataList(View view){
        students = DbHelper.getStudents((Activity) getContext());
        lessons = DbHelper.getLessons((Activity) getContext());
    }

    private void initialiseWidgetReferences(View view) {
        studentAddButton = StaticHelpers.initialiseImageButton(view, R.id.studentsAddImageButton);
        lessonAddButton = StaticHelpers.initialiseImageButton(view, R.id.lessonAddImageButton);
        studentsListButton = StaticHelpers.initialiseImageButton(view, R.id.numberLessonsTextView);
        lessonsListButton = StaticHelpers.initialiseImageButton(view, R.id.lessonViewImageButton);
        studentTotalTextView = StaticHelpers.initialiseTextView(view, R.id.studentTotalTextView);
        lessonsTotalTextView = StaticHelpers.initialiseTextView(view, R.id.lessonsTotalTextView);
    }

    private void setImageViewButtonsOncLickListeners() {

        // Form fragments
        studentAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                StaticHelpers.setFragment(new StudentFragment(), getFragmentManger(), TITLE, "New Student");
            }
        });

        lessonAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               StaticHelpers.setFragment(new LessonFragment(), getFragmentManger(), TITLE, "New Lesson");
            }
        });

        studentsListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StaticHelpers.setFragment(new ListItemsFragment<Student>(students), getFragmentManger(), TITLE,"Students");
            }
        });

        lessonsListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StaticHelpers.setFragment(new ListItemsFragment<Lesson>(lessons),getFragmentManger(), TITLE,"Lessons");
            }
        });


    }

    private FragmentManager getFragmentManger(){
        return  Objects.requireNonNull(getActivity()).getSupportFragmentManager();
    }

    private void setTotals(){
        studentTotalTextView.setText(String.valueOf(students.size()));
        lessonsTotalTextView.setText(String.valueOf(lessons.size()));
    }

    private Set<String> readFavorites() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        return  preferences.getStringSet(StudentDetailsTopFragment.FAVORITES, favorites);
    }

    //endregion private methods
}