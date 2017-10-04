package com.hfad.workout.View.Fragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.hfad.workout.Model.Workout;
import com.hfad.workout.R;
import com.hfad.workout.View.Activities.DetailActivity;
import com.hfad.workout.View.Adapters.recyclerViewWorkout;

import com.hfad.workout.SQL.WorkoutDatabaseHelper;

import java.util.ArrayList;


public class WorkoutRecyclerFragment extends Fragment implements recyclerViewWorkout.ListItemClickListener {



    private ArrayList<Workout> listWorkout;
    private RecyclerView recyclerViewWorkout;
    private recyclerViewWorkout captionedAdapterRecycler;
    private WorkoutDatabaseHelper databaseHelper;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_recycler_list, container, false);
        recyclerViewWorkout = (RecyclerView) view.findViewById(R.id.recyclerViewWorkout);
        initObjects();


        return view;
    }


    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        listWorkout = new ArrayList<>();
        captionedAdapterRecycler = new recyclerViewWorkout(listWorkout, getActivity().getApplicationContext(),this);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerViewWorkout.setLayoutManager(mLayoutManager);
        recyclerViewWorkout.setItemAnimator(new DefaultItemAnimator());
        recyclerViewWorkout.setHasFixedSize(true);
        recyclerViewWorkout.setAdapter(captionedAdapterRecycler);
        databaseHelper = new WorkoutDatabaseHelper(this.getActivity().getApplicationContext());

        getDataFromSQLite();
    }

    /**
     * This method is to fetch all user records from SQLite
     */
    private void getDataFromSQLite() {
        // AsyncTask is used that SQLite operation not blocks the UI Thread.
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                listWorkout.clear();
                listWorkout.addAll(databaseHelper. getAllWorkout());
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                captionedAdapterRecycler.notifyDataSetChanged();
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
}
