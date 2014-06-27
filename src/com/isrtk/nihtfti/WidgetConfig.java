package com.isrtk.nihtfti;

import android.R.string;
import android.app.Activity;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RemoteViews;
import android.widget.Toast;

public class WidgetConfig extends Activity implements OnClickListener {

	EditText info1;
	AppWidgetManager awm;
	Context nihtftiContext;
	int awID;
	Toast mToastText;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wigetconfiglayout);
		Button b1 = (Button) findViewById(R.id.b1);
		b1.setOnClickListener(this);
		 nihtftiContext = WidgetConfig.this;
		 info1 = (EditText) findViewById(R.id.inf1);
		 
		 // code recommended for getting wifget info == JACK
		 Intent i = getIntent();
		 Bundle extras =i.getExtras();
		 if(extras != null)
		// get the ID WIDGET value from the bundle , if not get default INVALID VALUE == JACK
		 { awID = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
		 }else {finish();}
		 awm = AppWidgetManager.getInstance(nihtftiContext);
		 
	}
	
	public void onClick(View v) {
		// TODO Auto-generated method stub
		String test = info1.getText().toString();
		   Toast mToastText = Toast.makeText(getApplicationContext(), "", Toast.LENGTH_LONG);	
		   mToastText.setText(test);
	        mToastText.show();

	      //CODE for auto exiting for config Page after button pressed = JACK
	       RemoteViews widgetView = new RemoteViews(nihtftiContext.getPackageName(),R.layout.widget) ;
	      Intent in = new Intent(nihtftiContext,WidgetProvider.class);// is the intent we want to run after config =JACK
	      PendingIntent pi = PendingIntent.getActivity(nihtftiContext, 0, in, 0);// to create a intent to do form in in the nihtfti contex because that is how WIDGET works = JACK
	      widgetView.setOnClickPendingIntent(R.id.b1, pi);
	      
	      // to transfer data back to widget, had to bundle it back
	      Intent transferdataback =new Intent();
	      transferdataback.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,awID);
	      setResult(RESULT_OK,transferdataback);
	      finish();
	}
	


}
	
