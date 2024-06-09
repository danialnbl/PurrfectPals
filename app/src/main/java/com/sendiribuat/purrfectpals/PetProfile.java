package com.sendiribuat.purrfectpals;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

import org.w3c.dom.Text;

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

    Button editProfileBtn, addMHistory, addMedication;

    private TextView petName, petAge, petType, petBreed, petGender, petColor, ownerName, ownerNum, ownerEmail, userId;
    Pet pet;


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
        addMHistory = findViewById(R.id.addMedHistory);
        addMedication = findViewById(R.id.addMedication);

        //Pet pet = new Pet();

        medHistoryList = findViewById(R.id.petMedHistoryList);
        petMedHistoryList = new ArrayList<>();
        medHistoryAdapter = new PetProfileMedicalAdapter(this, petMedHistoryList);
        medHistoryList.setAdapter(medHistoryAdapter);

        medicationList = findViewById(R.id.petMedicationList);
        petMedicationList = new ArrayList<>();
        medicationAdapter = new PetProfileMedicationAdapter(this, petMedicationList);
        medicationList.setAdapter(medicationAdapter);

        editProfileBtn = findViewById(R.id.editProfileBtn);

        // Set up Edit Profile button
        editProfileBtn = findViewById(R.id.editProfileBtn);
        editProfileBtn.setOnClickListener(v -> {
            Intent edit = new Intent(PetProfile.this, PetProfileEdit.class);
            edit.putExtra("pet", pet);
            startActivity(edit);
            finish();
        });
        Dialog dialog = new Dialog(this);
        addMHistory.setOnClickListener(v -> {
            dialog.setContentView(R.layout.add_medical_history);
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            EditText illness = dialog.findViewById(R.id.inputIllness);
            EditText illDate = dialog.findViewById(R.id.inputIllnessDate);
            Button save = dialog.findViewById(R.id.saveMedicalBtn);
            save.setOnClickListener(v1 -> {
                List<PetProfileMedical> histList = pet.getMedicalHistory() != null ? pet.getMedicalHistory() : new ArrayList<>();
                histList.add(new PetProfileMedical(illness.getText().toString(), illDate.getText().toString()));
                pet.setMedicalHistory(histList);
                dbRef.child(pet.getKey()).setValue(pet);
                retrieveData(pet.getKey());
                dialog.dismiss();
            });
            dialog.show();
        });
        addMedication.setOnClickListener(v -> {
            dialog.setContentView(R.layout.add_medication);
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            EditText medication = dialog.findViewById(R.id.inputMedication);
            EditText dosage = dialog.findViewById(R.id.inputDosage);
            Button save = dialog.findViewById(R.id.saveMedicalBtn);
            save.setOnClickListener(v1 -> {
                List<PetProfileMedication> medications = pet.getMedications() != null ? pet.getMedications() : new ArrayList<>();
                medications.add(new PetProfileMedication(medication.getText().toString(), dosage.getText().toString()));
                pet.setMedications(medications);
                dbRef.child(pet.getKey()).setValue(pet);
                retrieveData(pet.getKey());
                dialog.dismiss();
            });
            dialog.show();
        });

        retrieveData(null);
    }

    private void retrieveData(@Nullable String key) {
        if(key != null) {
            dbRef.orderByKey().equalTo(key).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            pet = snapshot.getValue(Pet.class);
                            pet.setKey(snapshot.getKey());
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(PetProfile.this, "Error fetching data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        else {
            Intent intent = getIntent();
            pet = (Pet) intent.getSerializableExtra("pet");
        }
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
        else {
            finish();
        }
    }


    public void toBack(View view) {
        Intent next = new Intent(getApplicationContext(), UserProfile.class);
        startActivity(next);
        finish();
    }
}
