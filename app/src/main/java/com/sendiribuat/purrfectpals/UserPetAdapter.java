package com.sendiribuat.purrfectpals;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserPetAdapter extends ArrayAdapter<Pet> {

    private FirebaseDbHelper db;
    private Activity activity;

    public UserPetAdapter(@NonNull Context context, @NonNull List<Pet> pets, Activity activity) {
        super(context, R.layout.item_pet, pets);
        db = new FirebaseDbHelper(context);
        this.activity = activity;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Pet pet = getItem(position);

        if(convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_pet, parent, false);

        TextView petNameTextView = convertView.findViewById(R.id.textPetName);
        TextView petSpeciesTextView = convertView.findViewById(R.id.textPetType);

        petNameTextView.setText(pet.getPetName());
        petSpeciesTextView.setText(pet.getPetAnimalType());

        Button viewPetDetailBtn = convertView.findViewById(R.id.viewPetDetailBtn);
        viewPetDetailBtn.setOnClickListener(v -> {
            Intent viewIntent = new Intent(getContext(), PetProfile.class);
            viewIntent.putExtra("pet", pet);
            getContext().startActivity(viewIntent);
            activity.finish();
        });

        return convertView;
    }
}
