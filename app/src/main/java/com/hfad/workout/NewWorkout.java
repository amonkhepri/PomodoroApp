package com.hfad.workout;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
/*used in MainActivity*/
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

            EditText text1=(EditText) findViewById(R.id.user_Input);
            EditText text2=(EditText) findViewById(R.id.user_Input2);
            Name=text1.getText().toString();
            Description=text2.getText().toString();
            text1.setText("");
            text2.setText("");


            ContentValues workoutValues = new ContentValues();
            workoutValues.put("NAME",Name );
            workoutValues.put("DESCRIPTION", Description);
            db.insert("WORKOUT", null, workoutValues);

        } catch(SQLiteException e) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();}}}