package com.hfad.workout.View.Fragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hfad.workout.SQL.Model.Workout;
import com.hfad.workout.R;
import com.hfad.workout.View.Activities.DetailActivity;
import com.hfad.workout.View.Adapters.RecyclerViewWorkout;

import com.hfad.workout.SQL.WorkoutDatabaseHelper;
import com.hfad.workout.View.Adapters.TouchHelpers.OnStartDragListener;
import com.hfad.workout.View.Adapters.TouchHelpers.SimpleItemTouchHelperCallback;

import java.util.ArrayList;


public class WorkoutRecyclerFragment extends Fragment implements RecyclerViewWorkout.ListItemClickListener, OnStartDragListener{



    private ArrayList<Workout> listWorkout;
    private RecyclerView recyclerView;
    private RecyclerViewWorkout recyclerViewWorkout;
    private WorkoutDatabaseHelper workoutDatabaseHelper;
    private ItemTouchHelper mItemTouchHelper;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_recycler_list, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewWorkout);
        listWorkout = new ArrayList<>();
        recyclerViewWorkout = new RecyclerViewWorkout(listWorkout, getActivity().getApplicationContext(),this,this);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(recyclerViewWorkout);
        workoutDatabaseHelper = new WorkoutDatabaseHelper(this.getActivity().getApplicationContext());

        getDataFromSQLite();

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(recyclerViewWorkout);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(recyclerView);


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
       getDataFromSQLite();
    }

    /**
     * This method is to fetch all user records from SQLite
     */
    private void getDataFromSQLite() {
        // AsyncTask is used so that SQLite operation does not block the UI Thread.
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                listWorkout.clear();
                listWorkout.addAll(workoutDatabaseHelper.getAllWorkoutData());
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                recyclerViewWorkout.notifyDataSetChanged();
            }
        }.execute();
    }

/**Read the click in order to open respective fragment and read data
 * Currently it opens only the timeView. Name and Description are empty.*/
    @Override
    public void onListItemClick(int clickedItemIndex){

        String string =String.valueOf(clickedItemIndex);


        /* View view=getView();

       View fragmentContainer = view.findViewById(R.id.fragment_container);
        if (fragmentContainer != null) {

            WorkoutDetailFragment details = new WorkoutDetailFragment();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            details.setWorkout(clickedItemIndex);
            ft.replace(R.id.fragment_container, );
            ft.addToBackStack(null);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit();}
        else {*/
            Intent intent = new Intent(getActivity(), DetailActivity.class);
            intent.putExtra("NUMBER_OF_WORKOUT",string);
            startActivity(intent);

    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
     mItemTouchHelper.startDrag(viewHolder);
    }
}
