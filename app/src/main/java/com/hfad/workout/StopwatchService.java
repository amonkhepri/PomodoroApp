package com.hfad.workout;

/**
 * Created by sirth on 15/07/2017.
 */
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;

public class StopwatchService extends Service {

    private final IBinder binder = new StopwatchBinder();


    public class StopwatchBinder extends Binder {
        StopwatchService getStopwatch() {
            return StopwatchService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onCreate() {



    }

    @Override
    public void onDestroy() {

    }

}