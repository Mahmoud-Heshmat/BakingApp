package com.wasltec.ahmadalghamdi.bakingapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wasltec.ahmadalghamdi.bakingapp.Activities.MainActivity;
import com.wasltec.ahmadalghamdi.bakingapp.adapters.RecipeAdapter;
import com.wasltec.ahmadalghamdi.bakingapp.models.Ingredients;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mahmoud Heshmat on 8/18/2018.
 */

public class IngredientFragment extends android.support.v4.app.Fragment  {

    public static int position = 0;
    public static Boolean mTowPane = false;

    ArrayList<Ingredients> ingredientsArrayList = new ArrayList<>();

    @BindView(R.id.recycle_view) RecyclerView recyclerView;
    private IngredientsAdapter adapter;
    LinearLayoutManager linearLayoutManager;
    Context context;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        position = getArguments().getInt("index");
        mTowPane = getArguments().getBoolean("isTowPane");
        Log.d("responsePos", "  " + position);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_ingredient, container, false);
        ButterKnife.bind(this, rootView);

        linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new IngredientsAdapter(context);
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);
        ingredientsArrayList = MainActivity.list.get(position).getmIngredients();
        if(!ingredientsArrayList.isEmpty()) {
            adapter.update_data(ingredientsArrayList);
        }

        return rootView;
    }

}
