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

public class VaccinationScheduleAdapter extends ArrayAdapter<VaccinationSchedule> {
    private TextView title, petName, date;

    public VaccinationScheduleAdapter(@NonNull Context context, @NonNull List<VaccinationSchedule> objects) {
        super(context, R.layout.schedule_item, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        VaccinationSchedule schedule = getItem(position);

        if(convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.schedule_item, parent, false);

        title = convertView.findViewById(R.id.scheduleTitle);
        petName = convertView.findViewById(R.id.schedulePetName);
        date = convertView.findViewById(R.id.scheduleDate);

        title.setText(schedule.getTitle());
        petName.setText(schedule.getPetName());
        date.setText(schedule.getDate());

        return convertView;
    }
}
