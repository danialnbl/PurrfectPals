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

public class PetProfileMedicalAdapter extends ArrayAdapter<PetProfileMedical> {

    private TextView illness, illnessDate;
    private FirebaseDbHelper db;

    public PetProfileMedicalAdapter(@NonNull Context context, @NonNull List<PetProfileMedical> objects) {
        super(context, R.layout.item_medicalhistory, objects);
        db = new FirebaseDbHelper(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        PetProfileMedical medical = getItem(position);

        if(convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_medicalhistory, parent, false);

        illness = convertView.findViewById(R.id.petProfileIllness);
        illnessDate = convertView.findViewById(R.id.petProfileIllnessDate);

        illness.setText(medical.getIllness());
        illnessDate.setText(medical.getDate());

        return convertView;
    }
}
