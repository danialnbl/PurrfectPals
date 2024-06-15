package com.sendiribuat.purrfectpals;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
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

import java.util.Arrays;

public class PetProfileEdit  extends AppCompatActivity {

    DatabaseReference dbRef;
    FirebaseDbHelper db;

    FirebaseDatabase fd;
    FirebaseAuth mAuth;
    FirebaseUser user;

    Button saveProfileBtn;

    private EditText inputPetName, inputPetAge, inputPetType, inputPetBreed, inputPetGender, inputPetColor, inputOwnerName, inputOwnerNum, inputOwnerEmail, inputIllness, inputIllnessDate, inputMedication, inputDosage;
    Pet pet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_petprofileedit2);

        mAuth = FirebaseAuth.getInstance();
        db = new FirebaseDbHelper(this);
        user = mAuth.getCurrentUser();
        dbRef = db.getDb().getReference("pets");

        // Initialize EditTexts
        inputPetName = findViewById(R.id.inputPetName);
        inputPetAge = findViewById(R.id.inputPetAge);
        inputPetType = findViewById(R.id.inputPetType);
        inputPetBreed = findViewById(R.id.inputPetBreed);
        inputPetGender = findViewById(R.id.inputPetGender);
        inputPetColor = findViewById(R.id.inputPetColor);

        // Initialize Save Button
        saveProfileBtn = findViewById(R.id.saveProfileBtn);
        saveProfileBtn.setOnClickListener(v -> saveProfile());

        // Retrieve current data for editing
        retrieveDataForEditing();
    }

    private void retrieveDataForEditing() {
        Intent intent = getIntent();
        pet = (Pet) intent.getSerializableExtra("pet");
        if (pet != null) {
            inputPetName.setText(pet.getPetName());
            inputPetAge.setText(String.valueOf(pet.getPetAge()));
            inputPetType.setText(pet.getPetAnimalType());
            inputPetBreed.setText(pet.getPetBreed());
            inputPetGender.setText(pet.getPetGender());
            inputPetColor.setText(pet.getPetColor());
        }
    }

    private void saveProfile() {
        String key;
        if(pet == null) {
            pet = new Pet();
            key = dbRef.push().getKey();
            pet.setKey(key);
        }
        else {
            key = pet.getKey();
        }

        String petName = inputPetName.getText().toString().trim();
        String petAge = inputPetAge.getText().toString().trim();
        String petType = inputPetType.getText().toString().trim();
        String petBreed = inputPetBreed.getText().toString().trim();
        String petGender = inputPetGender.getText().toString().trim();
        String petColor = inputPetColor.getText().toString().trim();

        pet.setUserId(mAuth.getUid());
        pet.setPetName(petName);
        pet.setPetAge(Integer.parseInt(petAge));
        pet.setPetAnimalType(petType);
        pet.setPetBreed(petBreed);
        pet.setPetGender(petGender);
        pet.setPetColor(petColor);

        dbRef.child(key).setValue(pet).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(PetProfileEdit.this, "Profile updated", Toast.LENGTH_SHORT).show();
                Intent viewIntent = new Intent(this, PetProfile.class);
                viewIntent.putExtra("pet", pet);
                startActivity(viewIntent);
                finish();
            } else {
                Toast.makeText(PetProfileEdit.this, "Failed to update profile", Toast.LENGTH_SHORT).show();
            }
        });

        Intent viewIntent = new Intent(getApplicationContext(), PetProfile.class);
        viewIntent.putExtra("pet", pet);
        startActivity(viewIntent);
    }

}
