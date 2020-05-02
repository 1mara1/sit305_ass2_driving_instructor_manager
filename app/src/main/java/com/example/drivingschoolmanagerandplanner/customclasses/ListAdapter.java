package com.example.drivingschoolmanagerandplanner.customclasses;

import android.content.Context;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public abstract class ListAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<T> records;

    public abstract ItemViewHolder setViewHolder(ViewGroup parent);

    public abstract void onBindData(RecyclerView.ViewHolder holder, T val);

    public ListAdapter(Context context, ArrayList<T> records){
        this.context = context;
        this.records = records;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemViewHolder holder = setViewHolder(parent);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        onBindData(holder, records.get(position));
    }

    @Override
    public int getItemCount() {
        return records.size();
    }

    public void addItems( ArrayList<T> savedCardItemz){
        records = savedCardItemz;
        this.notifyDataSetChanged();
    }

    public T getItem(int position){
        return records.get(position);
    }
}
