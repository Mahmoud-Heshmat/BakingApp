package com.wasltec.ahmadalghamdi.bakingapp.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Ingredients implements Parcelable {

    private String quantity;
    private String measure;
    private String ingredient;

    public Ingredients(String quantity, String measure, String ingredient) {
        this.quantity = quantity;
        this.measure = measure;
        this.ingredient = ingredient;
    }

    public Ingredients(Parcel in) {
        quantity = in.readString();
        measure = in.readString();
        ingredient = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(quantity);
        dest.writeString(measure);
        dest.writeString(ingredient);
    }

    // This is to de-serialize the object
    public static final Parcelable.Creator<Ingredients> CREATOR = new Parcelable.Creator<Ingredients>(){
        public Ingredients createFromParcel(Parcel in) {
            return new Ingredients(in);
        }

        public Ingredients[] newArray(int size) {
            return new Ingredients[size];
        }
    };

    public String getmQuantity() {
        return quantity;
    }

    public void setmQuantity(String mQuantity) {
        this.quantity = mQuantity;
    }

    public String getmMeasure() {
        return measure;
    }

    public void setmMeasure(String mMeasure) {
        this.measure = mMeasure;
    }

    public String getmIngredient() {
        return ingredient;
    }

    public void setmIngredient(String mIngredient) {
        this.ingredient = mIngredient;
    }
}
