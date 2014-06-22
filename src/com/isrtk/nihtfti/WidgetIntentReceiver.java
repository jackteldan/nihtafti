package com.isrtk.nihtfti;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

/**
 * Created by Avi on 19/06/2014.
 */
public class WidgetIntentReceiver extends BroadcastReceiver {

    public static Toast mToastText;

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals("pl.looksok.intent.action.CHANGE_PICTURE")) {

            Log.v("WidgetIntentReceiver", "RECIVED");


            Long timeLastClicked = DataHelper.contextReadLong(context.getApplicationContext(), "timeLastClicked");


            if (timeLastClicked == 0) {
                firstClick(context);
            } else {
                if (timeLastClicked + MainService.timeToZero * 1000 < System.currentTimeMillis()) {
                    firstClick(context);
                } else {
                    moreClick(context);
                }
            }


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

    private void firstClick(Context context) {

        DataHelper.contextWriteLong(context.getApplicationContext(), "timeLastClicked", System.currentTimeMillis());
        DataHelper.contextWriteInt(context.getApplicationContext(), "clickValue", 0);

        Intent newIntent = new Intent(context.getApplicationContext(), MainService.class);
        newIntent.putExtra("clickValue", 0);
        context.startService(newIntent);


    }

    private void moreClick(Context context) {

        int clickValue = DataHelper.contextReadInt(context.getApplicationContext(), "clickValue");
        DataHelper.contextWriteInt(context.getApplicationContext(), "clickValue", clickValue + 1);
        DataHelper.contextWriteLong(context.getApplicationContext(), "timeLastClicked", System.currentTimeMillis());

        Intent newIntent = new Intent(context.getApplicationContext(), MainService.class);
        newIntent.putExtra("clickValue", clickValue + 1);
        context.startService(newIntent);

    }


}
