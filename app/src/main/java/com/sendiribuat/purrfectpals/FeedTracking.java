package com.sendiribuat.purrfectpals;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.Manifest;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
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
    Button addFeedBtn, addFeedCamBtn, submit;
    EditText foodName, foodDesc, foodTime, foodDate;
    Spinner recPet;
    FirebaseDbHelper db;
    FirebaseUser user;
    FirebaseAuth mAuth;
    Uri image_uri;
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
        addFeedCamBtn = findViewById(R.id.addFeedImgBtn);
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
                db.insertFeedTracking(feed, null, () -> {
                    startActivity(new Intent(FeedTracking.this, FeedTracking.class));
                    finish();
                });
                dialog.dismiss();
            });
            dialog.show();
        });

        addFeedCamBtn.setOnClickListener(v -> {
            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED || checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_DENIED) {
                String[] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                requestPermissions(permission, 112);
            } else {
                openCamera();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 112) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, proceed to open camera
                openCamera();
            } else {
                // Permission denied, handle accordingly (e.g., show a message)
                Toast.makeText(this, "Permission denied. Cannot open camera.", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void openCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera");
        image_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);

        // Set 1:1 aspect ratio for camera intent
        cameraIntent.putExtra("aspectX", 1);
        cameraIntent.putExtra("aspectY", 1);
        cameraIntent.putExtra("scale", true);

        // Launch the camera intent using cameraActivityResultLauncher
        cameraActivityResultLauncher.launch(cameraIntent);
    }

    ActivityResultLauncher<Intent> cameraActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    showAddFeedDialog();
                }
            });
    private void showAddFeedDialog() {
        Dialog dialog = new Dialog(this);
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
            db.insertFeedTracking(feed, image_uri, () -> {
                startActivity(new Intent(FeedTracking.this, FeedTracking.class));
                finish();
            });
            dialog.dismiss();
        });
        dialog.show();
    }

    private void initList() {
        db.getFeedTracking(user.getUid(), feedTracks -> {
            FeedTrackAdapter feedApt = new FeedTrackAdapter(feedTracks, this, feedTrack -> {
                new AlertDialog.Builder(this)
                        .setTitle("Delete Feed Tracking")
                        .setMessage(feedTrack.getFeedName() + " " + feedTrack.getFeedDate() + " " + feedTrack.getFeedTime())
                        .setPositiveButton("Yes", (dialog1, which) -> {
                            db.getDb().getReference("feed_tracking").child(feedTrack.getKey()).removeValue();
                            db.getStorage().getReference("images/" + feedTrack.getKey()).delete();
                            startActivity(new Intent(FeedTracking.this, FeedTracking.class));
                            finish();
                        })
                        .setNegativeButton("No", (dialog1, which) -> {})
                        .create().show();
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