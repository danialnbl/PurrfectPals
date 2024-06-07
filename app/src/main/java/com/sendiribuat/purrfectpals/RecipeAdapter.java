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

//    @NonNull
//    @Override
//    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_foodrecipe, parent, false);
//        return new RecipeViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
//        Recipe recipe = recipes.get(position);
//        holder.textRecipeName.setText(recipe.getRecipeName());
//
//        holder.buttonView.setOnClickListener(v -> {
//            Intent intent = new Intent(context, FoodRecipeView.class);
//            intent.putExtra("recipe", recipe);
//            context.startActivity(intent);
//        });
//
//        holder.buttonDelete.setOnClickListener(v -> {
//            database.child(recipe.getId()).removeValue();
//            recipes.remove(position);
//            notifyItemRemoved(position);
//        });
//    }
//
//    @Override
//    public int getItemCount() {
//        return recipes.size();
//    }
//
//    static class RecipeViewHolder extends RecyclerView.ViewHolder {
//
//        TextView textRecipeName;
//        Button buttonView, buttonDelete;
//
//        public RecipeViewHolder(@NonNull View itemView) {
//            super(itemView);
//            textRecipeName = itemView.findViewById(R.id.recipeName);
//            buttonView = itemView.findViewById(R.id.addRecipeBtn);
//            buttonDelete = itemView.findViewById(R.id.deleteRecipeBtn);
//        }
//    }
}