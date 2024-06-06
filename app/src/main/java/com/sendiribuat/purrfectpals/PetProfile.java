package com.sendiribuat.purrfectpals;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class PetProfile  extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_petprofile);
        getSupportActionBar().setTitle("Pet Profile");
    }

    public void openPetProfileEdit(View view) {
        Intent next = new Intent(getApplicationContext(), PetProfileEdit.class);
        startActivity(next);
        finish();
    }
}
