package com.example.drivingschoolmanagerandplanner.customclasses;


import android.content.Context;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class RecyclerClickListener extends RecyclerView.SimpleOnItemTouchListener {

    interface onRecyclerClickListener{
        void onItemClick(View view, int position);
    }

    final private onRecyclerClickListener onRecyclerClickListener;

    public RecyclerClickListener(Context context, final RecyclerView recyclerView, RecyclerClickListener.onRecyclerClickListener onRecyclerClickListener) {
        this.onRecyclerClickListener = onRecyclerClickListener;
    }
}
