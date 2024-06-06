package com.sendiribuat.purrfectpals;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class VaccinationActivity extends AppCompatActivity {
    ListView schedules, records;
    FirebaseDbHelper db;
    FirebaseAuth mAuth;
    FirebaseUser user;
    Button addSchedule, addRec;
    EditText type, location, date;
    Spinner pet;
    String[] petNames;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_vaccination);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        schedules = findViewById(R.id.vScheduleList);
        List<VaccinationSchedule> scheduleList = new ArrayList<>();
        schedules.setAdapter(new VaccinationScheduleAdapter(this, scheduleList));

        records = findViewById(R.id.vRecordList);
        List<VaccinationRecord> recordsList = new ArrayList<>();
        records.setAdapter(new VaccinationRecordAdapter(this, recordsList));

        addSchedule = findViewById(R.id.addScheduleBtn);
        addRec = findViewById(R.id.addRecBtn);
        db = new FirebaseDbHelper(this);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        Dialog dialog = new Dialog(this);
        addRec.setOnClickListener(v -> {
            dialog.setContentView(R.layout.add_vaccine_record);
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            Button submit = dialog.findViewById(R.id.recAddBtn);
            db.getPets(user.getUid(), new FirebaseDbHelper.OnPetNamesLoadedListener() {
                @Override
                public void onPetNamesLoaded(String[] petNameArray) {
                    petNames = petNameArray;
                }
                @Override
                public void onLoadFailed(String errorMessage) {
                    Toast.makeText(getApplicationContext(), "Failed to load pet names: " + errorMessage, Toast.LENGTH_SHORT).show();
                }
            });
            submit.setOnClickListener(v1 -> {
                type = dialog.findViewById(R.id.recVacType);
                location = dialog.findViewById(R.id.recLocation);
                date = dialog.findViewById(R.id.recDate);
                pet = dialog.findViewById(R.id.recPet);

                VaccinationRecord rec = new VaccinationRecord(type.getText().toString(), location.getText().toString(),
                        pet.getSelectedItem().toString(), date.getText().toString(), user.getUid());
                db.insertVaccineRecord(rec);
                dialog.dismiss();
            });
        });
    }
}