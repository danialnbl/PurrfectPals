package com.sendiribuat.purrfectpals;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class FoodRecipe extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_recipe);
        getSupportActionBar().setTitle("Food Recipe");
    }
}
