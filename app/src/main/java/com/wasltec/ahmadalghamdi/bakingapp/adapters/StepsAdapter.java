package com.wasltec.ahmadalghamdi.bakingapp.adapters;

import android.content.Context;
import android.content.Intent;
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

import com.wasltec.ahmadalghamdi.bakingapp.IngredientsActivity;
import com.wasltec.ahmadalghamdi.bakingapp.R;
import com.wasltec.ahmadalghamdi.bakingapp.StepDetailFragment;
import com.wasltec.ahmadalghamdi.bakingapp.models.Steps;

import java.util.ArrayList;

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.ViewHolder>{

    private Context context;
    private ArrayList<Steps> items = new ArrayList<>();
    private Boolean isTowPane;

    public StepsAdapter(Context context, Boolean isTowPane) {
        this.context = context;
        this.isTowPane = isTowPane;
    }

    private static String TAG = "StepDetails";

    @Override
    public StepsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_steps, parent, false);
        return new StepsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StepsAdapter.ViewHolder holder, final int position) {

        final Steps steps = items.get(position);
        holder.stepText.setText(steps.getmShortDescription());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StepDetailFragment stepDetailFragment = new StepDetailFragment();
                addCenterFragments(stepDetailFragment, position, steps, TAG);

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

    public void update_data(ArrayList<Steps> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView stepText;
        private CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            stepText =  itemView.findViewById(R.id.step);
            cardView =  itemView.findViewById(R.id.card_view);
        }
    }

    private void addCenterFragments(Fragment fragment, int position, Steps step, String tag) {
        Bundle bundle = new Bundle();
        bundle.putInt("index", position);
        bundle.putSerializable("step", step);
        fragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
        if(isTowPane){
            fragmentTransaction.replace(R.id.stepDetailContainer, fragment, tag);
        }else {
            fragmentTransaction.replace(R.id.stepsContainer, fragment, tag);
        }
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

}