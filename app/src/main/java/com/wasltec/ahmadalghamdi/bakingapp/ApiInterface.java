package com.wasltec.ahmadalghamdi.bakingapp;

import com.wasltec.ahmadalghamdi.bakingapp.models.Recipe;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface ApiInterface {

    @GET("/topher/2017/May/59121517_baking/baking.json")
    Call<ArrayList<Recipe>> getRecipes();

}
