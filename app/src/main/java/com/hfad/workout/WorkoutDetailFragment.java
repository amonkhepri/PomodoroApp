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

import org.w3c.dom.Text;

/**WorkoutDetailFragment obj=new WorkoutDetailFragment();*/
public class WorkoutDetailFragment extends Fragment {
    //
    public static long workoutId=0;
    public static void setWorkout(long id) {workoutId = id;}
    //saved amount of seconds

    private Context c;

    @Override  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                        Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_workout_detail, container, false);

    }

    @Override public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putLong("workoutId", workoutId);}

@Override public void onCreate(Bundle savedInstanceState){
    super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            workoutId = savedInstanceState.getLong("workoutId");
        } else { FragmentTransaction ft = getChildFragmentManager().beginTransaction();
            StopwatchFragment stopwatchFragment = new StopwatchFragment();
            ft.replace(R.id.stopwatch_container, stopwatchFragment);
            ft.addToBackStack(null);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit();}}


@Override public void onStart() {
    super.onStart();

        try {
        c=getActivity().getApplicationContext();

        SQLiteOpenHelper WorkoutDatabaseHelper = new WorkoutDatabaseHelper(c);
        SQLiteDatabase db = WorkoutDatabaseHelper.getReadableDatabase();

        Cursor cursor = db.query ("WORKOUT",
                    new String[] {"NAME", "DESCRIPTION","TIME"},
                    "_id = ?",
                    new String[] {Long.toString(workoutId)},
                    null, null,null);

if(cursor.moveToFirst()) {

        int time=cursor.getInt(2);
                         View view = getView();
   TextView seconds=(TextView)view.findViewById(R.id.time_view);
            seconds.setText(Integer.toString(time));

  String nameText = cursor.getString(0);
TextView name = (TextView)view.findViewById(R.id.textTitle);
         name.setText(nameText);

  String descriptionText = cursor.getString(1);
TextView description = (TextView)view.findViewById(R.id.textDescription);
         description.setText(descriptionText);}}

        catch(SQLiteException e) {
           Toast toast = Toast.makeText(getActivity().
           getApplication(),"Database unavailable close", Toast.LENGTH_SHORT);toast.show();}}}