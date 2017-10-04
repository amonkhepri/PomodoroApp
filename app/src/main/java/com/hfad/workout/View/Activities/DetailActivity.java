package com.hfad.workout.View.Activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.widget.Toast;

import com.hfad.workout.R;
import com.hfad.workout.View.Fragments.WorkoutDetailFragment;

public class DetailActivity extends FragmentActivity {

@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

   /* // Create a new head BodyPartFragment
    WorkoutDetailFragment workoutDetailFragment = new WorkoutDetailFragment();
    // Get the correct index to access in the array of head images from the intent
    // Set the default value to 0

    String workoutId=  getIntent().getStringExtra("NUMBER_OF_WORKOUT");
    int workout=Integer.valueOf(workoutId);
    workoutDetailFragment.setWorkoutId(workout);

    // Add the fragment to its container using a FragmentManager and a Transaction
    FragmentManager fragmentManager = getSupportFragmentManager();

    fragmentManager.beginTransaction().add(R.id.detail_frag, workoutDetailFragment)
            .commit();*/
}}
