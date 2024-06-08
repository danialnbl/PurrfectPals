package com.sendiribuat.purrfectpals;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.Arrays;

import androidx.appcompat.app.AppCompatActivity;

public class FoodRecipeAdd extends AppCompatActivity {

    FirebaseDbHelper db;

    DatabaseReference dbRef;
    FirebaseDatabase fd;
    FirebaseAuth mAuth;
    FirebaseUser user;
    private EditText editTextRecipeName;
    private EditText editTextRecipeItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foodrecipeadd2);
//        getSupportActionBar().setTitle("Add Food Recipe");

        db = new FirebaseDbHelper(this);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        dbRef = FirebaseDatabase.getInstance().getReference("recipes");

        editTextRecipeName = findViewById(R.id.inputRecipeName);
        editTextRecipeItem = findViewById(R.id.inputRecipeItem);
//
//        mAuth = FirebaseAuth.getInstance();
//        db = FirebaseDatabase.getInstance(Constant.FIREBASE_DB_INSTANCE);
//        database = db.getReference("recipes");
    }

    public void addRecipe(View view) {
        String recipeName = editTextRecipeName.getText().toString().trim();
        String recipeItem = editTextRecipeItem.getText().toString().trim();

        if (recipeName.isEmpty()) {
            editTextRecipeName.setError("Recipe name is required");
            editTextRecipeName.requestFocus();
            return;
        }

        if (recipeItem.isEmpty()) {
            editTextRecipeItem.setError("Recipe item/ingredient is required");
            editTextRecipeItem.requestFocus();
            return;
        }

        String id = dbRef.push().getKey();
        Recipe recipe = new Recipe(recipeName, recipeItem);
        dbRef.child(id).setValue(recipe);

        Toast.makeText(this, "Recipe added successfully", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, FoodRecipe.class);
        startActivity(intent);
        finish();
    }
}
