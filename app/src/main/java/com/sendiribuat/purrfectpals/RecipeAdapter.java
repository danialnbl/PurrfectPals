package com.sendiribuat.purrfectpals;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.w3c.dom.Text;

import java.util.List;

public class RecipeAdapter extends ArrayAdapter<Recipe> {

    private TextView textRecipeName;

    public RecipeAdapter(@NonNull List<Recipe> objects, @NonNull Context context) {
        super(context, R.layout.item_foodrecipe, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Recipe recipe = getItem(position);

        if(convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_medicalhistory, parent, false);

        textRecipeName = convertView.findViewById(R.id.textRecipeName);

        textRecipeName.setText(recipe.getRecipeName());

        return convertView;
    }
}