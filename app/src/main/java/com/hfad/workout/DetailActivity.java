package com.hfad.workout;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;/**in MainActivity  intent.putExtra(DetailActivity.EXTRA_WORKOUT_ID, (int)id);
        Intent intent = new Intent(this, DetailActivity.class);*/
public class DetailActivity extends FragmentActivity {

    public static final String EXTRA_WORKOUT_ID = "id";

@Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

       }}
