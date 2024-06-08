package com.sendiribuat.purrfectpals;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserPetAdapter extends RecyclerView.Adapter<UserPetAdapter.PetViewHolder> {

    private List<Pet> petList;

    public UserPetAdapter(List<Pet> petList) {
        this.petList = petList;
    }

    @NonNull
    @Override
    public PetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pet, parent, false);
        return new PetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PetViewHolder holder, int position) {
        Pet pet = petList.get(position);
        holder.petName.setText(pet.getPetName());
        holder.petType.setText(pet.getPetAnimalType());
        holder.petBreed.setText(pet.getPetBreed());
        // Set other fields as needed
    }

    @Override
    public int getItemCount() {
        return petList.size();
    }

    static class PetViewHolder extends RecyclerView.ViewHolder {
        TextView petName, petType, petBreed;
        // Add other fields as needed

        PetViewHolder(@NonNull View itemView) {
            super(itemView);
            petName = itemView.findViewById(R.id.petName);
            petType = itemView.findViewById(R.id.petType);
            petBreed = itemView.findViewById(R.id.petBreed);
            // Initialize other fields as needed
        }
    }

//    public UserPetAdapter(@NonNull List<Pet> objects, @NonNull Context context) {
//        super(context, R.layout.item_pet, objects);
//    }
//
//    @NonNull
//    @Override
//    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        Pet pet = getItem(position);
//
//        if(convertView == null)
//            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_pet, parent, false);
//
//        textPetName = convertView.findViewById(R.id.textPetName);
//        textPetType = convertView.findViewById(R.id.textPetType);
//
//        textPetName.setText(pet.getPetName());
//        textPetType.setText(pet.getPetAnimalType());
//
//        return convertView;
//    }
}
