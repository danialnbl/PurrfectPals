package com.sendiribuat.purrfectpals;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
    private TextView noRecipeMessage;
    private CardView recipeCardView;
    private RecyclerView recyclerViewRecipes;
    private RecipeAdapter recipeAdapter;
    private List<Recipe> recipeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foodrecipe2);

//        mAuth = FirebaseAuth.getInstance();
//        db = FirebaseDatabase.getInstance(Constant.FIREBASE_DB_INSTANCE);
//        database = db.getReference("recipes");
//
//
//        recipeList = new ArrayList<>();
//        recipeAdapter = new RecipeAdapter(recipeList, this);
//
//        recyclerViewRecipes.setLayoutManager(new LinearLayoutManager(this));
//        recyclerViewRecipes.setAdapter(recipeAdapter);
//
//        checkForRecipes();
    }
//
//    private void checkForRecipes() {
//        database.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                recipeList.clear();
//                if (dataSnapshot.exists() && dataSnapshot.hasChildren()) {
//                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                        Recipe recipe = snapshot.getValue(Recipe.class);
//                        if (recipe != null) {
//                            recipeList.add(recipe);
//                        }
//                    }
//                    noRecipeMessage.setVisibility(View.GONE);
//                    recyclerViewRecipes.setVisibility(View.VISIBLE);
//                } else {
//                    noRecipeMessage.setVisibility(View.VISIBLE);
//                    recyclerViewRecipes.setVisibility(View.GONE);
//                }
//                recipeAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                noRecipeMessage.setText("Error getting recipes.");
//                noRecipeMessage.setVisibility(View.VISIBLE);
//                recipeCardView.setVisibility(View.GONE);
//            }
//        });
//    }

    public void openFoodRecipeAdd(View view) {
        Intent next = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(next);
        finish();
    }
}
