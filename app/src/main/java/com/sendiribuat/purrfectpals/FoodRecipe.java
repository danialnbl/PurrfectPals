package com.sendiribuat.purrfectpals;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;

public class FoodRecipe extends AppCompatActivity {

    FirebaseDbHelper db;

    DatabaseReference dbRef;
    FirebaseDatabase fd;
    FirebaseAuth mAuth;
    FirebaseUser user;

    Button addRecipeBtn;
    private TextView noRecipeMessage;
    private CardView recipeCardView;
    private ListView listViewRecipes;
    private RecipeAdapter recipeAdapter;
    private List<Recipe> recipeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foodrecipe2);

        db = new FirebaseDbHelper(this);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        dbRef = db.getDb().getReference("recipes");

        noRecipeMessage = findViewById(R.id.noRecipeMessage);
        listViewRecipes = findViewById(R.id.foodRecipeList);
        recipeList = new ArrayList<>();
        recipeAdapter = new RecipeAdapter(recipeList, this);
        listViewRecipes.setAdapter(recipeAdapter);

        Button addRecipeBtn = findViewById(R.id.addRecipeBtn);
        addRecipeBtn.setOnClickListener(v -> {
            Intent intent = new Intent(FoodRecipe.this, FoodRecipeAdd.class);
            startActivity(intent);
            finish();
        });
        listViewRecipes.setOnItemClickListener((parent, view, position, id) -> {

        });

        checkForRecipes();
    }

    private void checkForRecipes() {
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                recipeList.clear();
                if (dataSnapshot.exists() && dataSnapshot.hasChildren()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Recipe recipe = snapshot.getValue(Recipe.class);
                        recipe.setKey(snapshot.getKey());
                        if (recipe != null) {
                            recipeList.add(recipe);
                        }
                    }
                    noRecipeMessage.setVisibility(View.GONE);
                    listViewRecipes.setVisibility(View.VISIBLE);
                } else {
                    noRecipeMessage.setVisibility(View.VISIBLE);
                    listViewRecipes.setVisibility(View.GONE);
                }
                recipeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                noRecipeMessage.setText("Error getting recipes.");
                noRecipeMessage.setVisibility(View.VISIBLE);
            }
        });
    }

    public void openFoodRecipeAdd(View view) {
        Intent next = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(next);
        finish();
    }

    public void toBack(View view) {
        finish();
    }
}
