package com.sendiribuat.purrfectpals;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class FoodRecipeView  extends AppCompatActivity {

    private TextView textRecipeName;
    private TextView textRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foodrecipeview2);
        getSupportActionBar().setTitle("View Food Recipe");

//        textRecipeName = findViewById(R.id.textRecipeName);
//        textRecipe = findViewById(R.id.textRecipe);
//
//        Recipe recipe = (Recipe) getIntent().getSerializableExtra("recipe");
//
//        if (recipe != null) {
//            textRecipeName.setText(recipe.getRecipeName());
//            textRecipe.setText(recipe.getRecipeItemsAsString());
//        }
    }

    public void openFoodRecipe(View view) {
        Intent next = new Intent(getApplicationContext(), FoodRecipe.class);
        startActivity(next);
        finish();
    }

}
