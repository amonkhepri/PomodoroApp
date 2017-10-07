package com.hfad.workout.View.Adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hfad.workout.Model.Workout;
import com.hfad.workout.R;

import java.util.ArrayList;

public class RecyclerViewWorkout extends RecyclerView.Adapter<RecyclerViewWorkout.ViewHolder> {



    private ArrayList<Workout> listWorkout;

    private Context mContext;

    public interface ListItemClickListener{
        void onListItemClick(int clickedItemIndex);
    }

    final private ListItemClickListener mOnClickListener;

    public RecyclerViewWorkout(ArrayList<Workout> listWorkout, Context mContext, ListItemClickListener listener){
        this.listWorkout = listWorkout;
        this.mContext = mContext;
        mOnClickListener=listener;
    }


    @Override
    public RecyclerViewWorkout.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cv = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.workout_item, parent, false);
        return new ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        CardView cardView = holder.cardView;

        TextView name = (TextView)cardView.findViewById(R.id._name);
        name.setText(listWorkout.get(position).getName());

        TextView description = (TextView)cardView.findViewById(R.id._description);
        description.setText(listWorkout.get(position).getDescription());
    }

    public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private CardView cardView;

        public ViewHolder(CardView cardView) {
            super(cardView);
            this.cardView =cardView;
            cardView.setOnClickListener(this);
        }
        @Override
        public void onClick (View view){
            int clickedPosition=getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);
        }
    }

    @Override
    public int getItemCount() {
        return listWorkout.size();
    }
    //TODO how does this adapter differ from drawer adapter
}
