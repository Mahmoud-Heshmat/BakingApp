package com.wasltec.ahmadalghamdi.bakingapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.wasltec.ahmadalghamdi.bakingapp.Activities.MainActivity;
import com.wasltec.ahmadalghamdi.bakingapp.adapters.RecipeAdapter;
import com.wasltec.ahmadalghamdi.bakingapp.api.Singleton;
import com.wasltec.ahmadalghamdi.bakingapp.api.Urls;
import com.wasltec.ahmadalghamdi.bakingapp.models.Recipe;
import com.wasltec.ahmadalghamdi.bakingapp.utilits.JsonUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeFragment extends android.support.v4.app.Fragment  {

    @BindView(R.id.recycle_view) RecyclerView recyclerView;
    private RecipeAdapter adapter;
    LinearLayoutManager linearLayoutManager;

    public static ArrayList<Recipe> list = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_step, container, false);
        ButterKnife.bind(this, rootView);
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new RecipeAdapter(getContext());
        recyclerView.setAdapter(adapter);

        if(isOnline()) {
            getRecipes();
        }else{
            Snackbar.make(rootView, R.string.network_connection, Snackbar.LENGTH_LONG).show();
        }

        return rootView;
    }

    private void getRecipes(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET,  Urls.mainURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Log.d("response", response);
                if (!response.isEmpty()){
                    list = JsonUtils.parseRecipesJson(response);
                    if(!list.isEmpty()){
                        adapter.update_data(list);
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("response", error.toString());
            }
        });
        Singleton.getInstance(getContext()).addToRequestQueue(stringRequest);
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

}
