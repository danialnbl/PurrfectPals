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
    }

    public void toBack(View view) {
        finish();
    }

}
