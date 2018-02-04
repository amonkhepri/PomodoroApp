package com.hfad.workout.View.Activities;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.hfad.workout.R;
import com.hfad.workout.SQL.WorkoutDatabaseHelper;



/*An Activity Used to create new Workouts*/
public class NewWorkout extends AppCompatActivity   {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_workout);

    }

    public void addButtonClicked(View view){
        try {
            SQLiteOpenHelper WorkoutDatabaseHelper = new WorkoutDatabaseHelper(this);
            SQLiteDatabase db = WorkoutDatabaseHelper.getWritableDatabase();

            String Name;
            String Description;

            EditText name=(EditText) findViewById(R.id.user_Input);
            EditText description=(EditText) findViewById(R.id.user_Input2);

            Name=name.getText().toString();
            Description=description.getText().toString();

            name.setText("");
            description.setText("");


            ContentValues workoutValues = new ContentValues();
            workoutValues.put("NAME",Name );
            workoutValues.put("DESCRIPTION", Description);
            db.insert("WORKOUT", null, workoutValues);

        } catch(SQLiteException e) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }

    }
}