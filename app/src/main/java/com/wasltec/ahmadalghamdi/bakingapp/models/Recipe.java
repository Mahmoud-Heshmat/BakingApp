package com.wasltec.ahmadalghamdi.bakingapp.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Base64;
import android.util.Log;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Recipe implements Parcelable {

    @JsonProperty("id")
    private String id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("ingredients")
    private ArrayList<Ingredients> ingredients;
    @JsonProperty("steps")
    private ArrayList<Steps> steps;
    @JsonProperty("servings")
    private String servings;
    @JsonProperty("image")
    private String image;


    public Recipe(String id, String name, ArrayList<Ingredients> ingredients, ArrayList<Steps> steps, String servings, String image) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.steps = steps;
        this.servings = servings;
        this.image = image;
    }

    public Recipe(){

        this.id = "" ;
        this.name = "";
        this.ingredients = new ArrayList<>();
        this.steps = new ArrayList<>();
        this.servings = "";
        this.image = "";
    }

    // Parcelable
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.image);
        dest.writeString(this.servings);
        dest.writeString(this.name);
        dest.writeList(this.ingredients);
        dest.writeString(this.id);
        dest.writeList(this.steps);
    }

    protected Recipe(Parcel in) {
        this.image = in.readString();
        this.servings = in.readString();
        this.name = in.readString();
        this.ingredients = new ArrayList<>();
        in.readList(this.ingredients, Ingredients.class.getClassLoader());
        this.id = in.readString();
        this.steps = new ArrayList<>();
        in.readList(this.steps, Steps.class.getClassLoader());
    }

    public static final Parcelable.Creator<Recipe> CREATOR = new Parcelable.Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel source) {
            return new Recipe(source);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    public String getmId() {
        return id;
    }

    public void setmId(String mId) {
        this.id = mId;
    }

    public String getmName() {
        return name;
    }

    public void setmName(String mName) {
        this.name = mName;
    }

    public ArrayList<Ingredients> getmIngredients() {
        return ingredients;
    }

    public void setmIngredients(ArrayList<Ingredients> mIngredients) {
        this.ingredients = mIngredients;
    }

    public ArrayList<Steps> getmSteps() {
        return steps;
    }

    public void setmSteps(ArrayList<Steps> mSteps) {
        this.steps = mSteps;
    }

    public String getmServings() {
        return servings;
    }

    public void setmServings(String mServings) {
        this.servings = mServings;
    }

    public String getmImage() {
        return image;
    }

    public void setmImage(String mImage) {
        this.image = mImage;
    }

    public static String toBase64String(Recipe recipe) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return Base64.encodeToString(mapper.writeValueAsBytes(recipe), 0);
        } catch (JsonProcessingException e) {
            Log.d("responseBase", e.getMessage());
        }
        return null;


    }

    public static Recipe fromBase64(String encoded) {
        if (!"".equals(encoded)) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                return mapper.readValue(Base64.decode(encoded, 0), Recipe.class);
            } catch (IOException e) {
                Log.d("responseBase", e.getMessage());
            }
        }
        return null;
    }
}
