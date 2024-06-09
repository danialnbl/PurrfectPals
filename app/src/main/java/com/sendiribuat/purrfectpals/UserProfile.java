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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserProfile  extends AppCompatActivity {

    FirebaseDbHelper db;

    DatabaseReference dbRef;
    FirebaseDatabase fd;
    FirebaseAuth mAuth;
    FirebaseUser user;

    private RecyclerView petRecyclerView;
    private ListView petListView;
    private List<Pet> petList;
    private UserPetAdapter petAdapter;

    private TextView ownerName;
    private TextView ownerEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_userprofile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mAuth = FirebaseAuth.getInstance();
        db = new FirebaseDbHelper(this);
        user = mAuth.getCurrentUser();
        dbRef = db.getDb().getReference("pets");

        if(user != null) {
            db.getUserInformation(user.getUid(), new FirebaseDbHelper.UserCallback() {
                @Override
                public void onUserCallback(User user) {
                    ownerName = findViewById(R.id.ownerName);
                    ownerEmail = findViewById(R.id.ownerEmail);
                    ownerName.setText(user.getName());
                    ownerEmail.setText(user.getEmail());

                }
            });
        }

        petListView = findViewById(R.id.petList);
        petList = new ArrayList<>();
        petAdapter = new UserPetAdapter(this, petList, this);
        petListView.setAdapter(petAdapter);

        Button addPetBtn = findViewById(R.id.addPetBtn);
        addPetBtn.setOnClickListener(v -> {
            Intent intent = new Intent(UserProfile.this, PetProfileEdit.class);
            startActivity(intent);
        });
        petListView.setOnItemClickListener((parent, view, position, id) -> {

        });

        retrievePets();

    }

    private void retrievePets() {
        dbRef.orderByChild("userId").equalTo(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                petList.clear();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Pet pet = snapshot.getValue(Pet.class);
                        pet.setKey(snapshot.getKey());
                        if (pet != null) {
                            petList.add(pet);
                        }
                    }
                    petAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle database error
            }
        });
    }


    public void toBack(View view) {
        finish();
    }

}
