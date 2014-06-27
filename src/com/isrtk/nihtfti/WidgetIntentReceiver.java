package com.isrtk.nihtfti;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

/**
 * Created by Avi on 19/06/2014.
 */
public class WidgetIntentReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals("pl.looksok.intent.action.CHANGE_PICTURE")) {

            Log.v("WidgetIntentReceiver", "RECIVED");


            Intent newIntent = new Intent(context.getApplicationContext(), MainService.class);
            newIntent.putExtra("click", true);
            context.startService(newIntent);


            updateWidgetPictureAndButtonListener(context);
        }
    }


    private void updateWidgetPictureAndButtonListener(Context context) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget);
        //remoteViews.setImageViewResource(R.id.widget_image, getImageToSet());

        //REMEMBER TO ALWAYS REFRESH YOUR BUTTON CLICK LISTENERS!!!
        remoteViews.setOnClickPendingIntent(R.id.widget_image, WidgetProvider.buildButtonPendingIntent(context));


        WidgetProvider.pushWidgetUpdate(context.getApplicationContext(), remoteViews);
    }



}
