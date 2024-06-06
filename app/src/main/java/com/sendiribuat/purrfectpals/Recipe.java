package com.sendiribuat.purrfectpals;

import java.io.Serializable;
import java.util.List;

public class Recipe implements Serializable {
    private String id;
    private String recipeName;
    private List<String> recipeItems; // List of recipe items

    public Recipe() {
        // Default constructor required for calls to DataSnapshot.getValue(Recipe.class)
    }

    public Recipe(String id, String recipeName, List<String> recipeItems) {
        this.id = id;
        this.recipeName = recipeName;
        this.recipeItems = recipeItems;
    }

    public String getId() {
        return id;
    }

    public String getRecipeName() {
        return recipeName;
    }

    // Method to get the recipe items as a single string with multiple lines
    public String getRecipeItemsAsString() {
        StringBuilder itemsBuilder = new StringBuilder();
        for (String item : recipeItems) {
            itemsBuilder.append(item).append("\n"); // Add each item with a new line
        }
        return itemsBuilder.toString();
    }

}