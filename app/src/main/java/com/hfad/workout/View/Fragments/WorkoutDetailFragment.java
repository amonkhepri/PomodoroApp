package com.hfad.workout.View.Fragments;


import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.support.v4.app.FragmentTransaction;
import com.hfad.workout.Model.Workout;
import com.hfad.workout.R;
import com.hfad.workout.SQL.WorkoutDatabaseHelper;
import java.util.ArrayList;

public class WorkoutDetailFragment extends Fragment {

    public WorkoutDetailFragment(){}
    private int workoutId;
    private ArrayList<Workout> listWorkout;
    private WorkoutDatabaseHelper databaseHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                        Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_workout_detail, container, false);
    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt("workoutId", workoutId);
    }

@Override
public void onCreate(Bundle savedInstanceState){
    super.onCreate(savedInstanceState);

    String workout=getActivity().getIntent().getStringExtra("NUMBER_OF_WORKOUT");
    workoutId=Integer.valueOf(workout);

    if (savedInstanceState != null) {
            workoutId = savedInstanceState.getInt("workoutId");}
        else
            { FragmentTransaction ft = getChildFragmentManager().beginTransaction();
            StopwatchNestedFragment stopwatchNestedFragment = new StopwatchNestedFragment();
            stopwatchNestedFragment.setWorkoutId(workoutId);
            ft.replace(R.id.stopwatch_container, stopwatchNestedFragment);
            ft.addToBackStack(null);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit();}
}
@Override
public void onStart() {
    super.onStart();
    listWorkout=new ArrayList<>();
    databaseHelper = new WorkoutDatabaseHelper(this.getActivity().getApplicationContext());

            listWorkout.clear();
            listWorkout.addAll(databaseHelper. getAllWorkout());
                    // do what you need with the cursor here
                    View view = getView();

                    Workout work=listWorkout.get(workoutId);

                    String nameText = work.getName();
                    TextView name = (TextView) view.findViewById(R.id.textTitle);
                    name.setText(nameText);

                    String descriptionText = work.getDescription();
                    TextView description = (TextView) view.findViewById(R.id.textDescription);
                    description.setText(descriptionText);

                    int time = work.getTime();
                    TextView seconds = (TextView) view.findViewById(R.id.time_view);
                    seconds.setText(Integer.toString(time));
}
}