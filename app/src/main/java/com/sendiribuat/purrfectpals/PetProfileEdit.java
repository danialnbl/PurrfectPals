package com.sendiribuat.purrfectpals;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class PetProfileEdit  extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_petprofileedit);
        getSupportActionBar().setTitle("Edit Pet Profile");
    }

    public void openPetProfile(View view) {
        Intent next = new Intent(getApplicationContext(), PetProfile.class);
        startActivity(next);
        finish();
    }
}
