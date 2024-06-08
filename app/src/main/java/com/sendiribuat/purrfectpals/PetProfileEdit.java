package com.sendiribuat.purrfectpals;

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

public class PetProfileEdit  extends AppCompatActivity {

    DatabaseReference dbRef;
    FirebaseDbHelper db;

    FirebaseDatabase fd;
    FirebaseAuth mAuth;
    FirebaseUser user;

    Button saveProfileBtn;

    private EditText inputPetName, inputPetAge, inputPetType, inputPetBreed, inputPetGender, inputPetColor, inputOwnerName, inputOwnerNum, inputOwnerEmail, inputIllness, inputIllnessDate, inputMedication, inputDosage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_petprofileedit2);

        mAuth = FirebaseAuth.getInstance();
        db = new FirebaseDbHelper(this);
        user = mAuth.getCurrentUser();

        if (user != null) {
            dbRef = db.getDb().getReference("pets");
        }

// Initialize EditTexts
        inputPetName = findViewById(R.id.inputPetName);
        inputPetAge = findViewById(R.id.inputPetAge);
        inputPetType = findViewById(R.id.inputPetType);
        inputPetBreed = findViewById(R.id.inputPetBreed);
        inputPetGender = findViewById(R.id.inputPetGender);
        inputPetColor = findViewById(R.id.inputPetColor);
        inputOwnerName = findViewById(R.id.inputOwnerName);
        inputOwnerNum = findViewById(R.id.inputOwnerNumber);
        inputOwnerEmail = findViewById(R.id.inputOwnerEmail);

        // Initialize Save Button
        saveProfileBtn = findViewById(R.id.saveProfileBtn);
        saveProfileBtn.setOnClickListener(v -> saveProfile());

        // Retrieve current data for editing
        retrieveDataForEditing();
    }

    private void retrieveDataForEditing() {
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Pet pet = dataSnapshot.getValue(Pet.class);
                    if (pet != null) {
                        inputPetName.setText(pet.getPetName());
                        inputPetAge.setText(pet.getPetAge());
                        inputPetType.setText(pet.getPetAnimalType());
                        inputPetBreed.setText(pet.getPetBreed());
                        inputPetGender.setText(pet.getPetGender());
                        inputPetColor.setText(pet.getPetColor());
                        inputOwnerName.setText(pet.getOwnerName());
                        inputOwnerNum.setText(pet.getOwnerNum());
                        inputOwnerEmail.setText(pet.getOwnerEmail());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database error
            }
        });
    }

    private void saveProfile() {
        String key = dbRef.push().getKey();
        String petName = inputPetName.getText().toString().trim();
        String petAge = inputPetAge.getText().toString().trim();
        String petType = inputPetType.getText().toString().trim();
        String petBreed = inputPetBreed.getText().toString().trim();
        String petGender = inputPetGender.getText().toString().trim();
        String petColor = inputPetColor.getText().toString().trim();
        String ownerName = inputOwnerName.getText().toString().trim();
        String ownerNum = inputOwnerNum.getText().toString().trim();
        String ownerEmail = inputOwnerEmail.getText().toString().trim();

//        Pet2 pet = new Pet2(petName, petType, petBreed, petGender, petColor, ownerName, ownerNum, ownerEmail, "123", petAge);
        Pet pet = new Pet("Buddy", "Dog", "Golden Retriever", "Male", "Golden", "Nis", "0132406975", "hanis@gmail.com", "123", 3);
        pet.setUserId(mAuth.getUid());
        pet.setPetName(petName);
//        pet.setPetAge(petAge);
        pet.setPetAge(Integer.parseInt(petAge));
        pet.setPetAnimalType(petType);
        pet.setPetBreed(petBreed);
        pet.setPetGender(petGender);
        pet.setPetColor(petColor);
        pet.setOwnerName(ownerName);
        pet.setOwnerNum(ownerNum);
        pet.setOwnerEmail(ownerEmail);

        dbRef.child(key).setValue(pet).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(PetProfileEdit.this, "Profile updated", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(PetProfileEdit.this, "Failed to update profile", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
