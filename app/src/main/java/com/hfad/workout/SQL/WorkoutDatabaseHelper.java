package com.hfad.workout.SQL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.hfad.workout.SQL.Model.Workout;

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

    //TODO WorkoutDataBaseHelper oldVersion and newVersion aren't used, should I just remove them?
    private void updateMyDatabase(SQLiteDatabase db, int oldVersion, int newVersion) {
        /*if (oldVersion < 3) {*/
            db.execSQL("CREATE TABLE IF NOT EXISTS WORKOUT (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + " NAME TEXT, "
                    + " DESCRIPTION TEXT, "
                    + " TIME INTEGER );"
                   );
            //Some initially programmed data
            insertWorkout(db, "Programming", "10 hours a day");
            insertWorkout(db, "Guitar playing", "5 hours a day" );

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
                Workout workout = new Workout(); //TODO Had some issues here, wonder why? Anyway, it works again after reverting changes.
                workout.setId(Integer.parseInt(cursor.
                        getString(cursor.getColumnIndex(WorkoutContract.WorkoutEntry._ID))));
                workout.setName(cursor.getString(cursor.
                        getColumnIndex(WorkoutContract.WorkoutEntry.COLUMN_WORKOUT_NAME)));
                workout.setDescription(cursor.getString(cursor.
                        getColumnIndex(WorkoutContract.WorkoutEntry.COLUMN_WORKOUT_DESCRIPTION)));
                workout.setTime(cursor.getInt(cursor
                        .getColumnIndex(WorkoutContract.WorkoutEntry.COLUMN_WORKOUT_TIME)));
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
        ArrayList<Cursor> ArrayListCursor = new ArrayList<Cursor>(2);
        MatrixCursor matrixCursor= new MatrixCursor(columns);
        ArrayListCursor.add(null);
        ArrayListCursor.add(null);

        try{
            String maxQuery = Query ;
            //execute the query results will be save in Cursor c
            Cursor cursorQuery = sqlDB.rawQuery(maxQuery, null);

            matrixCursor.addRow(new Object[] { "Success" });

            ArrayListCursor.set(1,matrixCursor);
            if (null != cursorQuery && cursorQuery.getCount() > 0) {

                ArrayListCursor.set(0,cursorQuery);
                cursorQuery.moveToFirst();

                return ArrayListCursor ;
            }
            return ArrayListCursor;
        } catch(SQLException sqlEx){
            Log.d("printing exception", sqlEx.getMessage());
            //if any exceptions are triggered save the error message to cursor an return the arraylist
            matrixCursor.addRow(new Object[] { ""+sqlEx.getMessage() });
            ArrayListCursor.set(1,matrixCursor);
            return ArrayListCursor;
        } catch(Exception exception){
            Log.d("printing exception", exception.getMessage());

            //if any exceptions are triggered save the error message to cursor and return the arrayList
            matrixCursor.addRow(new Object[] { ""+exception.getMessage() });
            ArrayListCursor.set(1,matrixCursor);
            return ArrayListCursor;
        }
    }

}
