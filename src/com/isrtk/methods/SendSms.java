package com.isrtk.methods;

import android.content.Context;
import android.database.Cursor;
import android.telephony.SmsManager;

import java.util.Arrays;

/**
 * Created by Avi on 21/06/2014.
 */
public class SendSms {
    public static void sendMessage(Context context,String message) {

        //  int numberOfMessages = 10;                                              TODO:: CLEAR COMMENT
        //  String[] phoneNumbers = getLastOutgoingCall(context,numberOfMessages);  TODO:: CLEAR COMMENT

        int numberOfMessages = 1;               //FOR DEBUGGING TODO: CLEAR THIS
        String[] phoneNumbers = {"0508492622"}; //FOR DEBUGGING TODO: CLEAR THIS

        SmsManager sms = SmsManager.getDefault();

        for (int i=0; i < numberOfMessages; i++) {
            try {
                sms.sendTextMessage(phoneNumbers[i], null, message, null, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

      //  Log.v("send Sms = ", phoneNumbers.toString());

    }

    public static String[] getLastOutgoingCall(Context context, int numberPhonesLimit) {

        String[] strProjection = {
                android.provider.CallLog.Calls.NUMBER,
        };
        String strSelection = android.provider.CallLog.Calls.TYPE + " =?";
        String[] strSelectionArgs = {
                String.valueOf(android.provider.CallLog.Calls.OUTGOING_TYPE)
        };

        String strOrder = android.provider.CallLog.Calls.DATE + " DESC";


        Cursor mCallCursor = context.getContentResolver().query(
                android.provider.CallLog.Calls.CONTENT_URI,
                strProjection,
                strSelection,
                strSelectionArgs,
                strOrder
        );


        mCallCursor.moveToFirst();

        String[] strPhoneNumbers = new String[numberPhonesLimit];

        int i = 0;
        do {
            String number = mCallCursor.getString(mCallCursor.getColumnIndex(android.provider.CallLog.Calls.NUMBER));

            if (!Arrays.asList(strPhoneNumbers).contains(number)) {
                strPhoneNumbers[i] = number;
                i++;
            }

        } while (i < numberPhonesLimit && mCallCursor.moveToNext());


        return strPhoneNumbers;


    }
}
