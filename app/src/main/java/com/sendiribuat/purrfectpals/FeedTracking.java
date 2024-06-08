package com.sendiribuat.purrfectpals;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class FeedTracking extends AppCompatActivity {
    RecyclerView feedView;
    Button addFeedBtn, submit;
    EditText foodName, foodDesc, foodTime, foodDate;
    Spinner recPet;
    FirebaseDbHelper db;
    FirebaseUser user;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_feed_tracking);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        db = new FirebaseDbHelper(this);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        addFeedBtn = findViewById(R.id.addFeedBtn);
        feedView = findViewById(R.id.feedRecyclerView);
        feedView.setLayoutManager(new LinearLayoutManager(this));
        initList();

        Dialog dialog = new Dialog(this);
        addFeedBtn.setOnClickListener(v -> {
            dialog.setContentView(R.layout.add_feed);
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            // Initialize EditTexts and Spinner
            foodName = dialog.findViewById(R.id.foodName);
            foodDesc = dialog.findViewById(R.id.foodDesc);
            foodTime = dialog.findViewById(R.id.foodTime);
            foodDate = dialog.findViewById(R.id.foodDate);
            recPet = dialog.findViewById(R.id.recPet);
            submit = dialog.findViewById(R.id.feedAddBtn);

            // Set up Spinner with pet names
            getPetNames(petNames -> {
                ArrayAdapter<String> petAdapter = new ArrayAdapter<>(FeedTracking.this, android.R.layout.simple_spinner_item, petNames);
                petAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                recPet.setAdapter(petAdapter);
            });

            foodDate.setOnClickListener(v1 -> {
                final Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(FeedTracking.this,
                        (view, selectedYear, selectedMonth, selectedDay) -> {
                            foodDate.setText(selectedYear + "-" + (selectedMonth + 1) + "-" + selectedDay);
                        }, year, month, day);
                datePickerDialog.show();
            });

            foodTime.setOnClickListener(v1 -> {
                final Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(FeedTracking.this,
                        (view, selectedHour, selectedMinute) -> {
                            foodTime.setText(String.format("%02d:%02d", selectedHour, selectedMinute));
                        }, hour, minute, true);
                timePickerDialog.show();
            });

            submit.setOnClickListener(v1 -> {
                FeedTrack feed = new FeedTrack(foodName.getText().toString(), recPet.getSelectedItem().toString(), foodDesc.getText().toString(), foodTime.getText().toString(), foodDate.getText().toString(), user.getUid());
                db.insertFeedTracking(feed);
                initList();
                dialog.dismiss();
            });
            dialog.show();
        });
    }

    private void initList() {
        db.getFeedTracking(user.getUid(), feedTracks -> {
            FeedTrackAdapter feedApt = new FeedTrackAdapter(feedTracks, feedTrack -> {
                Toast.makeText(FeedTracking.this, "Clicked: " + feedTrack.getFeedName(), Toast.LENGTH_SHORT).show();
            });
            feedView.setAdapter(feedApt);
        });
    }

    private void getPetNames(PetNamesCallback callback) {
        ArrayList<String> petNames = new ArrayList<>();
        db.getPets(user.getUid(), new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Pet pet = snapshot.getValue(Pet.class);
                    if (pet != null) {
                        petNames.add(pet.getPetName());
                    }
                }
                callback.onPetNamesLoaded(petNames);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle error
            }
        });
    }

    public interface PetNamesCallback {
        void onPetNamesLoaded(ArrayList<String> petNames);
    }

    public void toBack(View view) {
        finish();
    }
}