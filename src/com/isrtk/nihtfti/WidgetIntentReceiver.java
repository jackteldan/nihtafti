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

    private static int clickCount = 0;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.v("WidgetIntentReceiver" , "RECIVED");

        Intent intent2 = new Intent(context, NihtaftiActivity.class);
        intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent2);

        //if(intent.getAction().equals("pl.looksok.intent.action.CHANGE_PICTURE")){
            updateWidgetPictureAndButtonListener(context);
       // }
    }

    private void updateWidgetPictureAndButtonListener(Context context) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget);
        //remoteViews.setImageViewResource(R.id.widget_image, getImageToSet());

        //REMEMBER TO ALWAYS REFRESH YOUR BUTTON CLICK LISTENERS!!!
        remoteViews.setOnClickPendingIntent(R.id.widget_image, WidgetProvider.buildButtonPendingIntent(context));


        WidgetProvider.pushWidgetUpdate(context.getApplicationContext(), remoteViews);
    }

   // private int getImageToSet() {
    //    clickCount++;
   //     return clickCount % 2 == 0 ? R.drawable.me : R.drawable.wordpress_icon;
 //   }
}
