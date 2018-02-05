package com.hfad.workout.View.Fragments;


import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.support.v4.app.FragmentTransaction;
import com.hfad.workout.SQL.Model.Workout;
import com.hfad.workout.R;
import com.hfad.workout.SQL.WorkoutDatabaseHelper;
import java.util.ArrayList;

public class WorkoutDetailFragment extends Fragment {

    public WorkoutDetailFragment(){}
    private int globalWorkoutId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_workout_detail, container, false);
    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt("globalWorkoutId", globalWorkoutId);
    }


    @Override

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        String workout=getActivity().getIntent().getStringExtra("NUMBER_OF_WORKOUT");
        globalWorkoutId =Integer.valueOf(workout);

        if (savedInstanceState != null) {
            globalWorkoutId = savedInstanceState.getInt("globalWorkoutId");
        }
        else{
            FragmentTransaction ft = getChildFragmentManager().beginTransaction();
            StopwatchNestedFragment stopwatchNestedFragment = new StopwatchNestedFragment();
            stopwatchNestedFragment.setWorkoutId(globalWorkoutId);
            ft.replace(R.id.stopwatch_container, stopwatchNestedFragment);
            ft.addToBackStack(null);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit();
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        ArrayList<Workout> listWorkout;
        listWorkout=new ArrayList<>();
        listWorkout.clear();
        WorkoutDatabaseHelper databaseHelper;
        databaseHelper = new WorkoutDatabaseHelper(this.getActivity().getApplicationContext());
        listWorkout.addAll(databaseHelper.getAllWorkoutData());

        Workout work=listWorkout.get(globalWorkoutId);
        View view = getView();

        String nameText = work.getName();
        TextView name = (TextView) view.findViewById(R.id.textTitle);
                 name.setText(nameText);

        String descriptionText = work.getDescription();
        TextView description = (TextView) view.findViewById(R.id.textDescription);
                 description.setText(descriptionText);

        Integer time = work.getTime();
        TextView seconds = (TextView) view.findViewById(R.id.time_view);
                 seconds.setText(Integer.toString(time));

    }

}