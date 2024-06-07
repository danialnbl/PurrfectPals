package com.sendiribuat.purrfectpals;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    FirebaseDbHelper db;
    FirebaseAuth mAuth;
    User loggedIn;
    TextView welcome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        welcome = findViewById(R.id.welcomeTxt);
        db = new FirebaseDbHelper(this);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        if(user != null) {
            db.getUserInformation(user.getUid(), new FirebaseDbHelper.UserCallback() {
                @Override
                public void onUserCallback(User user) {
                    loggedIn = user;
                    welcome.setText("Welcome, " + user.getName());
                }
            });
        } else {
            goToLogin();
        }
    }

    public void goToLogin() {
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }

    public void toVaccine(View view) {
        startActivity(new Intent(getApplicationContext(), VaccinationActivity.class));
    }

    public void toPetProfile(View view) {
        startActivity(new Intent(getApplicationContext(), PetProfile.class));
        finish();
    }

    public void toPetSchedule(View view) {
        startActivity(new Intent(getApplicationContext(), PetSchedule.class));
    }

    public void toFoodRecipe(View view) {
        startActivity(new Intent(getApplicationContext(), FoodRecipe.class));
    }

    public void toFeedingSchedule(View view) {
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
    }

    public void logoutUser(View view) {
        mAuth.signOut();
        goToLogin();
    }
}