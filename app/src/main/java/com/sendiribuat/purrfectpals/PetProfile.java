package com.sendiribuat.purrfectpals;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
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

public class PetProfile  extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseDatabase db;
    private DatabaseReference userRef;

    private TextView textPetName, textPetAge, textPetType, textPetBreed, textPetGender, textPetColor, textOwnerName, textOwnerNum;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_petprofile);
        getSupportActionBar().setTitle("Pet Profile");

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance("https://your-database-url");
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            String userId = currentUser.getUid();
            userRef = db.getReference("users").child(userId);
            initializeViews();
            fetchProfileData();
        } else {
            Toast.makeText(this, "No user logged in", Toast.LENGTH_SHORT).show();
        }
    }

    private void initializeViews() {
        textPetName = findViewById(R.id.textPetName);
        textPetAge = findViewById(R.id.textPetAge);
        textPetType = findViewById(R.id.textPetType);
        textPetBreed = findViewById(R.id.textPetBreed);
        textPetGender = findViewById(R.id.textPetGender);
        textPetColor = findViewById(R.id.textPetColor);
        textOwnerName = findViewById(R.id.textOwnerName);
        textOwnerNum = findViewById(R.id.textOwnerNum);
    }

    private void fetchProfileData() {
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String petName = snapshot.child("petName").getValue(String.class);
                    String petAge = snapshot.child("petAge").getValue(String.class);
                    String petType = snapshot.child("petType").getValue(String.class);
                    String petBreed = snapshot.child("petBreed").getValue(String.class);
                    String petGender = snapshot.child("petGender").getValue(String.class);
                    String petColor = snapshot.child("petColor").getValue(String.class);
                    String ownerName = snapshot.child("ownerName").getValue(String.class);
                    String ownerNum = snapshot.child("ownerNum").getValue(String.class);

                    textPetName.setText(petName != null ? petName : "Please update your profile");
                    textPetAge.setText(petAge != null ? petAge : "");
                    textPetType.setText(petType != null ? petType : "");
                    textPetBreed.setText(petBreed != null ? petBreed : "");
                    textPetGender.setText(petGender != null ? petGender : "");
                    textPetColor.setText(petColor != null ? petColor : "");
                    textOwnerName.setText(ownerName != null ? ownerName : "");
                    textOwnerNum.setText(ownerNum != null ? ownerNum : "");

                } else {
                    Toast.makeText(PetProfile.this, "Please update your profile", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(PetProfile.this, "Error fetching data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void openPetProfileEdit(View view) {
        Intent next = new Intent(getApplicationContext(), PetProfileEdit.class);
        startActivity(next);
        finish();
    }
}
