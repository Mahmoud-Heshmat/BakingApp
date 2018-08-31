package com.wasltec.ahmadalghamdi.bakingapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.wasltec.ahmadalghamdi.bakingapp.IngedStepsActivity;
import com.wasltec.ahmadalghamdi.bakingapp.R;
import com.wasltec.ahmadalghamdi.bakingapp.fragments.StepFragment;
import com.wasltec.ahmadalghamdi.bakingapp.models.Recipe;

import java.util.ArrayList;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder>{

    private Context context;
    private ArrayList<Recipe> items = new ArrayList<>();

    public RecipeAdapter(Context context) {
        this.context = context;
    }

    @Override
    public RecipeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_recipe, parent, false);
        return new RecipeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeAdapter.ViewHolder holder, final int position) {

        final Recipe recipe = items.get(position);
        holder.recipeText.setText(recipe.getmName());
        holder.serving.setText(recipe.getmServings());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, IngedStepsActivity.class);
                intent.putExtra("index", position);
                context.startActivity(intent);
                //Toast.makeText(context, recipe.getmName(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void update_data(ArrayList<Recipe> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView recipeText;
        private TextView serving;
        private CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            recipeText =  itemView.findViewById(R.id.recipe);
            serving =  itemView.findViewById(R.id.servicingNum);
            cardView =  itemView.findViewById(R.id.card_view);
        }
    }

}