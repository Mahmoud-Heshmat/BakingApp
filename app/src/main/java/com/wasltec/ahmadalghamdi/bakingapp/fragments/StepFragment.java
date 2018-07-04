package com.wasltec.ahmadalghamdi.bakingapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wasltec.ahmadalghamdi.bakingapp.Activities.MainActivity;
import com.wasltec.ahmadalghamdi.bakingapp.R;
import com.wasltec.ahmadalghamdi.bakingapp.RecipeFragment;
import com.wasltec.ahmadalghamdi.bakingapp.adapters.StepsAdapter;
import com.wasltec.ahmadalghamdi.bakingapp.models.Ingredients;
import com.wasltec.ahmadalghamdi.bakingapp.models.Steps;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepFragment extends android.support.v4.app.Fragment {

    static int position = 1;
    static Boolean mTowPane;

    ArrayList<Steps> stepsArrayList = new ArrayList<>();
    ArrayList<Ingredients> ingredientsArrayList = new ArrayList<>();

    @BindView(R.id.recycle_view) RecyclerView recyclerView;
    private StepsAdapter adapter;
    LinearLayoutManager linearLayoutManager;

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
        final View rootView = inflater.inflate(R.layout.fragment_step, container, false);
        ButterKnife.bind(this, rootView);

        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new StepsAdapter(getContext(), mTowPane);
        recyclerView.setAdapter(adapter);

        stepsArrayList = MainActivity.list.get(position).getmSteps();
        ingredientsArrayList = MainActivity.list.get(position).getmIngredients();

        if(!stepsArrayList.isEmpty()) {
            adapter.update_data(stepsArrayList);
        }

        return rootView;
    }

}
