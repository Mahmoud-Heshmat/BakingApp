package com.wasltec.ahmadalghamdi.bakingapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wasltec.ahmadalghamdi.bakingapp.adapters.StepsAdapter;
import com.wasltec.ahmadalghamdi.bakingapp.models.Ingredients;
import com.wasltec.ahmadalghamdi.bakingapp.models.Steps;

import java.util.ArrayList;

public class IngredientsAdapter  extends RecyclerView.Adapter<IngredientsAdapter.ViewHolder>{

    private Context context;
    private ArrayList<Ingredients> items = new ArrayList<>();

    public IngredientsAdapter(Context context) {
        this.context = context;
    }

    private static String TAG = "StepDetailFragment";

    @Override
    public IngredientsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_ingredients, parent, false);
        return new IngredientsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(IngredientsAdapter.ViewHolder holder, final int position) {

        final Ingredients ingredients = items.get(position);
        holder.quantity.setText(ingredients.getmQuantity());
        holder.measure.setText(ingredients.getmMeasure());
        holder.ingredient.setText(ingredients.getmIngredient());
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

    public void update_data(ArrayList<Ingredients> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView quantity;
        private TextView measure;
        private TextView ingredient;
        private CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            quantity =  itemView.findViewById(R.id.quantity);
            measure =  itemView.findViewById(R.id.measure);
            ingredient =  itemView.findViewById(R.id.ingredients);
            cardView =  itemView.findViewById(R.id.card_view);
        }
    }

}