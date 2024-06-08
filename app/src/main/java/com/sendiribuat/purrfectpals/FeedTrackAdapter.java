package com.sendiribuat.purrfectpals;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FeedTrackAdapter extends RecyclerView.Adapter<FeedTrackAdapter.ViewHolder> {
    private List<FeedTrack> feedList;
    private OnItemClickListener listener;

    public FeedTrackAdapter(List<FeedTrack> feedList, OnItemClickListener listener) {
        this.feedList = feedList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_item, parent, false);
        return new ViewHolder(view, listener); // Pass listener to ViewHolder constructor
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FeedTrack feedTrack = feedList.get(position);
        holder.petName.setText(feedTrack.getFeedPet());
        holder.foodName.setText(feedTrack.getFeedName());
        holder.foodDesc.setText(feedTrack.getFoodDesc());
        holder.feedTime.setText(feedTrack.getFeedTime());
        holder.feedDate.setText(feedTrack.getFeedDate());
    }

    @Override
    public int getItemCount() {
        return feedList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(FeedTrack feedTrack);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView petName, foodName, foodDesc, feedTime, feedDate;

        public ViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            petName = itemView.findViewById(R.id.petName);
            foodName = itemView.findViewById(R.id.foodName);
            foodDesc = itemView.findViewById(R.id.foodDesc);
            feedTime = itemView.findViewById(R.id.feedTime);
            feedDate = itemView.findViewById(R.id.feedDate);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(feedList.get(position));
                }
            });
        }
    }
}
