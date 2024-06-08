package com.sendiribuat.purrfectpals;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class VaccinationScheduleAdapter extends RecyclerView.Adapter<VaccinationScheduleAdapter.ViewHolder> {
    private List<VaccinationSchedule> schedules;
    private OnItemClickListener listener;

    public VaccinationScheduleAdapter(List<VaccinationSchedule> schedules, OnItemClickListener listener) {
        this.schedules = schedules;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.schedule_item, parent, false);
        return new ViewHolder(view, listener); // Pass listener to ViewHolder constructor
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        VaccinationSchedule schedule = schedules.get(position);
        holder.key.setText(schedule.getKey());
        holder.title.setText(schedule.getTitle());
        holder.petName.setText(schedule.getPetName());
        holder.date.setText(schedule.getDate());
    }

    @Override
    public int getItemCount() {
        return schedules.size();
    }

    public interface OnItemClickListener {
        void onItemClick(VaccinationSchedule schedule);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView key, title, petName, date;

        public ViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            key = itemView.findViewById(R.id.scheduleKey);
            title = itemView.findViewById(R.id.scheduleTitle);
            petName = itemView.findViewById(R.id.schedulePetName);
            date = itemView.findViewById(R.id.scheduleDate);
            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(schedules.get(position));
                }
            });
        }
    }
}
