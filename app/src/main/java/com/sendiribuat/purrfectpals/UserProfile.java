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
//        fd = FirebaseDatabase.getInstance("https://purrfect-pals-e5ca8-default-rtdb.asia-southeast1.firebasedatabase.app");
        user = mAuth.getCurrentUser();

        if (user != null) {
            dbRef = db.getDb().getReference("pets");
        }


//        petListView = findViewById(R.id.petList);
//        petList = new ArrayList<>();
//        petAdapter = new UserPetAdapter(petList, this);
//        petListView.setAdapter(petAdapter);

        petRecyclerView = findViewById(R.id.petList);
        petList = new ArrayList<>();
        petAdapter = new UserPetAdapter(petList);
        petRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        petRecyclerView.setAdapter(petAdapter);

        Button addPetBtn = findViewById(R.id.addPetBtn);
        addPetBtn.setOnClickListener(v -> {
            Intent intent = new Intent(UserProfile.this, PetProfileEdit.class);
            startActivity(intent);
        });

        retrievePets();


    }

    private void retrievePets() {
        dbRef.orderByChild("UserId").equalTo(mAuth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                        User user = snapshot.getValue(User.class);
                        if (user != null) {
                            ownerName.setText(user.getName());
                            ownerEmail.setText(user.getEmail());
                        }

                        Pet pet = snapshot.getValue(Pet.class);
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
        Intent next = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(next);
        finish();
    }

}
