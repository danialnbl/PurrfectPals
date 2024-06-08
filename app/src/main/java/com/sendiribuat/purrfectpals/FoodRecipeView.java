package com.sendiribuat.purrfectpals;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class FoodRecipeView  extends AppCompatActivity {

    FirebaseDbHelper db;

    DatabaseReference dbRef;
    FirebaseDatabase fd;
    FirebaseAuth mAuth;
    FirebaseUser user;

    private TextView recipeNameTextView;
    private TextView recipeItemTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foodrecipeview2);
        Intent intent = getIntent();
        Recipe recipe = (Recipe) intent.getSerializableExtra("recipe");

        db = new FirebaseDbHelper(this);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        recipeNameTextView = findViewById(R.id.recipeName);
        recipeItemTextView = findViewById(R.id.recipeItem);
        recipeNameTextView.setText(recipe.getRecipeName());
        recipeItemTextView.setText(recipe.getRecipeItem());

//        Bundle extras = getIntent().getExtras();
//        if (extras != null) {
//            String recipeName = extras.getString("recipeName");
//            retrieveRecipeFromDatabase(recipeName);
//        }
    }

    private void retrieveRecipeFromDatabase(String recipeName) {
        // Get a reference to the "recipes" node in the database
        DatabaseReference recipeRef = FirebaseDatabase.getInstance().getReference("recipes");

        // Query the database to find the recipe with the given name
        recipeRef.child(recipeName).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Recipe found, retrieve its details
                    Recipe recipe = dataSnapshot.getValue(Recipe.class);
                    if (recipe != null) {
                        // Set the retrieved details to the corresponding TextViews
                        recipeNameTextView.setText(recipe.getRecipeName());
                        recipeItemTextView.setText(recipe.getRecipeItem());
                    }
                } else {
                    // Recipe not found, display a message
                    Toast.makeText(FoodRecipeView.this, "Recipe not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle any errors that may occur
                Toast.makeText(FoodRecipeView.this, "Error retrieving recipe", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void toBack(View view) {
        Intent next = new Intent(getApplicationContext(), FoodRecipe.class);
        startActivity(next);
        finish();
    }

}
