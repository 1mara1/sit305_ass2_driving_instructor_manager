// ListRecordsFragment displays a list based on the provided model class
//
// 1) It used to set the recycler view with the adapter to display the records
// 2) It starts another activity when an item is clicked
// At the moment the class is used by the DashboardFragment receiving the model class

package com.example.drivingschoolmanagerandplanner;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.drivingschoolmanagerandplanner.customclasses.DbHelper;
import com.example.drivingschoolmanagerandplanner.customclasses.ItemViewHolder;
import com.example.drivingschoolmanagerandplanner.customclasses.CustomListAdapter;
import com.example.drivingschoolmanagerandplanner.customclasses.StaticHelpers;
import com.example.drivingschoolmanagerandplanner.data.DbHandler;
import com.example.drivingschoolmanagerandplanner.models.DrivingTest;
import com.example.drivingschoolmanagerandplanner.models.Lesson;
import com.example.drivingschoolmanagerandplanner.models.Package;
import com.example.drivingschoolmanagerandplanner.models.Student;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class ListItemsFragment<T> extends Fragment {

    public static final String TAG = "ListFragment" ;
    public static final String STUDENT_ID = "student_id";
    public static final String LESSON_ID = "Lesson Id";
    SharedPreferences sharedPref;
    Student student;
    CustomListAdapter<T> adapter;
    private Set<String> ids;
    private Set<String> lessonIds;
    private RecyclerView recyclerView;
    private TextView listTitleTextView;
    private String title;
    private String titleFromStudentDash;
    private T value;
    private ArrayList<T> data; // populate a list of provided model class
    private Set<String> favoritesSet;

    // constructor to create an array based on model class
    public ListItemsFragment(ArrayList<T> data) {
        // Required empty public constructor
        this.data = data;
    }

    public ListItemsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // set the title for the next activity loaded
            title = getArguments().getString(DashboardFragment.TITLE);
            titleFromStudentDash = getArguments().getString(StudentDetailsBottomFragment.TITLE);
        }
    }

//    @Override
//    public void onSaveInstanceState(@NonNull Bundle outState) {
//        super.onSaveInstanceState(outState);
//        outState.putIntArray(STUDENT_ID, ids);
//    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: called");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        listTitleTextView = (TextView) view.findViewById(R.id.listTitleTextView);

        listTitleTextView.setText(title);

        sharedPref = getActivity().getSharedPreferences(STUDENT_ID, Context.MODE_PRIVATE);
        sharedPref = getActivity().getSharedPreferences(LESSON_ID, Context.MODE_PRIVATE);

        recyclerView = (RecyclerView) view.findViewById(R.id.listRecyclerView);

        ids = new HashSet<>();
        lessonIds = new HashSet<>();

        // region Implementing the custom adapter
        // instantiate the adapter and provide the context and data based
        adapter = new CustomListAdapter<T>(getContext(), data) {
            @Override
            public ItemViewHolder setViewHolder(ViewGroup parent) {
                final View view = LayoutInflater.from(getContext()).inflate(R.layout.list_tem_view_holder, parent, false);
                ItemViewHolder viewHolder = new ItemViewHolder(getContext(), view);
                return viewHolder;
            }

            @Override
            public void onBindData(RecyclerView.ViewHolder holder, T val) {
                Log.d(TAG, "onBindData:  val " + val);
                value = val;
//                checkSharedPreferences();
                ItemViewHolder itemHolder = (ItemViewHolder) holder;

                if (val instanceof Student) {
                    Student student = (Student) val;

                    // save the student id so that we can access it later when clicked on the  list item
                    ids.add(String.valueOf(student.getStudentId()));
                    sharedPref.edit().putStringSet(STUDENT_ID, ids);
                    Log.d(TAG, "onBindData: student id: " + student.getStudentId());
                    setItemHolders(itemHolder, student.getFullName(), student.getAddress(), "", "");
                } else if (val instanceof Lesson) {
                    Lesson lesson = (Lesson) val;
                    if (titleFromStudentDash != null) {
                        // we have request from the student dashboard to load the lessons for a specific student

//                        Log.d(TAG, "onBindData:  student" + lesson.getClass());

                        lessonIds.add(String.valueOf(lesson.getLessonId()));
                        sharedPref.edit().putStringSet(LESSON_ID, lessonIds);

//                        DbHandler db = DbHelper.getDbHandler(Objects.requireNonNull(getActivity()));
//                        student = db.GetStudentById(lesson.getStudentId());
                          Student student = DbHelper.getStudentById(getActivity(), lesson.getStudentId());

                        setItemHolders(itemHolder, student.getFullName() , lesson.getStartTime(), lesson.getEndTime(), lesson.getMeetingAddress());
                    } else if (titleFromStudentDash == null) {
                        // we request other than the student dashboard

                        Log.d(TAG, "onBindData:  student" + lesson.getClass());
                        lessonIds.add(String.valueOf(lesson.getLessonId()));
                        sharedPref.edit().putStringSet(LESSON_ID, lessonIds);

                        DbHandler db = DbHelper.getDbHandler(Objects.requireNonNull(getActivity()));
                        Student st = db.GetStudentById(lesson.getStudentId());
                        if (st != null) {
                            // lesson and student exist
                            setItemHolders(itemHolder, st.getFullName(), lesson.getMeetingAddress(), lesson.getStartTime(), lesson.getEndTime());
                        } else {
                            // the student was deleted but not the lesson
                            setItemHolders(itemHolder, "Student was removed", lesson.getMeetingAddress(), lesson.getStartTime(), lesson.getEndTime());
                        }
                    }
                } else {
                    // if we have the id's
                    if(data != null) {
                        String i = val.toString();
                            Integer sId = Integer.valueOf(i);
                            if(sId == Integer.valueOf(i)) {
                                student = DbHelper.getStudentById(getActivity(), sId);
                                //  ids.add(String.valueOf(student.getStudentId()));
                                //    sharedPref.edit().putStringSet(STUDENT_ID, ids);
                                if(student!= null)
                                    setItemHolders(itemHolder, student.getFullName(), student.getAddress(), "", "");
                                else
                                    setItemHolders(itemHolder, "", "", "", "");
                        }
                    }
                }
            }




            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
                super.onBindViewHolder(holder, position);
                checkSharedPreferences();

                // when user clicks on an item
                View.OnClickListener itemClicked = new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Log.d(TAG, "onClick: ---- clicked row id " + view.getId() + "with position " + position);

                        if (value instanceof Student) {
                            // retrieve it from the shared preferences
                            Object[] id = ids.toArray();
                            Log.d(TAG, "onClick: value from set  "+ id[position]);
                            long sId = Long.parseLong((String)id[position]);

                            Bundle args = new Bundle();
                            args.putLong(STUDENT_ID, sId);
                            Intent intent = StaticHelpers.LoadActivityWithBundle(getContext(), StudentDashboardActivity.class, args);
                            Objects.requireNonNull(getContext()).startActivity(intent);
                        }

                        if (value instanceof Lesson) {
                            if (titleFromStudentDash != null) {

                                // retrieve it from the shared preferences
                                Object[] id = lessonIds.toArray();
                                Log.d(TAG, "onClick: value from set  "+ id[position]);
                                int lId = Integer.parseInt((String) id[position]);
                                // we have a request from the the student dashboard when a lesson is clicked
                                // load the lesson planner
                               Lesson les = DbHelper.getLessonById(getActivity(), lId);

                                Bundle args = new Bundle();
                                args.putInt(STUDENT_ID,  les.getStudentId());
                                args.putInt(LESSON_ID,   lId);
                                StaticHelpers.LoadFragmentWithBundle(Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction(), new PlannerFragment(), R.id.studentDetailsTop, args);
                            }else if (titleFromStudentDash == null) {
                                Object[] id = lessonIds.toArray();
                                Log.d(TAG, "onClick: value from set  "+ id[position]);
                                int lId = Integer.parseInt((String) id[position]);
                                Lesson les = DbHelper.getLessonById(getActivity(), lId);

                                Bundle args = new Bundle();
                                args.putInt(STUDENT_ID, les.getStudentId());
                                args.putInt(LESSON_ID, lId);
                                args.putString(TAG, TAG);
                                StaticHelpers.LoadFragmentWithBundle(Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction(), new LessonFragment(), R.id.FormsFrameLayout, args);
                            }
                        }
                    }
                };
                holder.itemView.setOnClickListener(itemClicked);
            }
        };
        recyclerView.setAdapter(adapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        // endregion

        return view;
    }



    // Sets the values for the row
    private void setItemHolders(ItemViewHolder itemHolder, String rightOfImage, String belowImageLeft, String belowImageCentre, String belowImageRight ) {
        itemHolder.rightOfImage.setText(rightOfImage);
        itemHolder.belowImageLeft.setText(belowImageLeft);
        itemHolder.belowImageCentre.setText(belowImageCentre);
        itemHolder.belowImageRight.setText(String.format(belowImageRight));
    }

    public void checkSharedPreferences()
    {
        ids = sharedPref.getStringSet(STUDENT_ID, ids);
    }

}
