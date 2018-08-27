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

public class Recipe implements Parcelable{

    @JsonProperty("mId")
    private String mId;
    @JsonProperty("mName")
    private String mName;
    @JsonProperty("mIngredients")
    private ArrayList<Ingredients> mIngredients;
    @JsonProperty("mSteps")
    private ArrayList<Steps> mSteps;
    @JsonProperty("mServings")
    private String mServings;
    @JsonProperty("mImage")
    private String mImage;

    public Recipe(String mId, String mName, ArrayList<Ingredients> mIngredients, ArrayList<Steps> mSteps
            , String mServings, String mImage){

        this.mId = mId;
        this.mName = mName;
        this.mIngredients = mIngredients;
        this.mSteps = mSteps;
        this.mServings = mServings;
        this.mImage = mImage;
    }

    public Recipe(){

        this.mId = "" ;
        this.mName = "";
        this.mIngredients = new ArrayList<>();
        this.mSteps = new ArrayList<>();
        this.mServings = "";
        this.mImage = "";
    }

    // Parcelable
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mImage);
        dest.writeString(this.mServings);
        dest.writeString(this.mName);
        dest.writeList(this.mIngredients);
        dest.writeString(this.mId);
        dest.writeList(this.mSteps);
    }

    protected Recipe(Parcel in) {
        this.mImage = in.readString();
        this.mServings = in.readString();
        this.mName = in.readString();
        this.mIngredients = new ArrayList<>();
        in.readList(this.mIngredients, Ingredients.class.getClassLoader());
        this.mId = in.readString();
        this.mSteps = new ArrayList<>();
        in.readList(this.mSteps, Steps.class.getClassLoader());
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
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public ArrayList<Ingredients> getmIngredients() {
        return mIngredients;
    }

    public void setmIngredients(ArrayList<Ingredients> mIngredients) {
        this.mIngredients = mIngredients;
    }

    public ArrayList<Steps> getmSteps() {
        return mSteps;
    }

    public void setmSteps(ArrayList<Steps> mSteps) {
        this.mSteps = mSteps;
    }

    public String getmServings() {
        return mServings;
    }

    public void setmServings(String mServings) {
        this.mServings = mServings;
    }

    public String getmImage() {
        return mImage;
    }

    public void setmImage(String mImage) {
        this.mImage = mImage;
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
