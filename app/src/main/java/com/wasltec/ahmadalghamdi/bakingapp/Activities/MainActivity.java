package com.wasltec.ahmadalghamdi.bakingapp.Activities;

import android.content.Context;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.design.widget.Snackbar;
import android.support.test.espresso.IdlingResource;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.FrameLayout;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.wasltec.ahmadalghamdi.bakingapp.ApiClient;
import com.wasltec.ahmadalghamdi.bakingapp.ApiInterface;
import com.wasltec.ahmadalghamdi.bakingapp.AppWidgetService;
import com.wasltec.ahmadalghamdi.bakingapp.R;
import com.wasltec.ahmadalghamdi.bakingapp.RecipeFragment;
import com.wasltec.ahmadalghamdi.bakingapp.adapters.RecipeAdapter;
import com.wasltec.ahmadalghamdi.bakingapp.api.Singleton;
import com.wasltec.ahmadalghamdi.bakingapp.api.Urls;
import com.wasltec.ahmadalghamdi.bakingapp.models.Ingredients;
import com.wasltec.ahmadalghamdi.bakingapp.models.Recipe;
import com.wasltec.ahmadalghamdi.bakingapp.models.Steps;
import com.wasltec.ahmadalghamdi.bakingapp.utilits.JsonUtils;
import com.wasltec.ahmadalghamdi.bakingapp.utilits.Prefs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity extends AppCompatActivity {

    private static String TAG_RECIPE = "RECIPE";

    @BindView(R.id.recycle_view) RecyclerView recyclerView;
    private RecipeAdapter adapter;
    LinearLayoutManager linearLayoutManager;
    GridLayoutManager gridLayoutManager;
    int posterWidth = 600;

    private static String RECIPES_KEY = "recipes";
    public static ArrayList<Recipe> list = new ArrayList<>();

    FrameLayout frameLayout;
    Context context;

    private Boolean mTowPane = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View parentView = findViewById(android.R.id.content);
        ButterKnife.bind(this);
        context = this;

        if(findViewById(R.id.tabletMode) != null){
            mTowPane = true;
            Log.d("responseeee", "tablet mode");
        }

        if(isOnline()) {
            getRecipes();

            if(mTowPane){
                gridLayoutManager = new GridLayoutManager(context, calculateBestSpanCount(posterWidth));
                recyclerView.setLayoutManager(gridLayoutManager);
            }else{
                linearLayoutManager = new LinearLayoutManager(context);
                recyclerView.setLayoutManager(linearLayoutManager);
            }

            adapter = new RecipeAdapter(context);
            recyclerView.setAdapter(adapter);

        }else{
            Snackbar.make(parentView, R.string.network_connection, Snackbar.LENGTH_LONG).show();
        }

        if (savedInstanceState != null && savedInstanceState.containsKey(RECIPES_KEY)) {
            list = savedInstanceState.getParcelableArrayList(RECIPES_KEY);
        }

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        if (list != null && !list.isEmpty())
            outState.putParcelableArrayList(RECIPES_KEY, (ArrayList<? extends Parcelable>) list);
    }

    private void getRecipeds(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET,  Urls.mainURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("response", response);
                if (!response.isEmpty()){
                    list = JsonUtils.parseRecipesJson(response);
                    if(!list.isEmpty()){
                        adapter.update_data(list);
                        // Set the default recipe for the widget
                        if (Prefs.loadRecipe(getApplicationContext()) == null) {
                            AppWidgetService.updateWidget(context, list.get(0));
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("response", error.toString());
            }
        });
        Singleton.getInstance(context).addToRequestQueue(stringRequest);
    }

    private void getRecipes(){
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ArrayList<Recipe>> call = apiService.getRecipes();

        call.enqueue(new Callback<ArrayList<Recipe>>() {
            @Override
            public void onResponse(Call<ArrayList<Recipe>> call, retrofit2.Response<ArrayList<Recipe>> response) {
                System.out.println("onResponse: " + response.errorBody());
                if (response.isSuccessful()){
                    if (response.code() == 200){
                        list = response.body();
                        if(!list.isEmpty()){
                            adapter.update_data(list);
                            // Set the default recipe for the widget
                            AppWidgetService.updateWidget(context, list.get(0));
                        }

                    }else{
                        System.out.println("response code != 200, possibly unsuccessful server request");
                    }
                }else{
                    System.out.println("response.isSuccessful() = false");
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Recipe>> call, Throwable t) {
                System.out.println("onFailure: " + t);
            }
        });
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){
            MainActivity.this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE);
            Log.d("responseOrientation", "ORIENTATION_LANDSCAPE");
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) frameLayout.getLayoutParams();
            params.width=params.MATCH_PARENT;
            params.height=params.MATCH_PARENT;
            frameLayout.setLayoutParams(params);
            if(getSupportActionBar()!=null) {
                getSupportActionBar().hide();
            }

        }else if(newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            Log.d("responseOrientation", "ORIENTATION_PORTRAIT");
            MainActivity.this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            if(getSupportActionBar()!=null) {
                getSupportActionBar().show();
            }
        }
    }

    private int calculateBestSpanCount(int posterWidth) {
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        float screenWidth = outMetrics.widthPixels;
        return Math.round(screenWidth / posterWidth);
    }
}
