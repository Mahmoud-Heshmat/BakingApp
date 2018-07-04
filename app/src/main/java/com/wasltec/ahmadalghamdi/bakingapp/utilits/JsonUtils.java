package com.wasltec.ahmadalghamdi.bakingapp.utilits;

import android.util.Log;

import com.wasltec.ahmadalghamdi.bakingapp.models.Ingredients;
import com.wasltec.ahmadalghamdi.bakingapp.models.Recipe;
import com.wasltec.ahmadalghamdi.bakingapp.models.Steps;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {


    public static final String mId = "id";
    public static final String mName = "name";
    public static final String mServings = "servings";
    public static final String mImage = "image";
    public static final String mQuantity = "quantity";
    public static final String mMeasure = "measure";
    public static final String mIngredients = "ingredients";
    public static final String mIngredient = "ingredient";
    public static final String mSteps = "steps";
    public static final String mShortDescription = "shortDescription";
    public static final String mDescription = "description";
    public static final String mVideoURL = "videoURL";
    public static final String mThumbnailURL = "thumbnailURL";

    public static ArrayList<Recipe> parseRecipesJson(String json, ArrayList<Recipe> list) {

        ArrayList<Ingredients> ingredientsList = new ArrayList<>();
        ArrayList<Steps> stepsList = new ArrayList<>();

        try {
            JSONArray jsonArray = new JSONArray(json);
            JSONArray ingredientArray = null;
            JSONObject object = null;
            JSONObject ingredientObject = null;
            JSONArray stepsArray = null;
            JSONObject stepsObject = null;

            for (int i = 0 ; i < jsonArray.length() ; i++){
                object = jsonArray.getJSONObject(i);
                String id = object.optString(mId);
                String name = object.optString(mName);
                String serving = object.optString(mServings);
                String image = object.optString(mImage);

                ingredientArray = object.getJSONArray(mIngredients);
                for(int x = 0 ; x<ingredientArray.length(); x++){
                    ingredientObject = ingredientArray.getJSONObject(x);
                    String quantity = ingredientObject.optString(mQuantity);
                    String measure = ingredientObject.optString(mMeasure);
                    String ingredient = ingredientObject.optString(mIngredient);

                    Ingredients ingred = new Ingredients(quantity, measure, ingredient);
                    ingredientsList.add(ingred);
                }

                stepsArray = object.getJSONArray(mSteps);
                for(int x = 0 ; x<stepsArray.length(); x++){
                    stepsObject = stepsArray.getJSONObject(x);
                    String stepID = stepsObject.optString(mId);
                    String shortDescription = stepsObject.optString(mShortDescription);
                    String description = stepsObject.optString(mDescription);
                    String videoURL = stepsObject.optString(mVideoURL);
                    String thumbnailURL = stepsObject.optString(mThumbnailURL);

                    Steps steps = new Steps(stepID, shortDescription, description, videoURL, thumbnailURL);
                    stepsList.add(steps);
                }

                Recipe recipe = new Recipe(id, name, ingredientsList, stepsList, serving, image);
                list.add(recipe);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return list;
    }

}
