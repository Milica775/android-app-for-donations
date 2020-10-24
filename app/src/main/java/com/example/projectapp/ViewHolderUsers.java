package com.example.projectapp;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolderUsers extends RecyclerView.ViewHolder {

    TextView email,sumOfMeals,flName;
    public ViewHolderUsers(@NonNull View itemView) {
        super(itemView);

        email=itemView.findViewById(R.id.tv_email1);
        sumOfMeals=itemView.findViewById(R.id.tv_sumOfMeals);
        flName=itemView.findViewById(R.id.tv_FLName);

    }
}
