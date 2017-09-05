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
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import static com.hfad.workout.WorkoutDetailFragment.workoutId;


/** in WorkoutDetailFragment: StopwatchFragment stopwatchFragment = new StopwatchFragment();
*/
public class StopwatchFragment extends Fragment implements View.OnClickListener {
    //Number of seconds displayed on the stopwatch.

    private int seconds;
    //Is the stopwatch running?
    private boolean running;
    private boolean wasRunning;
    Context c;

@Override public void onCreate(Bundle savedInstanceState) {super.onCreate(savedInstanceState);

    if (savedInstanceState != null) {
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
            wasRunning = savedInstanceState.getBoolean("wasRunning");
            if (wasRunning) {
                running = true;

            }}
    c=getActivity().getApplicationContext();
    new UpdateWorkoutTask().execute((int)workoutId);
}

@Override public void onStop() {
    super.onStop();

    View view=getView();
    TextView text1=(TextView) view.getRootView().findViewById(R.id.textTitle);

    String Name;
    Name=text1.getText().toString();

                        ContentValues workoutValues;
    workoutValues = new ContentValues();
    workoutValues.put("TIME", seconds);

    String workoutName = Name;
    SQLiteOpenHelper workoutDatabaseHelper =
            new WorkoutDatabaseHelper(getActivity().getApplicationContext());

         try {
            SQLiteDatabase db = workoutDatabaseHelper.getWritableDatabase();
            db.update("WORKOUT", workoutValues, "NAME = ?", new String[] {workoutName});
            db.close();

        } catch(SQLiteException e) {
            Toast toast = Toast.makeText(getActivity().getApplicationContext(),
                    "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
            e.printStackTrace();}}

    @Override  public View onCreateView(LayoutInflater inflater, ViewGroup container,
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

        return layout;}

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
            }});}

    private class UpdateWorkoutTask extends AsyncTask<Integer, Void, Boolean> {

        protected Boolean doInBackground(Integer... workouts) {

            int workoutNo = workouts[0];

            try {SQLiteOpenHelper WorkoutDatabaseHelper = new WorkoutDatabaseHelper(c);
                SQLiteDatabase db = WorkoutDatabaseHelper.getWritableDatabase();

                Cursor cursor = db.query("WORKOUT",
                        new String[]{"TIME"},
                        "_id = ?",
                        new String[]{Integer.toString(workoutNo)},
                        null, null, null);

                /**Get the number of seconds from the cursor*/
                if (cursor.moveToFirst()) {
                    seconds = cursor.getInt(0);
                    cursor.close();
                    db.close();} return true;
            }catch(SQLiteException e){
             /*Toast toast = Toast.makeText(c, "Database unavailable", Toast.LENGTH_SHORT);
             toast.show(); Can't post directly to UI thread*/ return false;}}

        protected void onPostExecute(Boolean success) {
            if (!success) {Toast toast = Toast.makeText(c,
                    "Database unavailable onPostExecute", Toast.LENGTH_SHORT);
                toast.show();}}}

    @Override    public void onPause() { super.onPause(); wasRunning = running;}

    @Override  public void onResume() { super.onResume();  if (wasRunning) { running = true;}}

    @Override   public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt("seconds", seconds);
        savedInstanceState.putBoolean("running", running);
        savedInstanceState.putBoolean("wasRunning", wasRunning);}

    @Override   public void onClick(View v) {

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
        }}

    public void onClickStart(View view) {
        running = true;}

    public void onClickStop(View view) {
        running = false;}

    public void onClickReset(View view) {
        running = false;
        seconds = 0;}

    public void deleteButtonClicked (View view){
        try {
            SQLiteOpenHelper WorkoutDatabaseHelper = new WorkoutDatabaseHelper(getActivity().getApplicationContext());
            SQLiteDatabase db = WorkoutDatabaseHelper.getWritableDatabase();

            String Name;
            TextView text1=(TextView) view.getRootView().findViewById(R.id.textTitle);
            Name=text1.getText().toString();
            db.execSQL("DELETE FROM " +"WORKOUT" + " WHERE " + "NAME" + "=\"" + Name + "\";");
            db.close();
        } catch(SQLiteException e) {
            Toast toast = Toast.makeText(getActivity().getApplicationContext(), "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }}}
