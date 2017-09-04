package com.hfad.workout;

import android.app.Fragment;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.app.FragmentTransaction;
import android.widget.Toast;
/**WorkoutDetailFragment obj=new WorkoutDetailFragment();*/
public class WorkoutDetailFragment extends Fragment {
    //
    public long workoutId;
    public void setWorkout(long id) {this.workoutId = id;}
    //saved amount of seconds
    int savedNumberOfseconds =4;
    //Context of this fragment for AsyncTask
    Context c;

public int getSavedNumberOfseconds() { return savedNumberOfseconds;}

@Override public void onActivityCreated(Bundle SavedInstanceState) {
        super.onActivityCreated(SavedInstanceState);
       //Getting context and
        c=getActivity().getApplicationContext();
        new UpdateWorkoutTask().execute((int)workoutId);}

@Override public void onCreate(Bundle savedInstanceState){ super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            workoutId = savedInstanceState.getLong("workoutId");
        } else { FragmentTransaction ft = getChildFragmentManager().beginTransaction();
            StopwatchFragment stopwatchFragment = new StopwatchFragment();
            ft.replace(R.id.stopwatch_container, stopwatchFragment);
            ft.addToBackStack(null);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit();}}

private class UpdateWorkoutTask extends AsyncTask<Integer, Void, Boolean> {
        //TODO Why is it here?
        ContentValues workoutValues;
    protected void onPreExecute() {
            workoutValues = new ContentValues();
            workoutValues.put("_id", workoutId);}

    protected Boolean doInBackground(Integer... workouts) {int workoutNo = workouts[0];
            try {SQLiteOpenHelper WorkoutDatabaseHelper = new WorkoutDatabaseHelper(c);
                SQLiteDatabase db = WorkoutDatabaseHelper.getWritableDatabase();

                Cursor cursor = db.query("WORKOUT",
                        new String[]{"id_",},
                        "_id = ?",
                        new String[]{Integer.toString(workoutNo)},
                        null, null, null);
                //Move to the first record in the Cursor
                if (cursor.moveToFirst()) { /**Get the drink details from the cursor*/
                    savedNumberOfseconds = cursor.getInt(0);
                    cursor.close();
                    db.close();} return true;
                }catch(SQLiteException e){
             /*Toast toast = Toast.makeText(c, "Database unavailable", Toast.LENGTH_SHORT);
             toast.show(); Can't post directly to UI thread*/ return false;}}

    protected void onPostExecute(Boolean success) {Context context=c;
            if (!success) {Toast toast = Toast.makeText(context,
                        "Database unavailable onPostExecute", Toast.LENGTH_SHORT);
                toast.show();}}}

@Override  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_workout_detail, container, false);}

@Override public void onStart() {super.onStart(); View view = getView();
        c=getActivity().getApplicationContext();
        try {SQLiteOpenHelper WorkoutDatabaseHelper = new WorkoutDatabaseHelper(c);
            SQLiteDatabase db = WorkoutDatabaseHelper.getReadableDatabase();
            Cursor cursor = db.query ("WORKOUT",
                    new String[] {"NAME", "DESCRIPTION"},
                    "_id = ?",
                    new String[] {Long.toString(workoutId)},
                    null, null,null);
/**Move to the first record in the Cursor*/if (cursor.moveToFirst()) {
/*//Get the workout details from the cursor*/String nameText = cursor.getString(0);
                                      String descriptionText = cursor.getString(1);
/* //Populate the workout name*/TextView name = (TextView)view.findViewById(R.id.textTitle);
                                         name.setText(nameText);
//Populate the workout description
TextView description = (TextView)view.findViewById(R.id.textDescription);
                description.setText(descriptionText);
            }}catch(SQLiteException e) {Toast toast = Toast.makeText(getActivity().
           getApplication(),"Database unavailable close", Toast.LENGTH_SHORT);toast.show();}}

@Override public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putLong("workoutId", workoutId);}}
