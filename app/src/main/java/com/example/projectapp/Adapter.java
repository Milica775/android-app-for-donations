package com.example.projectapp;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>{

    private Context context;
    private List<Donation> donations;
    private OnNoteListener onNoteListener;

    public Adapter(Context context,List<Donation> donations, OnNoteListener onNoteListener) {
        this.context=context;
        this.donations = donations;
        this.onNoteListener=onNoteListener;
    }



    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.donation_item, parent, false);
        return new Adapter.ViewHolder(view,onNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, final int position) {
          final Donation donation=donations.get(position);
        holder.tvAddress.setText(donation.address);
          holder.tvNumOfMeals.setText(Integer.toString(donation.numOfDonation) + "." + " " + "obroka");
          holder.tvDate.setText(donation.date);
          holder.tvEmail.setText(donation.email);




    }




    @Override
    public int getItemCount() {
        return donations.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvAddress,tvNumOfMeals,tvEmail,tvDate;
        OnNoteListener  onNoteListener;

        public ViewHolder(@NonNull View itemView, OnNoteListener onNoteListener) {
            super(itemView);
            tvAddress=itemView.findViewById(R.id.tv_address);
            tvNumOfMeals=itemView.findViewById(R.id.tv_numOfMeals);
            tvEmail=itemView.findViewById(R.id.tv_email);
            tvDate=itemView.findViewById(R.id.tv_date);

            this.onNoteListener=onNoteListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onNoteListener.onNoteClick(getAdapterPosition());

        }
    }

    public interface  OnNoteListener{
        void onNoteClick(int position);
    }
}
