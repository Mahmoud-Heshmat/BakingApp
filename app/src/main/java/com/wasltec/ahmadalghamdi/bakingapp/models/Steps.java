package com.wasltec.ahmadalghamdi.bakingapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Steps implements Serializable {

    private String id;
    private String shortDescription;
    private String description;
    private String videoURL;
    private String thumbnailURL;

    public Steps(String id, String shortDescription, String description, String videoURL, String thumbnailURL) {
        this.id = id;
        this.shortDescription = shortDescription;
        this.description = description;
        this.videoURL = videoURL;
        this.thumbnailURL = thumbnailURL;
    }

    //    public Steps(Parcel in) {
//        mId = in.readString();
//        mShortDescription = in.readString();
//        mDescription = in.readString();
//        mVideoURL = in.readString();
//        mThumbnailURL = in.readString();
//    }
//
//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeString(mId);
//        dest.writeString(mShortDescription);
//        dest.writeString(mDescription);
//        dest.writeString(mVideoURL);
//        dest.writeString(mThumbnailURL);
//    }
//
//    // This is to de-serialize the object
//    public static final Parcelable.Creator<Ingredients> CREATOR = new Parcelable.Creator<Ingredients>(){
//        public Ingredients createFromParcel(Parcel in) {
//            return new Ingredients(in);
//        }
//
//        public Ingredients[] newArray(int size) {
//            return new Ingredients[size];
//        }
//    };

    public String getmId() {
        return id;
    }

    public void setmId(String mId) {
        this.id = mId;
    }

    public String getmShortDescription() {
        return shortDescription;
    }

    public void setmShortDescription(String mShortDescription) {
        this.shortDescription = mShortDescription;
    }

    public String getmDescription() {
        return description;
    }

    public void setmDescription(String mDescription) {
        this.description = mDescription;
    }

    public String getmVideoURL() {
        return videoURL;
    }

    public void setmVideoURL(String mVideoURL) {
        this.videoURL = mVideoURL;
    }

    public String getmThumbnailURL() {
        return thumbnailURL;
    }

    public void setmThumbnailURL(String mThumbnailURL) {
        this.thumbnailURL = mThumbnailURL;
    }
}
