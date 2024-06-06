package com.sendiribuat.purrfectpals;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private List<Recipe> recipes;
    private Context context;
    private DatabaseReference database;

    public RecipeAdapter(List<Recipe> recipes, Context context) {
        this.recipes = recipes;
        this.context = context;
        this.database = FirebaseDatabase.getInstance(Constant.FIREBASE_DB_INSTANCE).getReference("recipes");
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_recipe, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        Recipe recipe = recipes.get(position);
        holder.textRecipeName.setText(recipe.getRecipeName());

        holder.buttonView.setOnClickListener(v -> {
            Intent intent = new Intent(context, FoodRecipeView.class);
            intent.putExtra("recipe", recipe);
            context.startActivity(intent);
        });

        holder.buttonDelete.setOnClickListener(v -> {
            database.child(recipe.getId()).removeValue();
            recipes.remove(position);
            notifyItemRemoved(position);
        });
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    static class RecipeViewHolder extends RecyclerView.ViewHolder {

        TextView textRecipeName;
        Button buttonView, buttonDelete;

        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            textRecipeName = itemView.findViewById(R.id.textRecipeName);
            buttonView = itemView.findViewById(R.id.buttonView);
            buttonDelete = itemView.findViewById(R.id.buttonDelete);
        }
    }
}