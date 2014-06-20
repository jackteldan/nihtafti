package com.isrtk.nihtfti;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.util.Timer;

/**
 * Created by Avi on 18/06/2014.
 */
public class MainService extends Service {

    public boolean startAction = false;
    int clickValue;
    Toast mToastText = null;
    Timer timer = new Timer();

    public static final int timeToZero = 10; // [s]
    public static final int maxClick = 5;

    @Override
    public IBinder onBind(Intent intent) {
        //TODO for communication return IBinder implementation

            return null;
    }




    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (mToastText == null) {
            Log.v("MainService", "Created");
            mToastText = Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT);
        }


        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Long timeLastClicked = DataHelper.contextReadLong(getApplicationContext(),"timeLastClicked");

                if (timeLastClicked + MainService.timeToZero * 1000 < System.currentTimeMillis()) {
                    MainService.this.stopSelf();
                }
            }
        }, 2000*timeToZero);


        clickValue = intent.getIntExtra("clickValue", 0);

        if (clickValue >= maxClick) {
            startAction = true;
            startToWork();
        } else {
            displayText("" + (maxClick - clickValue));

        }


        // We want this service to continue running until it is explicitly
        // stopped, so return sticky.
        return START_STICKY;
    }

    //this method display the toast
    private void displayText(final String message) {
        mToastText.setText(message);
        mToastText.show();
    }

    //this method start the actions the the app do when someone kidnapped
    private void startToWork() {

    }



}
