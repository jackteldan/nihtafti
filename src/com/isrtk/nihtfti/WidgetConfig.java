package com.isrtk.nihtfti;

import android.R.string;
import android.app.Activity;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

public class WidgetConfig extends PreferenceActivity{


    ArrayAdapterItem adapter;
    ListView listView;
    public static final int WIDGET_ID = 1;
    Integer[] values = {1,2,3,4,5};

    @Override
    public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.wigetconfiglayout);
        setContentView(R.layout.settings);




        // our adapter instance
        adapter = new ArrayAdapterItem(this, R.layout.settings_number_line, values);
        listView = (ListView) findViewById(R.id.number_list);
        listView.setAdapter(adapter);


        /**************************************************
        //TODO:: use this code for multiple widgets

		 // code recommended for getting widget info == JACK
		 Intent i = getIntent();
		 Bundle extras =i.getExtras();
		 if(extras != null)
		// get the ID WIDGET value from the bundle , if not get default INVALID VALUE == JACK
		 { awID = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
		 }else {finish();}

		 awm = AppWidgetManager.getInstance(nihtftiContext);

		 ***************************************************/

	}

    @Override
    public void onDestroy() {
        super.onDestroy();
        saveNumbers();
    }

    @Override
    public void onPause() {
        super.onPause();
        saveNumbers();
    }

    private void saveNumbers() {
        for (int i=0;i<values.length;i++) {
            EditText editTextItem = (EditText) listView.getChildAt(i).findViewById(R.id.editText);
            setNumber(values[i],editTextItem.getText().toString());

        }
    }
    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent intent) {
        super.onActivityResult(reqCode, resultCode, intent);

                if (resultCode == Activity.RESULT_OK) {
                    Uri contactData = intent.getData(); // has the uri for picked contact
                    Cursor c = getContentResolver().query(contactData, null, null, null, null); // creates the contact cursor with the returned uri
                    if (c.moveToFirst()) {

                        String id = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));

                        int hasPhoneNumber = c.getInt(c.getColumnIndexOrThrow(ContactsContract.PhoneLookup.HAS_PHONE_NUMBER));
                        if (hasPhoneNumber == 1) {

                            Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,
                                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = "+ id,null, null);
                            phones.moveToFirst();
                            String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            setNumber(reqCode,phoneNumber);
                            Toast.makeText(this,  " has number " + phoneNumber + " i = " + reqCode, Toast.LENGTH_LONG).show();

                        } else {

                            Toast.makeText(this,  " No number" , Toast.LENGTH_LONG).show();

                        }
                    }
                }


    }


    public class ArrayAdapterItem extends ArrayAdapter<Integer> {

        Context mContext;
        int layoutResourceId;
        Integer data[] = null;

        public ArrayAdapterItem(Context mContext, int layoutResourceId, Integer[] data) {

            super(mContext, layoutResourceId, data);

            this.layoutResourceId = layoutResourceId;
            this.mContext = mContext;
            this.data = data;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if(convertView==null){
                // inflate the layout
                LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
                convertView = inflater.inflate(layoutResourceId, parent, false);
            }

            // object item based on the position
            final Integer fieldId = data[position];

            EditText editTextItem = (EditText) convertView.findViewById(R.id.editText);
            editTextItem.setTag(fieldId);

            String savedNumber =  DataHelper.contextReadString(WidgetConfig.this,WIDGET_ID  +"_" + fieldId);
            if (savedNumber != null && !savedNumber.equals(editTextItem.getText())) {
                editTextItem.setText(savedNumber);
            }

            TextView textViewItem = (TextView) convertView.findViewById(R.id.textView);
            textViewItem.setText(getResources().getString(R.string.number) + " " + fieldId +" :");

            Button buttonItem = (Button) convertView.findViewById(R.id.button);
            buttonItem.setTag(fieldId);

            return convertView;

        }


    }



    public void onAddContactClick(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        int fieldId = (Integer) view.getTag();
        intent.putExtra("fieldId",fieldId);
        Log.v("WidgetConfig", "i=" + fieldId);
        startActivityForResult(intent, fieldId);

    }


    public void setNumber (int fieldId,String number) {
            DataHelper.contextWriteString(this,WIDGET_ID  +"_" + fieldId,number);
    }

}


