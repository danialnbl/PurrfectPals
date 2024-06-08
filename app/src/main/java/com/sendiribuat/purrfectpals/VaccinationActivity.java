package com.sendiribuat.purrfectpals;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
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
    ArrayList<String> petNames;
    ArrayList<VaccinationSchedule> scheduleList = new ArrayList<>();
    ArrayList<VaccinationRecord> recordsList = new ArrayList<>();

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
        Dialog dialog = new Dialog(this);
        db = new FirebaseDbHelper(this);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        db.getPets(user.getUid(), new FirebaseDbHelper.OnPetNamesLoadedListener() {
            @Override
            public void onPetNamesLoaded(ArrayList<String> petNameArray) {
                petNames = petNameArray;
            }
            @Override
            public void onLoadFailed(String errorMessage) {
                Toast.makeText(getApplicationContext(), "Failed to load pet names: " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });

        schedules = findViewById(R.id.vScheduleList);
        records = findViewById(R.id.vRecordList);
        scheduleList.add(new VaccinationSchedule("Test", "PetTest", "13/06/2002", "123"));
        scheduleList.add(new VaccinationSchedule("Test", "PetTest", "13/06/2002", "123"));
        scheduleList.add(new VaccinationSchedule("Test", "PetTest", "13/06/2002", "123"));
        scheduleList.add(new VaccinationSchedule("Test", "PetTest", "13/06/2002", "123"));
        recordsList.add(new VaccinationRecord("Test", "PetTest", "Location", "13/06/2002", "123"));
        recordsList.add(new VaccinationRecord("Test", "PetTest", "Location", "13/06/2002", "123"));
        recordsList.add(new VaccinationRecord("Test", "PetTest", "Location", "13/06/2002", "123"));
        recordsList.add(new VaccinationRecord("Test", "PetTest", "Location", "13/06/2002", "123"));
        schedules.setOnItemClickListener((parent, view, position, id) -> {
            TextView schTitle = view.findViewById(R.id.scheduleTitle);
            TextView schDate = view.findViewById(R.id.scheduleDate);
            TextView schKey = view.findViewById(R.id.scheduleKey);

            dialog.setContentView(R.layout.edit_vac_schedule);
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            Button submit = dialog.findViewById(R.id.schAddBtn);
            Button delete = dialog.findViewById(R.id.schRemoveBtn);
            EditText schLocation = dialog.findViewById(R.id.editSchLocation);
            Spinner schePet = dialog.findViewById(R.id.editSchPet);
            EditText scheDate = dialog.findViewById(R.id.editSchDate);
            TextView scheKey = dialog.findViewById(R.id.editSchKey);

            schLocation.setText(schTitle.getText().toString());
            scheDate.setText(schDate.getText().toString());
            scheKey.setText(schKey.getText().toString());

            ArrayAdapter<String> petAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, petNames);
            petAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            schePet.setAdapter(petAdapter);

            submit.setOnClickListener(v -> {
                db.updateVaccineSchedule(new VaccinationSchedule(scheKey.getText().toString(), schLocation.getText().toString(),
                        schePet.getSelectedItem().toString(), scheDate.getText().toString()));
                dialog.dismiss();
                startActivity(new Intent(this, VaccinationActivity.class));
                finish();
            });
            delete.setOnClickListener(v -> {
                db.deleteVaccineSchedule(schKey.getText().toString());
                dialog.dismiss();
                startActivity(new Intent(this, VaccinationActivity.class));
                finish();
            });
            dialog.show();
        });
        records.setOnItemClickListener((parent, view, position, id) -> {
            TextView recTitle = view.findViewById(R.id.recordTitle);
            TextView recLocation = view.findViewById(R.id.recordLocation);
            TextView recDate = view.findViewById(R.id.recordDate);
            TextView recKey = view.findViewById(R.id.recKey);

            dialog.setContentView(R.layout.edit_vac_schedule);
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            Button submit = dialog.findViewById(R.id.recAddBtn);
            Button delete = dialog.findViewById(R.id.recRemoveBtn);
            EditText vacRType = dialog.findViewById(R.id.recVacType);
            EditText vacRLocation = dialog.findViewById(R.id.recLocation);
            EditText vacDate = dialog.findViewById(R.id.recDate);
            Spinner petName = dialog.findViewById(R.id.recPet);

            petNames.add("SI KACAKS");
            ArrayAdapter<String> petAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, petNames);
            petAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            records.setAdapter(petAdapter);

            submit.setOnClickListener(v -> {
                VaccinationRecord record = new VaccinationRecord(recKey.getText().toString(), vacRType.getText().toString(), petName.getSelectedItem().toString(), vacRLocation.getText().toString(), vacDate.getText().toString());
                db.updateVaccineRecord(record);
                startActivity(new Intent(this, VaccinationActivity.class));
                finish();
            });
            delete.setOnClickListener(v -> {
                db.deleteVaccineRecord(recKey.getText().toString());
                dialog.dismiss();
                startActivity(new Intent(this, VaccinationActivity.class));
                finish();
            });
            dialog.show();
        });

        addSchedule = findViewById(R.id.addScheduleBtn);
        addRec = findViewById(R.id.addRecBtn);

        addSchedule.setOnClickListener(v -> {
            dialog.setContentView(R.layout.add_vaccine_schedule);
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            Button submit = dialog.findViewById(R.id.schAddBtn);
            submit.setOnClickListener(v1 -> {
                location = dialog.findViewById(R.id.schLocation);
                date = dialog.findViewById(R.id.schDate);
                pet = dialog.findViewById(R.id.schPet);

                VaccinationSchedule sch = new VaccinationSchedule(location.getText().toString(),
                        pet.getSelectedItem().toString(), date.getText().toString(), user.getUid());
                db.insertVaccineSchedule(sch);
                dialog.dismiss();
            });
            dialog.show();
        });
        addRec.setOnClickListener(v -> {
            dialog.setContentView(R.layout.add_vaccine_record);
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            Button submit = dialog.findViewById(R.id.recAddBtn);
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
            dialog.show();
        });
    }

    private void initListView() {
        schedules.setAdapter(new VaccinationScheduleAdapter(this, scheduleList));
        schedules.setClickable(true);
        records.setAdapter(new VaccinationRecordAdapter(this, recordsList));
        records.setClickable(true);
    }

    public void toBack(View view) {
        finish();
    }
}