package com.hfad.workout.SQL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.hfad.workout.Model.Workout;

import java.util.ArrayList;
import java.util.List;

public class WorkoutDatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "workout"; // the name of our database
    private static final int DB_VERSION = 11; // the version of the database

    public WorkoutDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        updateMyDatabase(db, 0, DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        updateMyDatabase(db, oldVersion, newVersion);
    }

    private void updateMyDatabase(SQLiteDatabase db, int oldVersion, int newVersion) {
        /*if (oldVersion < 3) {*/
            db.execSQL("CREATE TABLE IF NOT EXISTS WORKOUT (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + " NAME TEXT, "
                    + " DESCRIPTION TEXT, "
                    + " TIME INTEGER );"
                   );
           insertWorkout(db, "Latte", "Espresso and steamed milk");
            insertWorkout(db, "Cappuccino", "Espresso, hot milk and steamed-milk foam"
                   );

        insertWorkout(db, "Filter", "Our best drip coffee");
    }

    private static void insertWorkout(SQLiteDatabase db, String name,
                                    String description) {
        ContentValues workoutValues = new ContentValues();
        workoutValues.put("NAME", name);
        workoutValues.put("DESCRIPTION", description);
        workoutValues.put("TIME",3);
        db.insert("WORKOUT", null, workoutValues);

    }

    public List<Workout> getAllWorkoutData() {
        // array of columns to fetch
        String[] columns = {
                WorkoutContract.WorkoutEntry._ID,
                WorkoutContract.WorkoutEntry.COLUMN_WORKOUT_NAME,
                WorkoutContract.WorkoutEntry.COLUMN_WORKOUT_DESCRIPTION,
                WorkoutContract.WorkoutEntry.COLUMN_WORKOUT_TIME,

        };
        // sorting orders
        String sortOrder = WorkoutContract.WorkoutEntry.COLUMN_WORKOUT_NAME + " ASC";

        List<Workout> workoutList = new ArrayList<Workout>();

        SQLiteDatabase db = this.getReadableDatabase();


        Cursor cursor = db.query(WorkoutContract.WorkoutEntry.TABLE_NAME, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order


        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Workout workout = new Workout();
                workout.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(WorkoutContract.WorkoutEntry._ID))));
                workout.setName(cursor.getString(cursor.getColumnIndex(WorkoutContract.WorkoutEntry.COLUMN_WORKOUT_NAME)));
                workout.setDescription(cursor.getString(cursor.getColumnIndex(WorkoutContract.WorkoutEntry.COLUMN_WORKOUT_DESCRIPTION)));
                workout.setTime(cursor.getInt(cursor.getColumnIndex(WorkoutContract.WorkoutEntry.COLUMN_WORKOUT_TIME)));
                // Adding user record to list


                workoutList.add(workout);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return user list
        return workoutList;
    }

    public ArrayList<Cursor> getData(String Query){
        //get writable database
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        String[] columns = new String[] { "message" };
        //an array list of cursor to save two cursors one has results from the query
        //other cursor stores error message if any errors are triggered
        ArrayList<Cursor> alc = new ArrayList<Cursor>(2);
        MatrixCursor Cursor2= new MatrixCursor(columns);
        alc.add(null);
        alc.add(null);

        try{
            String maxQuery = Query ;
            //execute the query results will be save in Cursor c
            Cursor c = sqlDB.rawQuery(maxQuery, null);

            //add value to cursor2
            Cursor2.addRow(new Object[] { "Success" });

            alc.set(1,Cursor2);
            if (null != c && c.getCount() > 0) {

                alc.set(0,c);
                c.moveToFirst();

                return alc ;
            }
            return alc;
        } catch(SQLException sqlEx){
            Log.d("printing exception", sqlEx.getMessage());
            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[] { ""+sqlEx.getMessage() });
            alc.set(1,Cursor2);
            return alc;
        } catch(Exception ex){
            Log.d("printing exception", ex.getMessage());

            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[] { ""+ex.getMessage() });
            alc.set(1,Cursor2);
            return alc;
        }
    }

}
