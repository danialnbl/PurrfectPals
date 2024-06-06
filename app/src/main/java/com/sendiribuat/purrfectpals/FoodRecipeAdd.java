package com.sendiribuat.purrfectpals;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;
import java.util.Arrays;

import androidx.appcompat.app.AppCompatActivity;

public class FoodRecipeAdd extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseDatabase db;
    private EditText editTextRecipeName;
    private EditText editTextRecipeItem;
    private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foodrecipeadd);
        getSupportActionBar().setTitle("Add Food Recipe");

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance(Constant.FIREBASE_DB_INSTANCE);
        database = db.getReference("recipes");

        editTextRecipeName = findViewById(R.id.editTextRecipeName);
        editTextRecipeItem = findViewById(R.id.editTextRecipeItem);
    }

    public void saveRecipe(View view) {
        String recipeName = editTextRecipeName.getText().toString().trim();
        String recipeItemsString = editTextRecipeItem.getText().toString().trim();

        if (!recipeName.isEmpty() && !recipeItemsString.isEmpty()) {
            // Split the recipe items string into a list
            List<String> recipeItemsList = Arrays.asList(recipeItemsString.split("\\s*,\\s*"));

            // Generate a unique key for the recipe
            String recipeId = database.push().getKey();

            // Create a new Recipe object
            Recipe recipe = new Recipe(recipeId, recipeName, recipeItemsList);

            // Save the recipe to the database
            database.child(recipeId).setValue(recipe);

            // Navigate back to the FoodRecipe activity
            finish();
        }
    }

    public void openFoodRecipe(View view) {
        Intent next = new Intent(getApplicationContext(), FoodRecipe.class);
        startActivity(next);
        finish();
    }
}
