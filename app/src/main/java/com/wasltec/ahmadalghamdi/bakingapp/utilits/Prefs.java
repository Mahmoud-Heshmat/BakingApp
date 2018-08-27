package com.wasltec.ahmadalghamdi.bakingapp.utilits;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.wasltec.ahmadalghamdi.bakingapp.R;
import com.wasltec.ahmadalghamdi.bakingapp.models.Recipe;


public class Prefs {
    public static final String PREFS_NAME = "prefs";


    public static void saveRecipe(Context context, Recipe recipe) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit();
        Gson gson = new Gson();
        String json = gson.toJson(recipe);
        prefs.putString(context.getString(R.string.widget_recipe_key), json);
        prefs.apply();
    }

    public static Recipe loadRecipe(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString(context.getString(R.string.widget_recipe_key), "");
        Recipe recipe = gson.fromJson(json, Recipe.class);
        return recipe;
    }
}
