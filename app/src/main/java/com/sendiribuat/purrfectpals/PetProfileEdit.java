package com.sendiribuat.purrfectpals;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

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
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_petprofileedit2);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

        mAuth = FirebaseAuth.getInstance();
        db = new FirebaseDbHelper(this);
//        fd = FirebaseDatabase.getInstance("https://purrfect-pals-e5ca8-default-rtdb.asia-southeast1.firebasedatabase.app");
        user = mAuth.getCurrentUser();

        if (user != null) {
            dbRef = FirebaseDatabase.getInstance().getReference("Pet2").child(user.getUid());
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


    private void retrieveDataForEditing() {
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Pet2 pet = dataSnapshot.getValue(Pet2.class);
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
        Pet2 pet = new Pet2("Buddy", "Dog", "Golden Retriever", "Male", "Golden", "Nis", "0132406975", "hanis@gmail.com", "123", 3);
        pet.setUserId(pet.getUserId());
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

        dbRef.setValue(pet).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(PetProfileEdit.this, "Profile updated", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(PetProfileEdit.this, "Failed to update profile", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
