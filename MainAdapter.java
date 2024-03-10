package com.example.freelancingapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MyViewHolder> {

    Context context;
    ArrayList<User> userArrayList;

    public MainAdapter(Context context, ArrayList<User> userArrayList) {
        this.context = context;
        this.userArrayList = userArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.userentry, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        User user = userArrayList.get(position);
        holder.Job_id.setText(user.getJob_id());
        holder.Job_role.setText(user.getJob_name());
        holder.Location.setText(user.getLocation());
        holder.Organization_Name.setText(user.getOname());
        holder.Shift.setText(user.getShift());
        holder.Field.setText(user.getField());
        holder.Mode.setText(user.getMode());
        holder.Recruiter_id.setText(user.getRecruiter_id());
    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView Job_id, Job_role, Location, Organization_Name, Shift, Field, Mode, Recruiter_id;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Job_id = itemView.findViewById(R.id.job_id);
            Job_role = itemView.findViewById(R.id.job_name);
            Location = itemView.findViewById(R.id.Location);
            Organization_Name = itemView.findViewById(R.id.oname);
            Shift = itemView.findViewById(R.id.Shift);
            Field = itemView.findViewById(R.id.Field);
            Mode = itemView.findViewById(R.id.mode);
            Recruiter_id = itemView.findViewById(R.id.recruiter_id);
        }
    }

}
