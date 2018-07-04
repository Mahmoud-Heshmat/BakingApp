package com.wasltec.ahmadalghamdi.bakingapp.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Recipe implements Serializable{

    private String mId;
    private String mName;
    private ArrayList<Ingredients> mIngredients;
    private ArrayList<Steps> mSteps;
    private String mServings;
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
}
