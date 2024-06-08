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

public class VaccinationRecordAdapter extends RecyclerView.Adapter<VaccinationRecordAdapter.ViewHolder> {
    private List<VaccinationRecord> records;
    private OnItemClickListener listener;

    public VaccinationRecordAdapter(List<VaccinationRecord> records, OnItemClickListener listener) {
        this.records = records;
        this.listener = listener;
    }

    @NonNull
    @Override
    public VaccinationRecordAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.record_item, parent, false);
        return new ViewHolder(view, listener); // Pass listener to ViewHolder constructor
    }

    @Override
    public void onBindViewHolder(@NonNull VaccinationRecordAdapter.ViewHolder holder, int position) {
        VaccinationRecord record = records.get(position);
        holder.key.setText(record.getKey());
        holder.title.setText(record.getTitle());
        holder.petName.setText(record.getPetName());
        holder.location.setText(record.getLocation());
        holder.date.setText(record.getDate());
    }

    @Override
    public int getItemCount() {
        return records.size();
    }

    public interface OnItemClickListener {
        void onItemClick(VaccinationRecord record);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView key, title, petName, location, date;

        public ViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            key = itemView.findViewById(R.id.recKey);
            title = itemView.findViewById(R.id.recordTitle);
            petName = itemView.findViewById(R.id.recordPet);
            location = itemView.findViewById(R.id.recordLocation);
            date = itemView.findViewById(R.id.recordDate);
            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(records.get(position));
                }
            });
        }
    }
}
