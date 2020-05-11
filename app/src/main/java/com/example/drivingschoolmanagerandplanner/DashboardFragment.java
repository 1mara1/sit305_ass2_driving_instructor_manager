package com.example.drivingschoolmanagerandplanner;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.drivingschoolmanagerandplanner.data.DbHandler;
import com.example.drivingschoolmanagerandplanner.models.DrivingTest;
import com.example.drivingschoolmanagerandplanner.models.Lesson;
import com.example.drivingschoolmanagerandplanner.models.Package;
import com.example.drivingschoolmanagerandplanner.models.Student;

import java.util.ArrayList;
import java.util.Objects;


public class DashboardFragment extends Fragment {

    private static final String TAG = "DashboardFragment";
    private ImageView lessonImageView;
    private ImageView studentImageView;
    private ImageView testImageView;
    private ImageView packageImageView;
    private ImageButton testsViewImageButton;
    private ImageButton studentsViewImageButton;
    private ImageButton packagesImageButton;
    private ImageButton lessonsImageButton;


    final ArrayList<DrivingTest> tests = new ArrayList<>();
  //  final ArrayList<Student> students = new ArrayList<>();
    final ArrayList<Package> packages = new ArrayList<>();
    final ArrayList<Lesson> lessons = new ArrayList<>();

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
        studentsViewImageButton = (ImageButton)view.findViewById(R.id.lessonListForStudentImageButton);
        lessonsImageButton = (ImageButton)view.findViewById(R.id.lessonViewImageButton);

    }


    private void setImageViewButtonsOncLickListeners() {

        // Form fragments
        studentImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SetFragment(new StudentFragment(),"New Student");
            }
        });

        lessonImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SetFragment(new LessonFragment(),"New Lesson");
            }
        });

        testImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SetFragment(new DrivingTestFragment(),"New Driving Test");
            }
        });

        packageImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SetFragment(new PackageFragment(),"New Package");
            }
        });

        // Lists fragments
        testsViewImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SetFragment(new ListRecordsFragment<DrivingTest>(getTestsFromDB()), "Driving Tests");
            }
        });

        studentsViewImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // SetListFragment(new ListFragment());
                SetFragment(new ListRecordsFragment<Student>(getStudentsFromDB()),"Students");
            }
        });

        lessonsImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SetFragment(new ListRecordsFragment<Lesson>(getLessonsFromDB()),"Lessons");
//                SetListFragment("lessons");
            }
        });

        packagesImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             //   SetFragment(new ListRecordsFragment<Package>(getPackagesFromDB()));
               // SetListFragment("packages");
            }
        });
    }


    private void SetFragment(Fragment fragment, String title){
        Bundle args = new Bundle();

        FragmentManager manager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        fragmentTransaction.replace(R.id.FormsFrameLayout, fragment).addToBackStack(null);

        args.putString("title", title);
        fragment.setArguments(args);

        fragmentTransaction.commit();
    }

//
//    private void SetListFragment(String fragment){
//        Intent intent = new Intent( getContext(), ListActivity.class);
//        intent.putExtra("STARTACTIVITY", fragment);
//        startActivity(intent);
//    }

    // idea https://stackoverflow.com/questions/14247954/communicating-between-a-fragment-and-an-activity-best-practices
    public interface UpdateFrag {
        void updatefrag();
    }
    //end idea https://stackoverflow.com/questions/14247954/communicating-between-a-fragment-and-an-activity-best-practices

    private ArrayList<Student> getStudentsFromDB(){

       // SaveStudent();

        DbHandler db = new DbHandler(getActivity());
        ArrayList<Student> userList = db.GetStudents();
        Log.d(TAG, "getStudentsFromDB: number of students " + userList.size());


        db.close();
        return  userList;
    }

    private ArrayList<Lesson> getLessonsFromDB(){

//            DbHandler dbHandler = new DbHandler(getActivity());
//            long rowId =   dbHandler.InsertLessonDetails("Meet",65.50F, "2:30 PM", "3:30PM", "105 Sary St Liveprool", 0, 3);
//            Log.d(TAG, "getStudentsFromDB: row Id " + rowId);
//            dbHandler.close();


        DbHandler db = new DbHandler(getActivity());
        ArrayList<Lesson> lessons = db.GetLessons();
        Log.d(TAG, "getStudentsFromDB: number of lessons " + lessons.size());
        db.close();
        return  lessons;
    }

//    private ArrayList<Package> getPackagesFromDB(){
//        DbHandler db = new DbHandler(getActivity());
//        ArrayList<Package> packages = db.GetPackages();
//        Log.d(TAG, "getStudentsFromDB: number of packages " + packages.size());
//        db.close();
//        return  packages;
//    }
//
    private ArrayList<DrivingTest> getTestsFromDB(){

//                  DbHandler dbHandler = new DbHandler(getActivity());
//            long rowId =   dbHandler.InsertTestDetails("Liverpool","12/05/2015", "2:30 PM", "352635",  0, 3);
//            Log.d(TAG, "getStudentsFromDB: getTestsFromDB row Id " + rowId);
//            dbHandler.close();

        DbHandler db = new DbHandler(getActivity());
        ArrayList<DrivingTest> tests = db.GetDrivingTests();
        Log.d(TAG, "getStudentsFromDB: number of tests " + tests.size());
        db.close();
        return  tests;
    }


    private void SaveStudent(){
        DbHandler dbHandler = new DbHandler(getActivity());
        long rowId =   dbHandler.InsertStudentDetails("Deni","Kros",043616522 , "denicross@gmail.com","5 Smith St.","Fairfield", "NSW", 2120, "AU");
        Log.d(TAG, "getStudentsFromDB: row Id " + rowId);
        dbHandler.close();
    }
}
