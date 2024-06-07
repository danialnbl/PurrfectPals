package com.sendiribuat.purrfectpals;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import androidx.appcompat.app.AppCompatActivity;

public class PetProfileEdit  extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseDatabase db;
    private DatabaseReference userRef;

    private EditText editTextPetName, editTextPetAge, editTextPetType, editTextPetBreed, editTextPetGender, editTextPetColor, editTextOwnerName, editTextOwnerNum, editTextPetIllness, editTextIllnessDate, editTextPetMedication, editTextPetDosage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_petprofileedit2);
//        getSupportActionBar().setTitle("Edit Pet Profile");

//        mAuth = FirebaseAuth.getInstance();
//        db = FirebaseDatabase.getInstance("https://your-database-url");
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//
//        if (currentUser != null) {
//            DatabaseReference userUsernameRef = db.getReference("users").child(currentUser.getUid()).child("username");
//            userUsernameRef.addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    if (snapshot.exists()) {
//                        String username = snapshot.getValue(String.class);
//                        userRef = db.getReference("users").child(username);
//                        initializeViews();
//                        fetchProfileData();
//                    } else {
//                        // Handle the case where username is not found
//                        // For example, you could prompt the user to set up their username
//                        // or navigate them to a screen where they can do so.
//                        Toast.makeText(PetProfileEdit.this, "Username not found. Please set up your username.", Toast.LENGTH_SHORT).show();
//
//                        // You might also consider navigating the user to a username setup activity
//                        // or any other appropriate action for your app.
//                    }
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//                    // Handle error
//                    // Display an error message to the user or log the error for debugging
//                    Toast.makeText(PetProfileEdit.this, "Error fetching username: " + error.getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            });
//        }
    }
//
//    private void initializeViews() {
//        editTextPetName = findViewById(R.id.editTextPetName);
//        editTextPetAge = findViewById(R.id.editTextPetAge);
//        editTextPetType = findViewById(R.id.editTextPetType);
//        editTextPetBreed = findViewById(R.id.editTextPetBreed);
//        editTextPetGender = findViewById(R.id.editTextPetGender);
//        editTextPetColor = findViewById(R.id.editTextPetColor);
//        editTextOwnerName = findViewById(R.id.editTextOwnerName);
//        editTextOwnerNum = findViewById(R.id.editTextOwnerNum);
//        editTextPetIllness = findViewById(R.id.editTextPetIllness);
//        editTextIllnessDate = findViewById(R.id.editTextIllnessDate);
//        editTextPetMedication = findViewById(R.id.editTextPetMedication);
//        editTextPetDosage = findViewById(R.id.editTextPetDosage);
//    }
//
//    private void fetchProfileData() {
//        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.exists()) {
//                    editTextPetName.setText(snapshot.child("petName").getValue(String.class));
//                    editTextPetAge.setText(snapshot.child("petAge").getValue(String.class));
//                    editTextPetType.setText(snapshot.child("petType").getValue(String.class));
//                    editTextPetBreed.setText(snapshot.child("petBreed").getValue(String.class));
//                    editTextPetGender.setText(snapshot.child("petGender").getValue(String.class));
//                    editTextPetColor.setText(snapshot.child("petColor").getValue(String.class));
//                    editTextOwnerName.setText(snapshot.child("ownerName").getValue(String.class));
//                    editTextOwnerNum.setText(snapshot.child("ownerNum").getValue(String.class));
//                    editTextPetIllness.setText(snapshot.child("petIllness").getValue(String.class));
//                    editTextIllnessDate.setText(snapshot.child("illnessDate").getValue(String.class));
//                    editTextPetMedication.setText(snapshot.child("petMedication").getValue(String.class));
//                    editTextPetDosage.setText(snapshot.child("petDosage").getValue(String.class));
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(PetProfileEdit.this, "Error fetching data", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    public void saveProfile(View view) {
//        // Update data in the database based on the values in the EditText fields
//        String petName = editTextPetName.getText().toString();
//        String petAge = editTextPetAge.getText().toString();
//        String petType = editTextPetType.getText().toString();
//        String petBreed = editTextPetBreed.getText().toString();
//        String petGender = editTextPetGender.getText().toString();
//        String petColor = editTextPetColor.getText().toString();
//        String ownerName = editTextOwnerName.getText().toString();
//        String ownerNum = editTextOwnerNum.getText().toString();
//        String petIllness = editTextPetIllness.getText().toString();
//        String illnessDate = editTextIllnessDate.getText().toString();
//        String petMedication = editTextPetMedication.getText().toString();
//        String petDosage = editTextPetDosage.getText().toString();
//
//        userRef.child("petName").setValue(petName);
//        userRef.child("petAge").setValue(petAge);
//        userRef.child("petType").setValue(petType);
//        userRef.child("petBreed").setValue(petBreed);
//        userRef.child("petGender").setValue(petGender);
//        userRef.child("petColor").setValue(petColor);
//        userRef.child("ownerName").setValue(ownerName);
//        userRef.child("ownerNum").setValue(ownerNum);
//        userRef.child("petIllness").setValue(petIllness);
//        userRef.child("illnessDate").setValue(illnessDate);
//        userRef.child("petMedication").setValue(petMedication);
//        userRef.child("petDosage").setValue(petDosage);
//
//        // Redirect back to PetProfile activity after saving
//        Intent intent = new Intent(this, PetProfile.class);
//        startActivity(intent);
//        finish();
//    }
//
//    public void openPetProfile(View view) {
//        Intent next = new Intent(getApplicationContext(), PetProfile.class);
//        startActivity(next);
//        finish();
//    }
}
