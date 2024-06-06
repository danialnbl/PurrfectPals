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

public class VaccinationRecordAdapter extends ArrayAdapter<VaccinationRecord> {
    private TextView title, petName, location, date;
    public VaccinationRecordAdapter(@NonNull Context context, @NonNull List<VaccinationRecord> objects) {
        super(context, R.layout.record_item, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        VaccinationRecord record = getItem(position);

        if(convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.record_item, parent, false);

        title = convertView.findViewById(R.id.recordTitle);
        petName = convertView.findViewById(R.id.recordPet);
        location = convertView.findViewById(R.id.recordLocation);
        date = convertView.findViewById(R.id.recordDate);

        title.setText(record.getTitle());
        petName.setText(record.getPetName());
        location.setText(record.getLocation());
        date.setText(record.getDate());

        return convertView;
    }
}
