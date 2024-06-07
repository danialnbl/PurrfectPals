package com.sendiribuat.purrfectpals;

import java.io.Serializable;
import java.util.List;

public class Recipe {

    private String RecipeName, RecipeItem, UserId;

    public Recipe() {
        // Default constructor required for calls to DataSnapshot.getValue(Recipe.class)
    }

    public Recipe(String recipeName, String recipeItem) {
        RecipeName = recipeName;
        RecipeItem = recipeItem;
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


    // Method to get the recipe items as a single string with multiple lines
//    public String getRecipeItemsAsString() {
//        StringBuilder itemsBuilder = new StringBuilder();
//        for (String item : recipeItem) {
//            itemsBuilder.append(item).append("\n"); // Add each item with a new line
//        }
//        return itemsBuilder.toString();
//    }

}