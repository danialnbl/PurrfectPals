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

    private List<PetProfileMedical> petMedHistoryList;

    private List<PetProfileMedication> petMedicationList;

    ListView medHistoryList, medicationList;
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
        user = mAuth.getCurrentUser();


        if (user != null) {
            dbRef = db.getDb().getReference("pets");
        }

        petName = findViewById(R.id.petName);
        petAge = findViewById(R.id.petAge);
        petType = findViewById(R.id.petType);
        petBreed = findViewById(R.id.petBreed);
        petGender = findViewById(R.id.petGender);
        petColor = findViewById(R.id.petColor);

        //Pet pet = new Pet();

        medHistoryList = findViewById(R.id.petMedHistoryList);
        petMedHistoryList = new ArrayList<>();
        medHistoryAdapter = new PetProfileMedicalAdapter(this, petMedHistoryList);
        medHistoryList.setAdapter(medHistoryAdapter);

        medicationList = findViewById(R.id.petMedicationList);
        petMedicationList = new ArrayList<>();
        medicationAdapter = new PetProfileMedicationAdapter(this, petMedicationList);
        medicationList.setAdapter(medHistoryAdapter);

        editProfileBtn = findViewById(R.id.editProfileBtn);

        // Set up Edit Profile button
        editProfileBtn = findViewById(R.id.editProfileBtn);
        editProfileBtn.setOnClickListener(v -> startActivity(new Intent(PetProfile.this, PetProfileEdit.class)));

        retrieveData();
    }

    private void retrieveData() {
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        //Pet pet = snapshot.getValue(Pet.class);
                        Intent intent = getIntent();
                        Pet pet = (Pet) intent.getSerializableExtra("pet");
                        if (pet != null) {
                            petName.setText(pet.getPetName());
                            petType.setText(pet.getPetAnimalType());
                            petBreed.setText(pet.getPetBreed());
                            petAge.setText(String.valueOf(pet.getPetAge()));
                            petGender.setText(pet.getPetGender());
                            petColor.setText(pet.getPetColor());

                            // Update medical history
                            if (pet.getMedicalHistory() != null) {
                                petMedHistoryList.clear();
                                petMedHistoryList.addAll(pet.getMedicalHistory());
                                medHistoryAdapter.notifyDataSetChanged();
                            }

                            // Update medications
                            if (pet.getMedications() != null) {
                                petMedicationList.clear();
                                petMedicationList.addAll(pet.getMedications());
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


    public void toBack(View view) {
        Intent next = new Intent(getApplicationContext(), UserProfile.class);
        startActivity(next);
        finish();
    }
}
