   
package com.isrtk.nihtfti;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class NihtaftiActivity extends Activity {


    public static Toast     mToastText;
  @Override
  public void onCreate(Bundle state) {
    super.onCreate(state);
       Log.v("Activity", "onCreate");


      Long timeLastClicked = DataHelper.contextReadLong(this,"timeLastClicked");



    if(timeLastClicked == 0) {
        firstClick();
    } else {
        if (timeLastClicked + MainService.timeToZero * 1000 < System.currentTimeMillis()) {
            firstClick();
        } else {
            moreClick();
        }
    }

    finish();
  }


    private void firstClick() {

        DataHelper.contextWriteLong(this,"timeLastClicked",System.currentTimeMillis());
        DataHelper.contextWriteInt(this, "clickValue", 0);

        Intent newIntent = new Intent(this, MainService.class);
        newIntent.putExtra("clickValue", 0);
        startService(newIntent);


    }

    private void moreClick() {

        int clickValue = DataHelper.contextReadInt(this,"clickValue");
        DataHelper.contextWriteInt(this, "clickValue", clickValue + 1);
        DataHelper.contextWriteLong(this,"timeLastClicked",System.currentTimeMillis());

        Intent newIntent = new Intent(this, MainService.class);
        newIntent.putExtra("clickValue",  clickValue + 1);
        startService(newIntent);

    }







}