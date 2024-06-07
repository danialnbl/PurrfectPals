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

public class PetProfileMedicationAdapter extends ArrayAdapter<PetProfileMedication> {

    private TextView medName, medDose;

    public PetProfileMedicationAdapter(@NonNull Context context, @NonNull List<PetProfileMedication> objects) {
        super(context, R.layout.item_medication, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        PetProfileMedication medication = getItem(position);

        if(convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_medication, parent, false);

        medName = convertView.findViewById(R.id.petProfileMedication);
        medDose = convertView.findViewById(R.id.petProfileDosage);

        medName.setText(medication.getMedication());
        medDose.setText(medication.getDosage());

        return convertView;
    }
}
