package com.sendiribuat.purrfectpals;

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

import com.google.firebase.database.DatabaseReference;

import org.w3c.dom.Text;

import java.util.List;

public class RecipeAdapter extends ArrayAdapter<Recipe> {

    private FirebaseDbHelper db;
    private TextView textRecipeName;
    private Button view, delete;

    public RecipeAdapter(@NonNull List<Recipe> objects, @NonNull Context context) {
        super(context, R.layout.item_foodrecipe, objects);
        db = new FirebaseDbHelper(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Recipe recipe = getItem(position);

        if(convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_foodrecipe, parent, false);

        textRecipeName = convertView.findViewById(R.id.textRecipeName);
        view = convertView.findViewById(R.id.viewRecipeDetailBtn);
        delete = convertView.findViewById(R.id.deleteRecipeBtn);

        textRecipeName.setText(recipe.getRecipeName());
        view.setOnClickListener(v -> {
            Intent viewIntent = new Intent(getContext(), FoodRecipeView.class);
            viewIntent.putExtra("recipe", recipe);
            getContext().startActivity(viewIntent);
        });

        delete.setOnClickListener(v -> {
            db.getDb().getReference("recipes").child(recipe.getKey()).removeValue();
        });

        return convertView;
    }
}