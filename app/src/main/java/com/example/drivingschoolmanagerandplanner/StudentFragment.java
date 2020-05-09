package com.example.drivingschoolmanagerandplanner;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.drivingschoolmanagerandplanner.data.DbHandler;


///**
// * A simple {@link Fragment} subclass.
// * Use the {@link StudentFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
public class StudentFragment extends Fragment {




    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;


    private static final String TAG = "StudentFragment" ;
    public StudentFragment() {
        // Required empty public constructor
    }
//
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment StudentFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static StudentFragment newInstance(String param1, String param2) {
//        StudentFragment fragment = new StudentFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
////            mParam1 = getArguments().getString(ARG_PARAM1);
////            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_student, container, false);



        return  view;
    }

    private void SaveStudent(){
        DbHandler dbHandler = new DbHandler(getActivity());
        long rowId =   dbHandler.InsertStudentDetails("Maya","Ole",0415356652 , "eda@gmail.com","104 reilly St.","Liverpool", "NSW", 2170, "AU");
        Log.d(TAG, "getStudentsFromDB: row Id " + rowId);
        dbHandler.close();
    }

}
