package com.wasltec.ahmadalghamdi.bakingapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.wasltec.ahmadalghamdi.bakingapp.Activities.MainActivity;
import com.wasltec.ahmadalghamdi.bakingapp.fragments.StepFragment;
import com.wasltec.ahmadalghamdi.bakingapp.models.Steps;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IngedStepsActivity extends AppCompatActivity{

    private static String STEP_TAG = "Steps";
    private static String INGREDIENT_TAG = "ingredient";
    private static String STEP_DETAIL_TAG = "StepDetails";
    @BindView(R.id.frameLayout) FrameLayout frameLayout;

    private Boolean mTowPane = false;

    int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inged_steps);
        ButterKnife.bind(this);

        if(findViewById(R.id.stepDetailContainer) != null){
            mTowPane = true;
            StepDetailFragment stepDetailFragment = new StepDetailFragment();
            //addStepDetailFragments(stepDetailFragment, 0,MainActivity.list.get(0).getmSteps().get(0), STEP_DETAIL_TAG);
        }

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            position = bundle.getInt("index");
            StepFragment stepFragment = new StepFragment();
            addStepFragments(stepFragment, position, STEP_TAG);

            IngredientFragment ingredientFragment = new IngredientFragment();
            addIngredientFragments(ingredientFragment, position, INGREDIENT_TAG);
        }

    }

    private void addStepFragments(Fragment fragment, int position , String tag) {
        Bundle bundle = new Bundle();
        bundle.putInt("index", position);
        bundle.putBoolean("isTowPane", mTowPane);
        fragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.stepsContainer, fragment, tag);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void addIngredientFragments(Fragment fragment, int position , String tag) {
        Bundle bundle = new Bundle();
        bundle.putInt("index", position);
        bundle.putBoolean("isTowPane", mTowPane);
        fragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.ingredientContainer, fragment, tag);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void addStepDetailFragments(Fragment fragment, int position, Steps step, String tag) {
        Bundle bundle = new Bundle();
        bundle.putInt("index", position);
        bundle.putSerializable("step", step);
        fragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.stepDetailContainer, fragment, tag);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){
            IngedStepsActivity.this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE);
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
            IngedStepsActivity.this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            if(getSupportActionBar()!=null) {
                getSupportActionBar().show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.widget, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add_to_widget) {
            AppWidgetService.updateWidget(this, MainActivity.list.get(position));
            Toast.makeText(getApplicationContext(), "Added To widget0", Toast.LENGTH_LONG).show();
            return true;
        } else
            return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
