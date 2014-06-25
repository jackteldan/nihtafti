package com.isrtk.nihtfti;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.Toast;

/**
 * Created by Avi on 22/06/2014.
 */
public class Settings extends Activity{


    // Declare
    static final int PICK_CONTACT=1;
    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(intent, PICK_CONTACT);
        setContentView(R.layout.settings);
    }

    //code
    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        switch (reqCode) {
            case (PICK_CONTACT) :
                if (resultCode == Activity.RESULT_OK) {
                    Uri contactData = data.getData(); // has the uri for picked contact
                    Cursor c = getContentResolver().query(contactData, null, null, null, null); // creates the contact cursor with the returned uri
                    if (c.moveToFirst()) {

                        int hasPhoneNumber = c.getInt(c.getColumnIndexOrThrow(ContactsContract.PhoneLookup.HAS_PHONE_NUMBER));
                        if (hasPhoneNumber == 1) {
                            String phoneNumber = c.getString(c.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            Toast.makeText(this,  " has number " + phoneNumber, Toast.LENGTH_LONG).show();

                        } else {

                            Toast.makeText(this,  " No number" , Toast.LENGTH_LONG).show();

                        }
                    }
                }
                break;
        }

    }


}
