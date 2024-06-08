package com.sendiribuat.purrfectpals;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PetProfile  extends AppCompatActivity {

    ListView medicals, medications;
    FirebaseDbHelper db;

    DatabaseReference dbRef;
    FirebaseDatabase fd;
    FirebaseAuth mAuth;
    FirebaseUser user;

    ListView medHistoryList, medicationList;
    List<PetProfileMedical> medicalHistory = new ArrayList<>();
    List<PetProfileMedication> medication = new ArrayList<>();
    PetProfileMedicalAdapter medHistoryAdapter;
    PetProfileMedicationAdapter medicationAdapter;

    Button editProfileBtn;

    private TextView petName, petAge, petType, petBreed, petGender, petColor, ownerName, ownerNum, ownerEmail, userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_petprofile2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mAuth = FirebaseAuth.getInstance();
        db = new FirebaseDbHelper(this);
//        fd = FirebaseDatabase.getInstance("https://purrfect-pals-e5ca8-default-rtdb.asia-southeast1.firebasedatabase.app");
        user = mAuth.getCurrentUser();

//        mAuth = FirebaseAuth.getInstance();
//        user = mAuth.getCurrentUser();

        if (user != null) {
            dbRef = db.getDb().getReference("pets");
        }

        petName = findViewById(R.id.petName);
        petAge = findViewById(R.id.petAge);
        petType = findViewById(R.id.petType);
        petBreed = findViewById(R.id.petBreed);
        petGender = findViewById(R.id.petGender);
        petColor = findViewById(R.id.petColor);
        ownerName = findViewById(R.id.ownerName);
        ownerNum = findViewById(R.id.ownerNum);

        Pet pet = new Pet("Buddy", "Dog", "Golden Retriever", "Male", "Golden", "Nis", "0132406975", "hanis@gmail.com", "123", 3);

        medHistoryList = findViewById(R.id.petMedHistoryList);
        medicationList = findViewById(R.id.petMedicationList);
        medHistoryAdapter = new PetProfileMedicalAdapter(this, medicalHistory);
        medicationAdapter = new PetProfileMedicationAdapter(this, medication);
        medHistoryList.setAdapter(medHistoryAdapter);
        medicationList.setAdapter(medicationAdapter);

        editProfileBtn = findViewById(R.id.editProfileBtn);
        db = new FirebaseDbHelper(this);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        // Set up Edit Profile button
        editProfileBtn = findViewById(R.id.editProfileBtn);
        editProfileBtn.setOnClickListener(v -> startActivity(new Intent(PetProfile.this, PetProfileEdit.class)));

        retrieveData();
    }

    private void retrieveData() {
        dbRef.orderByChild("UserId").equalTo(mAuth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Pet pet = snapshot.getValue(Pet.class);
                        if (pet != null) {
                            petName.setText(pet.getPetName());
                            petType.setText(pet.getPetAnimalType());
                            petBreed.setText(pet.getPetBreed());
                            petAge.setText(pet.getPetAge());
                            petGender.setText(pet.getPetGender());
                            petColor.setText(pet.getPetColor());
                            ownerName.setText(pet.getOwnerName());
                            ownerNum.setText(pet.getOwnerNum());
                            ownerEmail.setText(pet.getOwnerEmail());

                            // Update medical history
                            if (pet.getMedicalHistory() != null) {
                                medicalHistory.clear();
                                medicalHistory.addAll(pet.getMedicalHistory());
                                medHistoryAdapter.notifyDataSetChanged();
                            }

                            // Update medications
                            if (pet.getMedications() != null) {
                                medication.clear();
                                medication.addAll(pet.getMedications());
                                medicationAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(PetProfile.this, "Error fetching data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }




//    private void displayPetData(Pet2 pet) {
//        userId.setText(pet.getUserId());
//        petName.setText(pet.getPetName());
//        petAge.setText(String.valueOf(pet.getPetAge()));
//        petType.setText(pet.getPetAnimalType());
//        petBreed.setText(pet.getPetBreed());
//        petGender.setText(pet.getPetGender());
//        petColor.setText(pet.getPetColor());
//        ownerName.setText(pet.getOwnerName());
//        ownerNum.setText(pet.getOwnerNum());
//        ownerEmail.setText(pet.getOwnerEmail());
//    }

    public void toBack(View view) {
        Intent next = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(next);
        finish();
    }
}
