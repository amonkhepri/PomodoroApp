package com.hfad.workout;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

/**Used in StopwatchService.StopwatchBinder */
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