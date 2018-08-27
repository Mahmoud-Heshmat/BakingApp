package com.wasltec.ahmadalghamdi.bakingapp;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.wasltec.ahmadalghamdi.bakingapp.Activities.MainActivity;
import com.wasltec.ahmadalghamdi.bakingapp.adapters.StepsAdapter;
import com.wasltec.ahmadalghamdi.bakingapp.models.Ingredients;
import com.wasltec.ahmadalghamdi.bakingapp.models.Steps;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IngredientsActivity extends AppCompatActivity {

    public static int position = 0;

    ArrayList<Ingredients> ingredientsArrayList = new ArrayList<>();

    @BindView(R.id.recycle_view) RecyclerView recyclerView;
    private IngredientsAdapter adapter;
    LinearLayoutManager linearLayoutManager;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients);
        ButterKnife.bind(this);
        context = this;

        linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new IngredientsAdapter(context);
        recyclerView.setAdapter(adapter);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            position = bundle.getInt("index");
            ingredientsArrayList = MainActivity.list.get(position).getmIngredients();
            if(!ingredientsArrayList.isEmpty()) {
                adapter.update_data(ingredientsArrayList);
            }
        }
    }
}
