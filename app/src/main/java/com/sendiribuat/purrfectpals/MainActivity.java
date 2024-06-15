package com.sendiribuat.purrfectpals;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
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

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    FirebaseDbHelper db;
    FirebaseAuth mAuth;
    User loggedIn;
    TextView welcome, temp;
    Boolean haveSensor, shownHypothermia = false, shownHeatstroke = false;
    private SensorManager sensorManager;
    private Sensor temperature;
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
        temp = findViewById(R.id.tempTxt);
        db = new FirebaseDbHelper(this);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        // Get an instance of the sensor service, and use that to get an instance of
        // a particular sensor.
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE) == null) {
            temp.setText("Sensor not available in this device!");
            haveSensor = false;
        }
        else {
            temperature = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
            haveSensor = true;
        }
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
        startActivity(new Intent(getApplicationContext(), UserProfile.class));
    }

    public void toPetSchedule(View view) {
        startActivity(new Intent(getApplicationContext(), PetScheduleActivity.class));
    }

    public void toFoodRecipe(View view) {
        startActivity(new Intent(getApplicationContext(), FoodRecipe.class));
    }

    public void toFeedingSchedule(View view) {
        startActivity(new Intent(getApplicationContext(), FeedTracking.class));
    }

    public void logoutUser(View view) {
        mAuth.signOut();
        goToLogin();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(temp == null) {
            temp = findViewById(R.id.tempTxt);
        }
        if(event.values[0] <= 0) {
            if(!shownHypothermia) {
                new AlertDialog.Builder(this)
                        .setTitle("Hypothermia Alert!")
                        .setMessage("Check on your pets, make sure they are warm!")
                        .setPositiveButton("Okay", (dialog1, which) -> {
                        })
                        .create().show();
                shownHypothermia = true;
            }
            findViewById(R.id.coldWarning).setVisibility(View.VISIBLE);
        }
        else if(event.values[0] >= 86) {
            if(!shownHeatstroke) {
                new AlertDialog.Builder(this)
                        .setTitle("Heatstroke Alert!")
                        .setMessage("Check on your pets, make sure they are hydrated and cooled!")
                        .setPositiveButton("Okay", (dialog1, which) -> {
                        })
                        .create().show();
                shownHeatstroke = true;
            }
            findViewById(R.id.hotWarning).setVisibility(View.VISIBLE);
        }
        else {
            findViewById(R.id.coldWarning).setVisibility(View.GONE);
            findViewById(R.id.hotWarning).setVisibility(View.GONE);
        }
        temp.setText(event.values[0] + " °C");
        //temp.setText(String.format("%.2f°C", event.values[0]));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        if(temp == null) {
            temp = findViewById(R.id.tempTxt);
        }
    }

    @Override
    protected void onResume() {
        // Register a listener for the sensor.
        super.onResume();
        if(haveSensor) sensorManager.registerListener(this, temperature, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        // Be sure to unregister the sensor when the activity pauses.
        super.onPause();
        if(haveSensor) sensorManager.unregisterListener(this);
    }
}