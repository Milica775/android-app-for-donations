package com.example.projectapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder>{


    private Context context;
    private List<User> users;
    private Adapter.OnNoteListener onNoteListener;

    public UserAdapter(Context context,List<User> users, Adapter.OnNoteListener onNoteListener) {
        this.context=context;
        this.users = users;
        this.onNoteListener=onNoteListener;
    }



    @NonNull
    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.user_item, parent, false);
        return new UserAdapter.ViewHolder(view,onNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.ViewHolder holder, final int position) {
        final User user=users.get(position);
        holder.tvSumOfMeals.setText(user.sumOfDonation);
        holder.tvEmail.setText(user.email);




    }




    @Override
    public int getItemCount() {
        return users.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
         TextView tvSumOfMeals,tvEmail;
        Adapter.OnNoteListener onNoteListener;

        public ViewHolder(@NonNull View itemView, Adapter.OnNoteListener onNoteListener) {
            super(itemView);
            tvSumOfMeals=itemView.findViewById(R.id.tv_sumOfMeals);
            tvEmail=itemView.findViewById(R.id.tv_email1);


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
