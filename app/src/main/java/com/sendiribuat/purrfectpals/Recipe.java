package com.sendiribuat.purrfectpals;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.List;

public class Recipe implements Serializable{

    private String RecipeName, RecipeItem, UserId, Key;

    public Recipe() {
        // Default constructor required for calls to DataSnapshot.getValue(Recipe.class)
    }

    public Recipe(String recipeName, String recipeItem, String userId) {
        RecipeName = recipeName;
        RecipeItem = recipeItem;
        UserId = userId;
    }

    public String getRecipeName() {
        return RecipeName;
    }

    public void setRecipeName(String recipeName) {
        RecipeName = recipeName;
    }

    public String getRecipeItem() {
        return RecipeItem;
    }

    public void setRecipeItem(String recipeItem) {
        RecipeItem = recipeItem;
    }
    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    @Exclude
    public String getKey() {
        return Key;
    }

    @Exclude
    public void setKey(String key) {
        Key = key;
    }
}