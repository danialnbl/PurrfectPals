package com.sendiribuat.purrfectpals;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class UserPetAdapter extends ArrayAdapter<Pet> {

    private TextView textPetName;
    private TextView textPetType;

    public UserPetAdapter(@NonNull List<Pet> objects, @NonNull Context context) {
        super(context, R.layout.item_pet, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Pet pet = getItem(position);

        if(convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_pet, parent, false);

        textPetName = convertView.findViewById(R.id.textPetName);
        textPetType = convertView.findViewById(R.id.textPetType);

        textPetName.setText(pet.getPetName());
        textPetType.setText(pet.getPetAnimalType());

        return convertView;
    }
}
