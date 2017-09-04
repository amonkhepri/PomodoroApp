package com.hfad.workout;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;

import static com.hfad.workout.WorkoutDetailFragment.workoutId;

public class MainActivity extends AppCompatActivity
                          implements WorkoutListFragment.WorkoutListListener {

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {

        @Override  public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),
                        NewWorkout.class);
                startActivity(intent);}});}

    @Override
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
            WorkoutDetailFragment.setWorkout(workoutId);
            startActivity(intent);}}}

/**TODO
* Save results after closing the app
* Adding/removing challenges

* */