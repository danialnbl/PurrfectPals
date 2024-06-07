package com.sendiribuat.purrfectpals;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class PetProfile  extends AppCompatActivity {

    ListView medicals, medications;
    FirebaseDbHelper db;

    FirebaseDatabase fd;
    FirebaseAuth mAuth;
    FirebaseUser user;

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

        petName = findViewById(R.id.petName);
        petAge = findViewById(R.id.petAge);
        petType = findViewById(R.id.petType);
        petBreed = findViewById(R.id.petBreed);
        petGender = findViewById(R.id.petGender);
        petColor = findViewById(R.id.petColor);
        ownerName = findViewById(R.id.ownerName);
        ownerNum = findViewById(R.id.ownerNum);

        Pet2 pet = new Pet2("Buddy", "Dog", "Golden Retriever", "Male", "Golden", "Nis", "0132406975", "hanis@gmail.com", "123", 3);

        displayPetData(pet);

        medicals = findViewById(R.id.petMedHistoryList);
        ArrayList<PetProfileMedical> medicalList = new ArrayList<>();
        medicalList.add(new PetProfileMedical("Test", "13/06/2002", "123"));
        medicalList.add(new PetProfileMedical("Test", "13/06/2002", "123"));
        medicalList.add(new PetProfileMedical("Test", "13/06/2002", "123"));
        medicals.setAdapter(new PetProfileMedicalAdapter(this, medicalList));

        medications = findViewById(R.id.petMedicationList);
        ArrayList<PetProfileMedication> medicationList = new ArrayList<>();
        medicationList.add(new PetProfileMedication("Test", "10mg", "123"));
        medicationList.add(new PetProfileMedication("Test", "10mg", "123"));
        medicationList.add(new PetProfileMedication("Test", "10mg", "123"));
        medicationList.add(new PetProfileMedication("Test", "10mg", "123"));
        medications.setAdapter(new PetProfileMedicationAdapter(this, medicationList));

        editProfileBtn = findViewById(R.id.editProfileBtn);
        db = new FirebaseDbHelper(this);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        Dialog dialog = new Dialog(this);
        editProfileBtn.setOnClickListener(v -> {
            dialog.setContentView(R.layout.activity_petprofileedit2);
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        });


//        mAuth = FirebaseAuth.getInstance();
//        db = FirebaseDatabase.getInstance("https://your-database-url");
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//
//        if (currentUser != null) {
//            String userId = currentUser.getUid();
//            userRef = db.getReference("users").child(userId);
//            initializeViews();
//            fetchProfileData();
//        } else {
//            Toast.makeText(this, "No user logged in", Toast.LENGTH_SHORT).show();
//        }
    }

//    private void initializeViews() {
//        textPetName = findViewById(R.id.textPetName);
//        textPetAge = findViewById(R.id.textPetAge);
//        textPetType = findViewById(R.id.textPetType);
//        textPetBreed = findViewById(R.id.textPetBreed);
//        textPetGender = findViewById(R.id.textPetGender);
//        textPetColor = findViewById(R.id.textPetColor);
//        textOwnerName = findViewById(R.id.textOwnerName);
//        textOwnerNum = findViewById(R.id.textOwnerNum);
//    }
//
//    private void fetchProfileData() {
//        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.exists()) {
//                    String petName = snapshot.child("petName").getValue(String.class);
//                    String petAge = snapshot.child("petAge").getValue(String.class);
//                    String petType = snapshot.child("petType").getValue(String.class);
//                    String petBreed = snapshot.child("petBreed").getValue(String.class);
//                    String petGender = snapshot.child("petGender").getValue(String.class);
//                    String petColor = snapshot.child("petColor").getValue(String.class);
//                    String ownerName = snapshot.child("ownerName").getValue(String.class);
//                    String ownerNum = snapshot.child("ownerNum").getValue(String.class);
//
//                    textPetName.setText(petName != null ? petName : "Please update your profile");
//                    textPetAge.setText(petAge != null ? petAge : "");
//                    textPetType.setText(petType != null ? petType : "");
//                    textPetBreed.setText(petBreed != null ? petBreed : "");
//                    textPetGender.setText(petGender != null ? petGender : "");
//                    textPetColor.setText(petColor != null ? petColor : "");
//                    textOwnerName.setText(ownerName != null ? ownerName : "");
//                    textOwnerNum.setText(ownerNum != null ? ownerNum : "");
//
//                } else {
//                    Toast.makeText(PetProfile.this, "Please update your profile", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(PetProfile.this, "Error fetching data", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }



    private void displayPetData(Pet2 pet) {
        userId.setText(pet.getUserId());
        petName.setText(pet.getPetName());
        petAge.setText(String.valueOf(pet.getPetAge()));
        petType.setText(pet.getPetAnimalType());
        petBreed.setText(pet.getPetBreed());
        petGender.setText(pet.getPetGender());
        petColor.setText(pet.getPetColor());
        ownerName.setText(pet.getOwnerName());
        ownerNum.setText(pet.getOwnerNum());
        ownerEmail.setText(pet.getOwnerEmail());
    }

    public void toBack(View view) {
        finish();
    }
}
