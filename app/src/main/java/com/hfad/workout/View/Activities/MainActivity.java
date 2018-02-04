package com.hfad.workout.View.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;

import com.hfad.workout.R;



public class MainActivity extends AppCompatActivity                           {

    @Override protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fabDbManager = (FloatingActionButton) findViewById(R.id.fabDbmanager);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), NewWorkout.class);
                startActivity(intent);}
        });

        fabDbManager.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent dbManagerActivity = new Intent(getApplicationContext(), AndroidDatabaseManager.class);
                startActivity(dbManagerActivity);
            }
        });}



    }
//TODO From previous version of the app, should probably delete it..OR use it
// instead of my overcomplicated WorkoutDetailFragment, or perhaps the previous implenentation
//was overcomplicated and that's why I decided to do this way? Couldn't say now.

   /* @Override
    public void itemClicked(long id) {
        View fragmentContainer = findViewById(R.id.fragment_container);
        if (fragmentContainer != null) {

            WorkoutDetailFragment details = new WorkoutDetailFragment();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            details.setWorkout(id);
            ft.replace(R.id.fragment_container, details);
            ft.addToBackStack(null);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit();}
        else {
            Intent intent = new Intent(this, DetailActivity.class);
            WorkoutDetailFragment.setWorkout(id);
            startActivity(intent);}
    }*/


