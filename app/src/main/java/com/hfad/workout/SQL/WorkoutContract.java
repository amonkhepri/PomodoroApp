package com.hfad.workout.SQL;

import android.provider.BaseColumns;

/**
 * Created by delaroy on 5/11/17.
 */
public class WorkoutContract {

    public static final class WorkoutEntry implements BaseColumns {

        public static final String TABLE_NAME = "WORKOUT";
        public static final String COLUMN_WORKOUT_NAME = "NAME";
        public static final String COLUMN_WORKOUT_DESCRIPTION = "DESCRIPTION";
        public static final String COLUMN_WORKOUT_TIME = "TIME";

    }
}
