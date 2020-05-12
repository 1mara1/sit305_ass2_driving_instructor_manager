// ListRecordsFragment displays a list based on the provided model class
//
// 1) It used to set the recycler view with the adapter to display the records
// 2) It starts another activity when an item is clicked
// At the moment the class is used by the DashboardFragment receiving the model class

package com.example.drivingschoolmanagerandplanner;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
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
import java.util.Objects;

public class ListRecordsFragment<T> extends Fragment {

    private static final String TAG = "ListFragment" ;
    CustomListAdapter<T> adapter;
    RecyclerView recyclerView;
    TextView listTitleTextView;
    String title;
    T value;

    private ArrayList<T> data; // populate a list of provided model class

    // constructor to create an array based on model class
    public ListRecordsFragment(ArrayList<T> data) {
        // Required empty public constructor
        this.data = data;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // set the title for the next activity loaded
            title = getArguments().getString(DashboardFragment.TITLE);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        listTitleTextView = (TextView) view.findViewById(R.id.listTitleTextView);

        listTitleTextView.setText(title);
        recyclerView = (RecyclerView) view.findViewById(R.id.listRecyclerView);

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
                ItemViewHolder itemHolder = (ItemViewHolder) holder;

                if (val instanceof DrivingTest) {
                    DrivingTest test = (DrivingTest) val;
                    Log.d(TAG, "onBindData:  test" + test.getClass());
                    DbHandler db = DbHelper.getDbHandler(Objects.requireNonNull(getActivity()));
                    // get the student from the lesson id
                    Student st = db.GetStudentById(test.getStudentId());
                    setItemHolders(itemHolder, st.getFullName(), test.getDate(), test.getTime(), test.getLocation());
                }

                if (val instanceof Student) {
                    Student student = (Student) val;
                    Log.d(TAG, "onBindData:  student" + student.getClass());
                    setItemHolders(itemHolder, student.getFullName(), student.getAddressLine(), student.getSuburb(), student.getState());
                }

                if (val instanceof Lesson) {
                    Lesson lesson = (Lesson) val;
                    Log.d(TAG, "onBindData:  student" + lesson.getClass());
                    DbHandler db = DbHelper.getDbHandler(Objects.requireNonNull(getActivity()));
                    Student st = db.GetStudentById(lesson.getStudentId());
                    setItemHolders(itemHolder, st.getFullName(), lesson.getMeetingAddress(), lesson.getStartTime(), lesson.getEndTime());
                }

                if (val instanceof Package) {
                    Package packageLessons = (Package) val;
                    Log.d(TAG, "onBindData:  student" + packageLessons.getClass());
                    setItemHolders(itemHolder, packageLessons.getName()
                            , packageLessons.getStudent().getFullName()
                            , MessageFormat.format("{0}{1}", getString(R.string.lessons), packageLessons.getNumberOfLessons())
                            , String.format("%s%s", getString(R.string.dollar_sign), packageLessons.getAmount()));
                }
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
                super.onBindViewHolder(holder, position);

                View.OnClickListener itemClicked = new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Log.d(TAG, "onClick: ---- " + view.getId() + " position " + position);

                        if (value instanceof Student) {
                            Intent intent = StaticHelpers.LoadActivityWithStudentId(getContext(), StudentDashboardActivity.class,position + 1);
                            Objects.requireNonNull(getContext()).startActivity(intent);
                        }

                        if (value instanceof Lesson)
                           StaticHelpers.LoadFragmentWithStudentId(new LessonFragment(), R.id.FormsFrameLayout, position + 1);


                        if (value instanceof DrivingTest) {
//                         StaticHelpers.LoadFragmentWithStudentId(new LessonFragment(), R.id.FormsFrameLayout, position + 1);
                        }

                        if (value instanceof Package) {
//                          StaticHelpers.LoadFragmentWithStudentId(new LessonFragment(), R.id.FormsFrameLayout, position + 1);
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



}
