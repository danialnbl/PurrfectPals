package com.sendiribuat.purrfectpals;

import android.app.DatePickerDialog;
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
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class VaccinationActivity extends AppCompatActivity {
    RecyclerView schedules, records;
    FirebaseDbHelper db;
    FirebaseAuth mAuth;
    FirebaseUser user;
    Button addSchedule, addRec;
    EditText type, location, date;
    Spinner pet;
    ArrayList<String> petNames;
    ArrayList<VaccinationSchedule> scheduleList;
    ArrayList<VaccinationRecord> recordsList;

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

        schedules = findViewById(R.id.vScheduleList);
        schedules.setLayoutManager(new LinearLayoutManager(this));
        records = findViewById(R.id.vRecordList);
        records.setLayoutManager(new LinearLayoutManager(this));

        initListView();

        addSchedule = findViewById(R.id.addScheduleBtn);
        addRec = findViewById(R.id.addRecBtn);

        addSchedule.setOnClickListener(v -> {
            location = dialog.findViewById(R.id.schLocation);
            date = dialog.findViewById(R.id.schDate);
            pet = dialog.findViewById(R.id.schPet);
            dialog.setContentView(R.layout.add_vaccine_schedule);
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            Button submit = dialog.findViewById(R.id.schAddBtn);
            Spinner addSchePets = dialog.findViewById(R.id.schPet);

            populatePetNames();
            ArrayAdapter<String> petAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, petNames);
            petAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            addSchePets.setAdapter(petAdapter);
            date.setOnClickListener(v1 -> {
                // Open DatePickerDialog
                final Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(VaccinationActivity.this,
                        (view1, selectedYear, selectedMonth, selectedDay) -> {
                            // Set selected date to EditText
                            date.setText(selectedYear + "-" + (selectedMonth + 1) + "-" + selectedDay);
                        }, year, month, day);
                datePickerDialog.show();
            });
            submit.setOnClickListener(v1 -> {
                VaccinationSchedule sch = new VaccinationSchedule(location.getText().toString(),
                        pet.getSelectedItem().toString(), date.getText().toString(), user.getUid());
                db.insertVaccineSchedule(sch);
                initListView();
                dialog.dismiss();
            });
            dialog.show();
        });
        addRec.setOnClickListener(v -> {
            dialog.setContentView(R.layout.add_vaccine_record);
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            Button submit = dialog.findViewById(R.id.recAddBtn);
            Spinner addRecPets = dialog.findViewById(R.id.recPet);
            type = dialog.findViewById(R.id.recVacType);
            location = dialog.findViewById(R.id.recLocation);
            date = dialog.findViewById(R.id.recDate);
            pet = dialog.findViewById(R.id.recPet);

            populatePetNames();
            ArrayAdapter<String> petAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, petNames);
            petAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            addRecPets.setAdapter(petAdapter);
            date.setOnClickListener(v1 -> {
                // Open DatePickerDialog
                final Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(VaccinationActivity.this,
                        (view1, selectedYear, selectedMonth, selectedDay) -> {
                            // Set selected date to EditText
                            date.setText(selectedYear + "-" + (selectedMonth + 1) + "-" + selectedDay);
                        }, year, month, day);
                datePickerDialog.show();
            });
            submit.setOnClickListener(v1 -> {
                VaccinationRecord rec = new VaccinationRecord(type.getText().toString(), pet.getSelectedItem().toString(),
                        location.getText().toString(), date.getText().toString(), user.getUid());
                db.insertVaccineRecord(rec);
                initListView();
                dialog.dismiss();
            });
            dialog.show();
        });
    }

    private void initListView() {
        scheduleList = new ArrayList<>();
        recordsList = new ArrayList<>();
        Dialog dialog = new Dialog(VaccinationActivity.this);
        db.getVaccineSchedules(user.getUid(), new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        VaccinationSchedule schedule = snapshot.getValue(VaccinationSchedule.class);
                        schedule.setKey(snapshot.getKey());
                        scheduleList.add(schedule);
                    }
                    VaccinationScheduleAdapter schApt = new VaccinationScheduleAdapter(scheduleList, schedule -> {
                        dialog.setContentView(R.layout.edit_vac_schedule);
                        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        Button submit = dialog.findViewById(R.id.schAddBtn);
                        Button delete = dialog.findViewById(R.id.schRemoveBtn);
                        EditText schLocation = dialog.findViewById(R.id.editSchLocation);
                        Spinner schePet = dialog.findViewById(R.id.editSchPet);
                        EditText scheDate = dialog.findViewById(R.id.editSchDate);

                        schLocation.setText(schedule.getTitle());
                        scheDate.setText(schedule.getDate());

                        populatePetNames();
                        ArrayAdapter<String> petAdapter = new ArrayAdapter<>(VaccinationActivity.this, android.R.layout.simple_spinner_item, petNames);
                        petAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        schePet.setAdapter(petAdapter);

                        scheDate.setOnClickListener(v -> {
                            // Open DatePickerDialog
                            final Calendar calendar = Calendar.getInstance();
                            int year = calendar.get(Calendar.YEAR);
                            int month = calendar.get(Calendar.MONTH);
                            int day = calendar.get(Calendar.DAY_OF_MONTH);

                            DatePickerDialog datePickerDialog = new DatePickerDialog(VaccinationActivity.this,
                                    (view1, selectedYear, selectedMonth, selectedDay) -> {
                                        // Set selected date to EditText
                                        scheDate.setText(selectedYear + "-" + (selectedMonth + 1) + "-" + selectedDay);
                                    }, year, month, day);
                            datePickerDialog.show();
                        });
                        submit.setOnClickListener(v -> {
                            schedule.setTitle(schLocation.getText().toString());
                            schedule.setPetName(schePet.getSelectedItem().toString());
                            schedule.setDate(scheDate.getText().toString());
                            db.updateVaccineSchedule(schedule);
                            dialog.dismiss();
                            initListView();
                        });
                        delete.setOnClickListener(v -> {
                            db.deleteVaccineSchedule(schedule.getKey());
                            dialog.dismiss();
                            initListView();
                        });
                        dialog.show();
                    });
                    schedules.setAdapter(schApt);
                } else {
                    // No schedules found
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Error fetching data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        db.getVaccineRecords(user.getUid(), new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        VaccinationRecord record = snapshot.getValue(VaccinationRecord.class);
                        record.setKey(snapshot.getKey());
                        recordsList.add(record);
                    }
                    VaccinationRecordAdapter recApt = new VaccinationRecordAdapter(recordsList, record -> {
                        populatePetNames();
                        dialog.setContentView(R.layout.edit_vac_record);
                        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        Button submit = dialog.findViewById(R.id.recAddBtn);
                        Button delete = dialog.findViewById(R.id.recRemoveBtn);
                        EditText vacRType = dialog.findViewById(R.id.recVacType);
                        EditText vacRLocation = dialog.findViewById(R.id.recLocation);
                        EditText vacDate = dialog.findViewById(R.id.recDate);
                        Spinner petName = dialog.findViewById(R.id.recPet);

                        vacRType.setText(record.getTitle());
                        vacRLocation.setText(record.getLocation());
                        vacDate.setText(record.getDate());

                        ArrayAdapter<String> petAdapter = new ArrayAdapter<>(VaccinationActivity.this, android.R.layout.simple_spinner_item, petNames);
                        petAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        petName.setAdapter(petAdapter);

                        vacDate.setOnClickListener(v -> {
                            // Open DatePickerDialog
                            final Calendar calendar = Calendar.getInstance();
                            int year = calendar.get(Calendar.YEAR);
                            int month = calendar.get(Calendar.MONTH);
                            int day = calendar.get(Calendar.DAY_OF_MONTH);

                            DatePickerDialog datePickerDialog = new DatePickerDialog(VaccinationActivity.this,
                                    (view1, selectedYear, selectedMonth, selectedDay) -> {
                                        // Set selected date to EditText
                                        vacDate.setText(selectedYear + "-" + (selectedMonth + 1) + "-" + selectedDay);
                                    }, year, month, day);
                            datePickerDialog.show();
                        });
                        submit.setOnClickListener(v -> {
                            record.setTitle(vacRType.getText().toString());
                            record.setPetName(petName.getSelectedItem().toString());
                            record.setLocation(vacRLocation.getText().toString());
                            record.setDate(vacDate.getText().toString());
                            db.updateVaccineRecord(record);
                            dialog.dismiss();
                            initListView();
                        });
                        delete.setOnClickListener(v -> {
                            db.deleteVaccineRecord(record.getKey());
                            dialog.dismiss();
                            initListView();
                        });
                        dialog.show();
                    });
                    records.setAdapter(recApt);
                    // Process fetched schedules here
                } else {
                    // No schedules found
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Error fetching data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        schedules.setClickable(true);
        records.setClickable(true);
    }

    private void populatePetNames() {
        petNames = new ArrayList<>();
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
        petNames.add("SI KACAKS");
        petNames.add("SI KACAKS");
        petNames.add("SI KACAKS");
    }

    public void toBack(View view) {
        finish();
    }
}