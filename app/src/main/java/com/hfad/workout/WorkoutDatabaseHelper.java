package com.hfad.workout;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
/*Used in
*NewWorkout
*StopwatchFragment
*WorkoutDetailFragment
*WorkoutDetailFragment.UpdateWorkoutTask
*WorkoutListFragment*/
class WorkoutDatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "workout"; // the name of our database
    private static final int DB_VERSION = 8; // the version of the database

    WorkoutDatabaseHelper(Context context) {
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
                    + "NAME TEXT, "
                    + "DESCRIPTION TEXT"
                    + "TIME NUMERIC);) "
                   );
            insertWorkout(db, "Latte", "Espresso and steamed milk");
            insertWorkout(db, "Cappuccino", "Espresso, hot milk and steamed-milk foam"
                   );
      /*  db.execSQL("ALTER TABLE WORKOUT ADD COLUMN TIME BLOB;");*/
       /* }*/
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
}
