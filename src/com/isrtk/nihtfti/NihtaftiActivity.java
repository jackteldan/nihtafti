   
package com.isrtk.nihtfti;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

public class NihtaftiActivity extends Activity {
  @Override
  public void onCreate(Bundle state) {
    super.onCreate(state);
    

    Toast.makeText(this, "aaa", Toast.LENGTH_LONG).show();
    
    finish();
  }
}