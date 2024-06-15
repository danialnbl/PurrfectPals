package com.sendiribuat.purrfectpals;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class FirebaseDbHelper {
    private FirebaseDatabase db;
    private FirebaseAuth mAuth;
    private DatabaseReference ref;
    private FirebaseStorage storage;
    private StorageReference storageRef;
    private Context context;
    public FirebaseDbHelper(Context context) {
        db = FirebaseDatabase.getInstance(Constant.FIREBASE_DB_INSTANCE);
        mAuth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        this.context = context;
    }

    public FirebaseDatabase getDb() {
        return db;
    }

    public DatabaseReference getRef() {
        return ref;
    }

    public FirebaseStorage getStorage() {
        return storage;
    }

    public boolean insertUser(User user, String userId, Activity activity) {
        AtomicBoolean success = new AtomicBoolean(false);
        ref = db.getReference("users");
        ref.child(userId).setValue(user)
                .addOnCompleteListener(task1 -> {
                    success.set(task1.isSuccessful());
                    if (task1.isSuccessful()) {
                        Toast.makeText(context, "Registration successful!", Toast.LENGTH_SHORT).show();
                        mAuth.signOut();
                        context.startActivity(new Intent(context, LoginActivity.class));
                        activity.finish();
                    } else {
                        Toast.makeText(context, "Failed to register. Please try again.", Toast.LENGTH_SHORT).show();
                    }
                });
        return success.get();
    }
    public void getPets(String userId, final ValueEventListener listener) {
        ref = db.getReference("pets");
        ref.orderByChild("userId").equalTo(userId).addListenerForSingleValueEvent(listener);
    }

    public void getUserInformation(String userId, final UserCallback userCallback) {
        ref = db.getReference("users");
        ref.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    User user = dataSnapshot.getValue(User.class);
                    userCallback.onUserCallback(user);
                } else {
                    Toast.makeText(context, "User not found", Toast.LENGTH_SHORT).show();
                    userCallback.onUserCallback(null);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(context, "Failed to get user information: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                userCallback.onUserCallback(null);
            }
        });
    }

    public void insertVaccineSchedule(VaccinationSchedule schedule) {
        ref = db.getReference("vaccine_schedules");
        String key = ref.push().getKey();
        ref.child(key).setValue(schedule).addOnCompleteListener(task -> {
            if(task.isSuccessful()) {
                Toast.makeText(context, "Successfully added vaccine schedule!", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void updateVaccineSchedule(VaccinationSchedule schedule) {
        ref = db.getReference("vaccine_schedules");
        ref.child(schedule.getKey()).setValue(schedule).addOnCompleteListener(task -> {
            if(task.isSuccessful()) {
                Toast.makeText(context, "Successfully updated vaccine schedule!", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void deleteVaccineSchedule(String key) {
        ref = db.getReference("vaccine_schedules");
        ref.child(key).removeValue();
    }
    public void getVaccineSchedules(String userId, final ValueEventListener listener) {
        ref = db.getReference("vaccine_schedules");
        ref.orderByChild("userId").equalTo(userId).addListenerForSingleValueEvent(listener);
    }

    //PetSchedule

    public void insertPetSchedule(PetSchedule schedule) {
        ref = db.getReference("pet_schedules");
        String key = ref.push().getKey();
        ref.child(key).setValue(schedule).addOnCompleteListener(task -> {
            if(task.isSuccessful()) {
                Toast.makeText(context, "Successfully added pet schedule!", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void updatePetSchedule(PetSchedule schedule) {
        ref = db.getReference("pet_schedules");
        ref.child(schedule.getKey()).setValue(schedule).addOnCompleteListener(task -> {
            if(task.isSuccessful()) {
                Toast.makeText(context, "Successfully added pet schedule!", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void deletePetSchedule(String key) {
        ref = db.getReference("pet_schedules");
        ref.child(key).removeValue();
    }
    public void getPetSchedules(String userId, final ValueEventListener listener) {
        ref = db.getReference("pet_schedules");
        ref.orderByChild("userId").equalTo(userId).addListenerForSingleValueEvent(listener);
    }

    public void insertVaccineRecord(VaccinationRecord record) {
        ref = db.getReference("vaccine_records");
        String key = ref.push().getKey();
        ref.child(key).setValue(record).addOnCompleteListener(task -> {
            if(task.isSuccessful()) {
                Toast.makeText(context, "Successfully added vaccine record!", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void deleteVaccineRecord(String key) {
        ref = db.getReference("vaccine_records");
        ref.child(key).removeValue();
    }

    public void updateVaccineRecord(VaccinationRecord record) {
        ref = db.getReference("vaccine_records");
        ref.child(record.getKey()).setValue(record).addOnCompleteListener(task -> {
            if(task.isSuccessful()) {
                Toast.makeText(context, "Successfully updated vaccine schedule!", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getVaccineRecords(String userId, final ValueEventListener listener) {
        ref = db.getReference("vaccine_records");
        ref.orderByChild("userId").equalTo(userId).addListenerForSingleValueEvent(listener);
    }

    public void getFeedTracking(String userId, OnFeedTrackingLoadedListener listener) {
        ref = db.getReference("feed_tracking");
        ref.orderByChild("userId").equalTo(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    ArrayList<FeedTrack> feedTracks = new ArrayList<>();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        FeedTrack feed = snapshot.getValue(FeedTrack.class);
                        feed.setKey(snapshot.getKey());
                        feedTracks.add(feed);
                    }
                    listener.onFeedTrackingLoaded(feedTracks);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(context, "Failed to get user information: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void insertFeedTracking(FeedTrack feed, @Nullable Uri uri, OnFeedTrackingUploadListener listener) {
        ref = db.getReference("feed_tracking");
        String key = ref.push().getKey();
        if (uri != null) {
            StorageReference imagesRef = FirebaseStorage.getInstance().getReference().child("images/" + key);

            try {
                // Convert Uri to Bitmap
                InputStream inputStream = context.getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                if (inputStream != null) {
                    inputStream.close();
                }
                bitmap = rotateBitmap(bitmap);

                // Compress the Bitmap to 25% quality
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 25, baos);
                byte[] data = baos.toByteArray();

                // Upload the compressed image
                UploadTask uploadTask = imagesRef.putBytes(data);
                uploadTask.addOnSuccessListener(taskSnapshot -> {
                    ref.child(key).setValue(feed).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(context, "Successfully added feed tracking!", Toast.LENGTH_SHORT).show();
                            listener.onFeedTrackingUpload();
                        } else {
                            Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }).addOnFailureListener(exception -> {
                    // Handle failed upload
                    Toast.makeText(context, "Image upload failed: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                });
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(context, "Failed to convert Uri to Bitmap: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        } else {
            ref.child(key).setValue(feed).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(context, "Successfully added feed tracking!", Toast.LENGTH_SHORT).show();
                    listener.onFeedTrackingUpload();
                } else {
                    Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void updateFeedTracking(FeedTrack feed) {
        ref = db.getReference("feed_tracking");
        ref.child(feed.getKey()).setValue(feed).addOnCompleteListener(task -> {
            if(task.isSuccessful()) {
                Toast.makeText(context, "Successfully updated feed tracking!", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void deleteFeedTracking(String key) {
        ref = db.getReference("feed_tracking");
        ref.child(key).removeValue();
    }

    private Bitmap rotateBitmap(Bitmap bitmap) {
        Matrix matrix = new Matrix();
        matrix.postRotate(90);
        Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return rotatedBitmap;
    }

    public interface OnFeedTrackingLoadedListener {
        void onFeedTrackingLoaded(ArrayList<FeedTrack> feedTracks);
    }

    public interface OnFeedTrackingUploadListener {
        void onFeedTrackingUpload();
    }

    public interface OnPetNamesLoadedListener {
        void onPetNamesLoaded(ArrayList<String> petNames);

        void onLoadFailed(String errorMessage);
    }

    public interface UserCallback {
        void onUserCallback(User user);
    }
}
