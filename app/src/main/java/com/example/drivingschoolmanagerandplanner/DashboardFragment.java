package com.example.drivingschoolmanagerandplanner;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import java.util.Objects;


public class DashboardFragment extends Fragment {

    private ImageView lessonImageView;
    private ImageView studentImageView;
    private ImageView testImageView;
    private ImageView packageImageView;
    private ImageButton testsViewImageButton;
    private ImageButton studentsViewImageButton;
    private ImageButton packagesImageButton;
    private ImageButton lessonsImageButton;

    public DashboardFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
           // mParam1 = getArguments().getString(ARG_PARAM1);
           // mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        initialiseWidgetReferences(view);
        setImageViewButtonsOncLickListeners();

        return view;
    }

    private void initialiseWidgetReferences(View view) {
        studentImageView = (ImageView)view.findViewById(R.id.studentsAddImageButton);
        lessonImageView = (ImageView)view.findViewById(R.id.lessonAddImageButton);
        testImageView = (ImageView)view.findViewById(R.id.testsAddImageButton);
        packageImageView = (ImageView)view.findViewById(R.id.packagesAddImageButton);
        testsViewImageButton = (ImageButton)view.findViewById(R.id.testsViewImageButton);
        packagesImageButton = (ImageButton)view.findViewById(R.id.packagesViewImageButton);
        studentsViewImageButton = (ImageButton)view.findViewById(R.id.studentsViewImageButton);
        lessonsImageButton = (ImageButton)view.findViewById(R.id.lessonViewImageButton);

    }


    private void setImageViewButtonsOncLickListeners() {

        studentImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SetFragment(new StudentFragment());
            }
        });

        lessonImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SetFragment(new LessonFragment());
            }
        });

        testImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SetFragment(new DrivingTestFragment());
            }
        });

        packageImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SetFragment(new PackageFragment());
            }
        });

        testsViewImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // SetListFragment(new ListFragment());
                SetListFragment("tests");
            }
        });

        studentsViewImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // SetListFragment(new ListFragment());
                SetListFragment("students");
            }
        });

        lessonsImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // SetListFragment(new ListFragment());
                SetListFragment("lessons");
            }
        });

        packagesImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // SetListFragment(new ListFragment());
                SetListFragment("packages");
            }
        });
    }


    private void SetFragment(Fragment fragment){

        FragmentManager manager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        fragmentTransaction.replace(R.id.FormsFrameLayout, fragment).addToBackStack(null);
        fragmentTransaction.commit();
    }


    private void SetListFragment(String fragment){
        Intent intent = new Intent( getContext(), ListActivity.class);
        intent.putExtra("STARTACTIVITY", fragment);
        startActivity(intent);
    }

    // idea https://stackoverflow.com/questions/14247954/communicating-between-a-fragment-and-an-activity-best-practices
    public interface UpdateFrag {
        void updatefrag();
    }
    //end idea https://stackoverflow.com/questions/14247954/communicating-between-a-fragment-and-an-activity-best-practices
}
