package com.sendiribuat.purrfectpals;

import android.content.Context;
import android.widget.Toast;

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
    private DatabaseReference ref;
    private Context context;
    public FirebaseDbHelper(Context context) {
        db = FirebaseDatabase.getInstance(Constant.FIREBASE_DB_INSTANCE);
        this.context = context;
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
                List<String> petNames = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Pet pet = snapshot.getValue(Pet.class);
                    if (pet != null) {
                        petNames.add(pet.getPetName());
                    }
                }
                String[] petNamesArray = petNames.toArray(new String[0]);
                listener.onPetNamesLoaded(petNamesArray);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle error
                listener.onLoadFailed(databaseError.toException().getMessage());
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

    public interface OnPetNamesLoadedListener {
        void onPetNamesLoaded(String[] petNames);

        void onLoadFailed(String errorMessage);
    }
}
