   
package com.isrtk.nihtfti;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class NihtaftiActivity extends Activity {
  public static final int timeToZero = 30; // [s]
  public static final int maxClick = 5;

    public static Toast     mToastText;
  @Override
  public void onCreate(Bundle state) {
    super.onCreate(state);
        Log.v("Activity", "onCreate");

      mToastText = Toast.makeText(this, "", Toast.LENGTH_SHORT);

      Long timeLastClicked = DataHelper.contextReadLong(this,"timeLastClicked");

    if(timeLastClicked == 0) {
        firstClick();
    } else {
        if (timeLastClicked + timeToZero * 1000 < System.currentTimeMillis()) {
            firstClick();
        } else {
            moreClick();
        }
    }



    finish();
  }


    private void firstClick() {
        Log.v("Activity", "firstClick");
        Log.v("Activity", "Toast");

        DataHelper.contextWriteLong(this,"timeLastClicked",System.currentTimeMillis());
        DataHelper.contextWriteInt(this, "clickValue", 1);
        displayText("5");

    }

    private void moreClick() {
        Log.v("Activity", "moreClick");

        int clickValue = DataHelper.contextReadInt(this,"clickValue");
      //  if (clickValue == maxClick) {
       //     callNihtafti();
      //  } else {
            Log.v("Activity", "Toast");

            DataHelper.contextWriteInt(this, "clickValue", clickValue + 1);
               displayText("" + (maxClick - clickValue));
      //  }

    }

    private void displayText(final String message) {
       mToastText.cancel();
        mToastText = Toast.makeText(this, "", Toast.LENGTH_SHORT);
        mToastText.setText(message);
        mToastText.show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.v("Activity", "onDestroy");

    }

    private void callNihtafti() {


    }

}