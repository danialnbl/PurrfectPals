package com.sendiribuat.purrfectpals;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class PetProfile  extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.pet_profile);
        getSupportActionBar().setTitle("Pet Profile");
    }
}
