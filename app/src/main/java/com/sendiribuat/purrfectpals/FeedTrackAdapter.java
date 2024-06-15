package com.sendiribuat.purrfectpals;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageException;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class FeedTrackAdapter extends RecyclerView.Adapter<FeedTrackAdapter.ViewHolder> {
    private List<FeedTrack> feedList;
    private OnItemClickListener listener;
    private Activity activity;
    private FirebaseDbHelper db;

    public FeedTrackAdapter(List<FeedTrack> feedList, Activity activity, OnItemClickListener listener) {
        this.feedList = feedList;
        this.listener = listener;
        this.activity = activity;
        db = new FirebaseDbHelper(activity);
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
        final long ONE_MEGABYTE = 1024 * 1024;
        StorageReference imagesRef = db.getStorage().getReference().child("images/" + feedTrack.getKey());
        imagesRef.getBytes(ONE_MEGABYTE).addOnCompleteListener(bytes -> {
            Log.d("Storage", feedTrack.getKey());
            if(bytes.isSuccessful()) {
                byte[] data = bytes.getResult();
                if(data != null) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                    holder.feedImg.setImageBitmap(bitmap);
                    holder.feedImg.setVisibility(View.VISIBLE);
                }
            }
        });
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
        ImageView feedImg;

        public ViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            petName = itemView.findViewById(R.id.petName);
            foodName = itemView.findViewById(R.id.foodName);
            foodDesc = itemView.findViewById(R.id.foodDesc);
            feedTime = itemView.findViewById(R.id.feedTime);
            feedDate = itemView.findViewById(R.id.feedDate);
            feedImg = itemView.findViewById(R.id.feedImage);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(feedList.get(position));
                }
            });
        }
    }
}
