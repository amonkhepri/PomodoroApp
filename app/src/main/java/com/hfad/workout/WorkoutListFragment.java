 package com.hfad.workout;

import android.os.Bundle;
import android.app.ListFragment;
import android.view.View;
import android.app.Activity;
import android.widget.ListView;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.CursorAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

/*public class MainActivity extends AppCompatActivity
implements WorkoutListFragment.WorkoutListListener { */

public class WorkoutListFragment extends ListFragment {

    public boolean flag;
  /*  private SQLiteDatabase db;
    private Cursor cursor;
*/
    static interface WorkoutListListener {
        void itemClicked(long id);
    };
    
    private WorkoutListListener listener;

    private SQLiteDatabase db;
    private Cursor cursor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            SQLiteOpenHelper WorkoutDatabaseHelper =
                    new WorkoutDatabaseHelper(getActivity().getApplicationContext());
            db = WorkoutDatabaseHelper.getReadableDatabase();

            cursor = db.query("WORKOUT",
                    new String[]{"_id", "NAME"},
                    null, null, null, null, null);

            CursorAdapter listAdapter =
                    new SimpleCursorAdapter(getActivity().getApplicationContext(),
                    android.R.layout.simple_list_item_1,
                    cursor,
                    new String[]{"NAME"},
                    new int[]{android.R.id.text1},
                    0);
           setListAdapter(listAdapter);
        } catch(SQLiteException e) {
            Toast toast = Toast.makeText(getActivity().getApplicationContext(),
                    "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();}
    flag=true;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.listener = (WorkoutListListener)activity;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        if (listener != null) {
            listener.itemClicked(id);
        }
    }
    @Override
    public void onResume(){
        super.onResume();
        try {
            SQLiteOpenHelper WorkoutDatabaseHelper = new WorkoutDatabaseHelper(getActivity().getApplicationContext());
            db = WorkoutDatabaseHelper.getReadableDatabase();

           Cursor NewCursor = db.query("WORKOUT",
                    new String[]{"_id", "NAME"},
                    null, null, null, null, null);

            CursorAdapter listAdapter = new SimpleCursorAdapter(getActivity().getApplicationContext(),
                    android.R.layout.simple_list_item_1,
                    NewCursor,
                    new String[]{"NAME"},
                    new int[]{android.R.id.text1},
                    0);
           listAdapter.changeCursor(NewCursor);
            setListAdapter(listAdapter);
        } catch(SQLiteException e) {
            Toast toast = Toast.makeText(getActivity().getApplicationContext(), "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();}

    }
}
