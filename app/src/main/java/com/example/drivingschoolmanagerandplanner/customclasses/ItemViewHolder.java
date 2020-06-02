package com.example.drivingschoolmanagerandplanner.customclasses;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.drivingschoolmanagerandplanner.R;

public class ItemViewHolder extends RecyclerView.ViewHolder {

    private Context context;
    private View view;
   private final ImageView image;
   public final TextView rightOfImage, belowImageLeft, belowImageCentre, belowImageRight;


    public ItemViewHolder (Context context, View view) {
        super(view);

        this.context = context;
        this.view = view;


        image = (ImageView)view.findViewById(R.id.listItemImageView);
        rightOfImage = (TextView) view.findViewById(R.id.textRightOfImageItemTextView);
        belowImageLeft = (TextView)view.findViewById(R.id.textBelowLeftImageItemTextView);
        belowImageCentre = (TextView)view.findViewById(R.id.textBelowCentreImageItemTextView);
        belowImageRight = (TextView)view.findViewById(R.id.textBelowRightImageItemTextView);
    }


}
