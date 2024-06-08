package com.sendiribuat.purrfectpals;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class FirebaseDbHelper {
    private FirebaseDatabase db;
    private FirebaseAuth mAuth;
    private DatabaseReference ref;
    private Context context;
    public FirebaseDbHelper(Context context) {
        db = FirebaseDatabase.getInstance(Constant.FIREBASE_DB_INSTANCE);
        mAuth = FirebaseAuth.getInstance();
        this.context = context;
    }

    public FirebaseDatabase getDb() {
        return db;
    }

    public DatabaseReference getRef() {
        return ref;
    }

    public boolean insertUser(User user, String userId) {
        AtomicBoolean success = new AtomicBoolean(false);
        ref = db.getReference("users");
        ref.child(userId).setValue(user)
                .addOnCompleteListener(task1 -> {
                    success.set(task1.isSuccessful());
                    if (task1.isSuccessful()) {
                        Toast.makeText(context, "Registration successful!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Failed to register. Please try again.", Toast.LENGTH_SHORT).show();
                    }
                });
        return success.get();
    }
    public void getPets(String userId, final OnPetNamesLoadedListener listener) {
        ref = db.getReference("pets");
        ref.orderByChild("UserId").equalTo(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<String> petNames = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Pet pet = snapshot.getValue(Pet.class);
                    if (pet != null) {
                        petNames.add(pet.getPetName());
                    }
                }
                listener.onPetNamesLoaded(petNames);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle error
                listener.onLoadFailed(databaseError.toException().getMessage());
            }
        });
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
                Toast.makeText(context, "Successfully added vaccine schedule!", Toast.LENGTH_SHORT).show();
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

    public interface OnPetNamesLoadedListener {
        void onPetNamesLoaded(ArrayList<String> petNames);

        void onLoadFailed(String errorMessage);
    }

    public interface UserCallback {
        void onUserCallback(User user);
    }
}
