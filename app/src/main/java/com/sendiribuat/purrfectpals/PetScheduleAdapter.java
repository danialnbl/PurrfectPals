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

import java.util.ArrayList;
import java.util.List;

public class PetScheduleAdapter extends RecyclerView.Adapter<PetScheduleAdapter.scheduleViewHolder> {

    private OnItemClickListener listener;
    private List<PetSchedule> dataList;

    public PetScheduleAdapter(List<PetSchedule> dataList,OnItemClickListener listener) {
        this.dataList = dataList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public scheduleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.petschedule_item, parent, false);
        return new scheduleViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull scheduleViewHolder holder, int position) {
        PetSchedule schedule = dataList.get(position);
        holder.Key.setText(dataList.get(position).getKey());
        holder.Title.setText(dataList.get(position).getTitle());
        holder.PetName.setText(dataList.get(position).getPetName());
        holder.EvenDesc.setText(dataList.get(position).getEvenDesc());
        holder.EventLocation.setText(dataList.get(position).getEventLocation());
        holder.Date.setText(dataList.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void searchDataList(ArrayList<PetSchedule> searchList) {
        dataList = searchList;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(PetSchedule record);
    }

    public class scheduleViewHolder extends RecyclerView.ViewHolder {
        TextView Key, Title, PetName, EvenDesc, EventLocation,Date;

        public scheduleViewHolder(@NonNull View itemView, PetScheduleAdapter.OnItemClickListener listener) {
            super(itemView);
            Key = itemView.findViewById(R.id.scheduleKey);
            Title = itemView.findViewById(R.id.scheduleTitle);
            PetName = itemView.findViewById(R.id.schedulePetName);
            EvenDesc = itemView.findViewById(R.id.scheduleDesc);
            EventLocation = itemView.findViewById(R.id.scheduleLoc);
            Date = itemView.findViewById(R.id.scheduleDate);
            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(dataList.get(position));
                }
            });
        }
    }

}
