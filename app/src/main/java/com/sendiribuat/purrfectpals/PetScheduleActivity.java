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
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PetScheduleActivity extends AppCompatActivity {

    FirebaseDbHelper db;
    DatabaseReference dbRef;
    FirebaseAuth mAuth;
    FirebaseUser user;
    Button addPetScheduleBtn;
    ListView vScheduleList;
    ValueEventListener eventListener;
    EditText eventName, recLocation, recDate, eventDesc;
    Spinner pet;
    ArrayList<String> petNames;
    ArrayList<PetSchedule> petScheduleList;
    RecyclerView petSchedules;
    List<PetSchedule> dataList;
    PetScheduleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pet_schedule);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Dialog dialog = new Dialog(PetScheduleActivity.this);

        // Firebase
        db = new FirebaseDbHelper(this);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        dbRef = db.getDb().getReference("pet_schedules");

        // Component Init
        addPetScheduleBtn = findViewById(R.id.addPetScheduleBtn);
        petSchedules = findViewById(R.id.vScheduleList);
        petSchedules.setLayoutManager(new LinearLayoutManager(this));

        initListView();

        dataList = new ArrayList<>();
        petSchedules.setAdapter(adapter);

        addPetScheduleBtn.setOnClickListener(v -> {
            dialog.setContentView(R.layout.add_pet_schedule);
            eventName = dialog.findViewById(R.id.recEventName);
            recLocation = dialog.findViewById(R.id.recLocation);
            recDate = dialog.findViewById(R.id.recDate);
            eventDesc = dialog.findViewById(R.id.eventDesc);
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            Button submit = dialog.findViewById(R.id.recAddBtn);
            Spinner addSchePets = dialog.findViewById(R.id.recPetSchedule);

            getPetNames(petNames -> {
                ArrayAdapter<String> petAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, petNames);
                petAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                addSchePets.setAdapter(petAdapter);
            });

            recDate.setOnClickListener(v1 -> {
                // Open DatePickerDialog
                final Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(PetScheduleActivity.this,
                        (view1, selectedYear, selectedMonth, selectedDay) -> {
                            // Set selected date to EditText
                            recDate.setText(selectedYear + "-" + (selectedMonth + 1) + "-" + selectedDay);
                        }, year, month, day);
                datePickerDialog.show();
            });
            submit.setOnClickListener(v1 -> {
                PetSchedule sch = new PetSchedule(eventName.getText().toString(), addSchePets.getSelectedItem().toString(), eventDesc.getText().toString(), recLocation.getText().toString(), recDate.getText().toString(), user.getUid());
                db.insertPetSchedule(sch);
                initListView();
                dialog.dismiss();
            });
            dialog.show();
        });
    }

    private void initListView() {
        petScheduleList = new ArrayList<>();
        Dialog dialog = new Dialog(PetScheduleActivity.this);
        db.getPetSchedules(user.getUid(), new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        PetSchedule schedule = snapshot.getValue(PetSchedule.class);
                        schedule.setKey(snapshot.getKey());
                        petScheduleList.add(schedule);
                    }
                    PetScheduleAdapter schApt = new PetScheduleAdapter(petScheduleList, schedule -> {
                        dialog.setContentView(R.layout.edit_pet_schedule);
                        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        Button submit = dialog.findViewById(R.id.recAddBtn);
                        Button delete = dialog.findViewById(R.id.recRemoveBtn);
                        EditText recEventTitle = dialog.findViewById(R.id.recEventTitle);
                        EditText editeventDesc = dialog.findViewById(R.id.editeventDesc);
                        EditText editLocation = dialog.findViewById(R.id.editLocation);
                        EditText recDate = dialog.findViewById(R.id.recDate);
                        Spinner editPetSchedule = dialog.findViewById(R.id.editPetSchedule);

                        recEventTitle.setText(schedule.getTitle());
                        editeventDesc.setText(schedule.getEvenDesc());
                        editLocation.setText(schedule.getEventLocation());
                        recDate.setText(schedule.getDate());

                        getPetNames(petNames -> {
                            ArrayAdapter<String> petAdapter = new ArrayAdapter<>(PetScheduleActivity.this, android.R.layout.simple_spinner_item, petNames);
                            petAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            editPetSchedule.setAdapter(petAdapter);
                            // Set the selected pet name if necessary
                            editPetSchedule.setSelection(petNames.indexOf(schedule.getPetName()));
                        });

                        recDate.setOnClickListener(v -> {
                            // Open DatePickerDialog
                            final Calendar calendar = Calendar.getInstance();
                            int year = calendar.get(Calendar.YEAR);
                            int month = calendar.get(Calendar.MONTH);
                            int day = calendar.get(Calendar.DAY_OF_MONTH);

                            DatePickerDialog datePickerDialog = new DatePickerDialog(PetScheduleActivity.this,
                                    (view1, selectedYear, selectedMonth, selectedDay) -> {
                                        // Set selected date to EditText
                                        recDate.setText(selectedYear + "-" + (selectedMonth + 1) + "-" + selectedDay);
                                    }, year, month, day);
                            datePickerDialog.show();
                        });
                        submit.setOnClickListener(v -> {
                            schedule.setTitle(recEventTitle.getText().toString());
                            schedule.setPetName(editPetSchedule.getSelectedItem().toString());
                            schedule.setDate(recDate.getText().toString());
                            db.updatePetSchedule(schedule);
                            dialog.dismiss();
                            startActivity(new Intent(PetScheduleActivity.this, PetScheduleActivity.class));
                            finish();
                        });
                        delete.setOnClickListener(v -> {
                            db.deletePetSchedule(schedule.getKey());
                            dialog.dismiss();
                            startActivity(new Intent(PetScheduleActivity.this, PetScheduleActivity.class));
                            finish();
                        });
                        dialog.show();
                    });
                    petSchedules.setAdapter(schApt);
                } else {
                    // No schedules found
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Error fetching data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        petSchedules.setClickable(true);
    }

    private void getPetNames(PetScheduleActivity.PetNamesCallback callback) {
        petNames = new ArrayList<>();
        db.getPets(user.getUid(), new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Pet pet = snapshot.getValue(Pet.class);
                    if (pet != null) {
                        petNames.add(pet.getPetName());
                    }
                }
                callback.onPetNamesLoaded(petNames);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle error
            }
        });
    }

    public interface PetNamesCallback {
        void onPetNamesLoaded(ArrayList<String> petNames);
    }

    public void toBack(View view) {
        finish();
    }
}