package com.hfad.workout.View.Fragments;


import android.support.v4.app.Fragment;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import com.hfad.workout.SQL.Model.Workout;
import com.hfad.workout.R;
import com.hfad.workout.SQL.WorkoutDatabaseHelper;

import java.util.ArrayList;

/**Displays the time, all the buttons are handled here */
public class StopwatchNestedFragment extends Fragment implements View.OnClickListener {
    //Number of seconds displayed on the stopwatch. Gets the data from database.
    private int seconds;
    private static final String TAG = "MyActivity";
    //Is the stopwatch running?
    private boolean running;
    private static boolean wasRunning;

    private ArrayList<Workout> listWorkout;
    private WorkoutDatabaseHelper databaseHelper;

    public void setWorkoutId(int workoutId) {
        this.workoutId = workoutId;
    }

    private int workoutId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    if (savedInstanceState != null) {
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
            wasRunning = savedInstanceState.getBoolean("wasRunning");
            if (wasRunning) {
                running = true;

            }}
    new UpdateWorkoutTask().execute(workoutId);/**TODO Comment*/

    }

@Override
public void onStop() {
    super.onStop();
  /**Get name column in order to navigate through database*/
    TextView textTitle=(TextView) getView().getRootView().findViewById(R.id.textTitle);
    String workoutName=textTitle.getText().toString();

    ContentValues workoutValues;
    workoutValues = new ContentValues();

    workoutValues.put("TIME", seconds);

    Toast toasty = Toast.makeText(getActivity().
            getApplication(),workoutName, Toast.LENGTH_SHORT);
    toasty.show();


  SQLiteOpenHelper workoutDatabaseHelper =
          new WorkoutDatabaseHelper(getActivity().getApplicationContext());

         try {
            SQLiteDatabase db = workoutDatabaseHelper.getWritableDatabase();
            db.update("WORKOUT", workoutValues, "NAME = ?", new String[] {workoutName});
            db.close();

             } catch(SQLiteException e) {
            Log.e(TAG,"STACKTRACE");
             Log.e(TAG,Log.getStackTraceString(e));
             e.printStackTrace();
             }
}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_stopwatch, container, false);

        runTimer(layout);

        Button startButton = (Button) layout.findViewById(R.id.start_button);
        startButton.setOnClickListener(this);
        Button stopButton = (Button) layout.findViewById(R.id.stop_button);
        stopButton.setOnClickListener(this);
        Button resetButton = (Button) layout.findViewById(R.id.reset_button);
        resetButton.setOnClickListener(this);
        Button deleteButton=(Button) layout.findViewById(R.id.delete_Button);
        deleteButton.setOnClickListener(this);

        return layout;
    }

    private void runTimer(View view) {

        final TextView timeView = (TextView) view.findViewById(R.id.time_view);
        final Handler handler = new Handler();
                      handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;
                String time = String.format("%d:%02d:%02d",
                        hours, minutes, secs);
                timeView.setText(time);
                if (running) {
                    seconds++;
                }
                handler.postDelayed(this, 1000);
            }
                      });
    }

   public class UpdateWorkoutTask extends AsyncTask<Integer, Void, Boolean> {

        public Boolean doInBackground(Integer... workouts) {

            listWorkout=new ArrayList<>();
            databaseHelper = new WorkoutDatabaseHelper(getActivity().
                    getApplicationContext());

            listWorkout.clear();
            listWorkout.addAll(databaseHelper.getAllWorkoutData());

        // do what you need with the cursor here

        Workout work=listWorkout.get(workouts[0]);

        seconds=work.getTime();

            return true;
        }

        protected void onPostExecute(Boolean success) {
            if (!success) {Toast toast = Toast.makeText(getActivity().getApplicationContext(),
            "Database unavailable onPostExecute", Toast.LENGTH_SHORT);toast.show();}
        }
    }

    @Override
    public void onPause() { super.onPause(); wasRunning = running;}

    @Override
    public void onResume() { super.onResume();  if (wasRunning) { running = true;}}

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt("seconds", seconds);
        savedInstanceState.putBoolean("running", running);
        savedInstanceState.putBoolean("wasRunning", wasRunning);}

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
        case R.id.start_button:
            onClickStart(v);
            break;
        case R.id.stop_button:
            onClickStop(v);
            break;
        case R.id.reset_button:
            onClickReset(v);
            break;
            case R.id.delete_Button:
                deleteButtonClicked(v);
                break;
        }
    }

    public void onClickStart(View view) { running = true;}

    public void onClickStop(View view) { running = false;}

    public void onClickReset(View view) { running = false;     seconds = 0;}

    public void deleteButtonClicked (View view){

        listWorkout=new ArrayList<>();
        databaseHelper = new WorkoutDatabaseHelper(getActivity().
                getApplicationContext());

        listWorkout.clear();
        listWorkout.addAll(databaseHelper.getAllWorkoutData());

        // do what you need with the cursor here

        Workout work=listWorkout.get(workoutId);

        String id=Integer.toString(work.getId());


        try {
            SQLiteOpenHelper WorkoutDatabaseHelper =
                    new WorkoutDatabaseHelper(getActivity().getApplicationContext());
            SQLiteDatabase db = WorkoutDatabaseHelper.getWritableDatabase();

            Toast toasty = Toast.makeText(getActivity().getApplicationContext(),
                    id, Toast.LENGTH_LONG);
            toasty.show();
            db.execSQL("DELETE FROM " +"WORKOUT" + " WHERE " + "_id" + "=\"" + id + "\";");
            db.close();
        } catch(SQLiteException e) {
            Log.e(TAG, "STACKTRACE");
            Log.e(TAG, Log.getStackTraceString(e));
        }
    }

}