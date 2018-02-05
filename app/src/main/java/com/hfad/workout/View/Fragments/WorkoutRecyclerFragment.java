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


public class WorkoutRecyclerFragment extends Fragment
        implements RecyclerViewWorkout.ListItemClickListener, OnStartDragListener{



    private ArrayList<Workout> globalListWorkout;
    private RecyclerViewWorkout globalRecyclerViewWorkout;
    private WorkoutDatabaseHelper globalWorkoutDatabaseHelper;
    private ItemTouchHelper globalMItemTouchHelper;
    /*Regular fragment methods*/

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        RecyclerView recyclerViewWorkout;
        View fragmentRecyclerListView= inflater.inflate(R.layout.fragment_recycler_list, container, false);
        recyclerViewWorkout = (RecyclerView) fragmentRecyclerListView.
                findViewById(R.id.recyclerViewWorkout);

        globalListWorkout = new ArrayList<>();

        globalRecyclerViewWorkout = new RecyclerViewWorkout(globalListWorkout,
                getActivity().getApplicationContext(),this,this);

        LinearLayoutManager mLayoutManager =
                new LinearLayoutManager(getActivity().getApplicationContext());

        recyclerViewWorkout.setLayoutManager(mLayoutManager);
        recyclerViewWorkout.setItemAnimator(new DefaultItemAnimator());
        recyclerViewWorkout.setHasFixedSize(true);
        recyclerViewWorkout.setAdapter(globalRecyclerViewWorkout);
        globalWorkoutDatabaseHelper = new WorkoutDatabaseHelper
                (this.getActivity().getApplicationContext());

        getDataFromSQLite();

        ItemTouchHelper.Callback callback =
                new SimpleItemTouchHelperCallback(globalRecyclerViewWorkout);
        globalMItemTouchHelper = new ItemTouchHelper(callback);
        globalMItemTouchHelper.attachToRecyclerView(recyclerViewWorkout);


        return fragmentRecyclerListView;
    }

    @Override
    public void onResume() {
        super.onResume();
        /*This method is to fetch all user records from SQLite */
        getDataFromSQLite();
    }

    /*Overriding interface methods*/
    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        globalMItemTouchHelper.startDrag(viewHolder);
    }

    /*TODO Read the click on RecyclerViewWorkout in order to open respective
     fragment and read data Currently it opens only the timeView. Name and
      Description are empty.*/
    @Override
    public void onListItemClick(int clickedItemIndex){

        String clickedItem =String.valueOf(clickedItemIndex);
        /*TODO that's a previous implementation, explore whether if what I did is an improvement
         View view=getView();

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
        intent.putExtra("NUMBER_OF_WORKOUT",clickedItem);
        startActivity(intent);

    }
    /** This method is to fetch all user records from SQLite*/
       private void getDataFromSQLite() {
        // AsyncTask is used so that SQLite operation does not block the UI Thread.
        //TODO Explore events about which lint speaks where leaks occur
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                globalListWorkout.clear();
                globalListWorkout.addAll(globalWorkoutDatabaseHelper.getAllWorkoutData());
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                globalRecyclerViewWorkout.notifyDataSetChanged();
            }
        }.execute();
    }






}
