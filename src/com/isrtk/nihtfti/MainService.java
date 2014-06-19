package com.isrtk.nihtfti;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.util.Timer;

/**
 * Created by Avi on 18/06/2014.
 */
public class MainService extends Service {

    int lastClickValue = -1;
    int clickValue;
    Toast mToastText = null;
    Timer timer = new Timer();


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

        this.clickValue = intent.getIntExtra("clickValue", 0);
        Log.v("MainService", "got call");


        if (this.clickValue == 0) {
                 displayText("" + NihtaftiActivity.maxClick);
            Log.v("MainService", "" + NihtaftiActivity.maxClick);
        } else {
                    displayText("" + (NihtaftiActivity.maxClick - clickValue));
            Log.v("MainService","" + (NihtaftiActivity.maxClick - clickValue));
        }


        // We want this service to continue running until it is explicitly
        // stopped, so return sticky.
        return START_STICKY;
    }

    private void displayText(final String message) {

        mToastText.setText(message);
        mToastText.show();
    }
}
