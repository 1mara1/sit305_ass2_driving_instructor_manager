package com.example.drivingschoolmanagerandplanner;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.drivingschoolmanagerandplanner.customclasses.ItemViewHolder;
import com.example.drivingschoolmanagerandplanner.customclasses.CustomListAdapter;
import com.example.drivingschoolmanagerandplanner.data.DbHandler;
import com.example.drivingschoolmanagerandplanner.models.DrivingTest;
import com.example.drivingschoolmanagerandplanner.models.Lesson;
import com.example.drivingschoolmanagerandplanner.models.Package;
import com.example.drivingschoolmanagerandplanner.models.Student;

import java.text.MessageFormat;
import java.text.ParseException;
import java.util.ArrayList;


///**
// * A simple {@link Fragment} subclass.
// * Use the {@link ListRecordsFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
public class ListRecordsFragment<T> extends Fragment {

    private static final String TAG = "ListFragment" ;
    CustomListAdapter<T> adapter;
    RecyclerView recyclerView;
    TextView listTitleTextView;
    String title;

//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
    private ArrayList<T> tests;

    public ListRecordsFragment(ArrayList<T> tests) {
        // Required empty public constructor
        this.tests = tests;
    }

//    public ListRecordsFragment() {
//        // Required empty public constructor
//    }
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment ListFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static ListRecordsFragment newInstance(String param1, String param2) {
//        ListRecordsFragment fragment = new ListRecordsFragment();
////        Bundle args = new Bundle();
////        args.putString(ARG_PARAM1, param1);
////        args.putString(ARG_PARAM2, param2);
////        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            title = getArguments().getString("title");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        listTitleTextView = (TextView)view.findViewById(R.id.listTitleTextView);

        listTitleTextView.setText(title);
        recyclerView = (RecyclerView) view.findViewById(R.id.listRecyclerView);
        adapter  = new CustomListAdapter<T>(getContext(), tests ) {

            Student student;
            ArrayList<DrivingTest> test;



            @Override
            public ItemViewHolder setViewHolder(ViewGroup parent) {
                final View view = LayoutInflater.from(getContext()).inflate(R.layout.list_tem_view_holder, parent, false);
                ItemViewHolder viewHolder = new ItemViewHolder(getContext(), view);
                return viewHolder;
            }

            @Override
            public void onBindData(RecyclerView.ViewHolder holder, T val) {
                Log.d(TAG, "onBindData:  val " + val);

                ItemViewHolder itemHolder = (ItemViewHolder)holder;

                if(val instanceof DrivingTest) {


                    DrivingTest test = (DrivingTest) val;
                    Log.d(TAG, "onBindData:  test" + test.getClass() );
                    DbHandler db = new DbHandler(getActivity());
                    // get the student from the lesson id
                    Student st = db.GetStudentById(test.getStudentId());
//                    Student st = db.GetStudentById(1);
                    itemHolder.rightOfImage.setText(st.getFullName());
                    itemHolder.belowImageLeft.setText(test.getDate().toString());
                    itemHolder.belowImageCentre.setText(test.getTime().toString());
                    itemHolder.belowImageRight.setText(test.getLocation());
                }

                if(val instanceof Student){
                    Student student = (Student) val;
                    Log.d(TAG, "onBindData:  student" + student.getClass() );
                    itemHolder.rightOfImage.setText(student.getFullName());
                    itemHolder.belowImageLeft.setText(student.getAddressLine());
                    itemHolder.belowImageCentre.setText(student.getSuburb());
                    itemHolder.belowImageRight.setText(student.getState());
                }

                if(val instanceof Lesson){
                    Lesson lesson = (Lesson) val;
                    Log.d(TAG, "onBindData:  student" + lesson.getClass() );
//                    Student student = new Student();
                    DbHandler db = new DbHandler(getActivity());
                    // get the student from the lesson id
                    Student st = db.GetStudentById(lesson.getStudentId());
//                    Student st = db.GetStudentById(1);

                  //  student.setFullName(st.getFirstName(), st.getAddressLine());
                    itemHolder.rightOfImage.setText(st.getFullName());
                    itemHolder.belowImageLeft.setText(lesson.getMeetingAddress());
                    itemHolder.belowImageCentre.setText(lesson.getStartTime().toString());
                    itemHolder.belowImageRight.setText(lesson.getEndTime().toString());
                }

                if(val instanceof Package){
                    Package packageLessons = (Package) val;
                    Log.d(TAG, "onBindData:  student" + packageLessons.getClass() );
                    itemHolder.rightOfImage.setText(packageLessons.getName());
                    itemHolder.belowImageLeft.setText(packageLessons.getStudent().getFullName());
                    itemHolder.belowImageCentre.setText(MessageFormat.format("{0}{1}", getString(R.string.lessons), packageLessons.getNumberOfLessons()));
                    itemHolder.belowImageRight.setText(String.format("%s%s", getString(R.string.dollar_sign), packageLessons.getAmount()));
                }
            };


            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
                super.onBindViewHolder(holder, position);

                View.OnClickListener itemClicked = new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // do something when item is clicked
                        Log.d(TAG, "onClick: ---- " + view.getId() + " position " + position );


                    }
                };
                holder.itemView.setOnClickListener(itemClicked);
            }
        };

        recyclerView.setAdapter(adapter);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(),  LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        return view;
    }


    private void setListViewTitle(String titleName){
        listTitleTextView.setText(titleName);
    }


}
