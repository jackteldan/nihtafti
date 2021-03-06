package com.isrtk.nihtfti;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.isrtk.methods.SendSms;

import java.util.Timer;

/**
 * Created by Avi on 18/06/2014.
 */
public class MainService extends Service {

    public boolean startAction = false;
    int clickValue = 0;
    Toast mToastText = null;
    Timer timer = new Timer();
    RemoteViews remoteViews;
    long timeLastClicked = 0;

    public static final int timeToZero = 10; // [s]
    public static final int maxClick = 5;

    public MainService() {


    }
    @Override
    public IBinder onBind(Intent intent) {
        //TODO for communication return IBinder implementation

            return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (mToastText == null) {
            mToastText = Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT);
            remoteViews = new RemoteViews(getPackageName(), R.layout.widget);
        }

        closeServiceIfNoAction();

        Boolean click = intent.getBooleanExtra("click",false);
        if (click != null && click) {
            Log.v("MainService", "Click");
            if (timeLastClicked == 0) {
                clickValue = 1;
            } else {
                if (timeLastClicked + MainService.timeToZero * 1000 < System.currentTimeMillis()) {
                    clickValue = 1;
                } else {
                    clickValue += 1;
                }
            }


            timeLastClicked = System.currentTimeMillis();

            if (clickValue >= maxClick ) {
                if (!startAction) {
                    startToWork();
                    startAction = true;
                }
            } else {
                displayText(" לחץ " + (maxClick - clickValue)+"לחץ עוד  ");
                remoteViews.setTextViewText(R.id.widget_image,"S.O.S\n"+ "עוד " + (maxClick - clickValue)+"X"+" לחיצות");
                WidgetProvider.pushWidgetUpdate(getApplicationContext(), remoteViews);
            }

        }






        // We want this service to continue running until it is explicitly
        // stopped, so return sticky.
        return START_STICKY;
    }

    private void closeServiceIfNoAction() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {


                if (timeLastClicked + MainService.timeToZero * 1000 < System.currentTimeMillis() && startAction == false) {

                    remoteViews.setTextViewText(R.id.widget_image, getResources().getString(R.string.widget_first_text));
                    WidgetProvider.pushWidgetUpdate(getApplicationContext(), remoteViews);

                    try {
                        MainService.this.stopSelf();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        }, 2000*timeToZero);
    }

    //this method display the toast
    private void displayText(final String message) {
        mToastText.setText(message);
        mToastText.show();
       

    }

    //this method start the actions the the app do when someone kidnapped
    private void startToWork() {

        displayText(getResources().getString(R.string.alarm_activated));

        remoteViews.setTextViewText(R.id.widget_image, getResources().getString(R.string.alarm_activated));
        WidgetProvider.pushWidgetUpdate(getApplicationContext(), remoteViews);

        putSilentMode();

        SendSms.sendMessage(getApplicationContext(), "בדיקה");

    }

    private void putSilentMode() {
        AudioManager am = (AudioManager) getBaseContext().getSystemService(this.AUDIO_SERVICE);
        am.setRingerMode(AudioManager.RINGER_MODE_SILENT);
    }


}
