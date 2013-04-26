package com.example.androidrsaproject;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.gsm.SmsMessage;
import android.util.Log;
import android.widget.Toast;
 
public class Receiver extends BroadcastReceiver
{
	private static final String TAG = "smsfwd";
    private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";

    private NotificationManager mNotificationManager;
    private int SIMPLE_NOTFICATION_ID;
    String str="";
    String SMS_MIME_TYPE = "vnd.android-dir/mms-sms";

    public void onReceive(Context context, Intent intent) {
        Intent defineIntent = new Intent(Intent.ACTION_MAIN);                

        defineIntent.setType(SMS_MIME_TYPE);
        Log.i(TAG, "Intent recieved: " + intent.getAction());
        mNotificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (intent.getAction().equals(SMS_RECEIVED)) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                Object[] pdus = (Object[])bundle.get("pdus");
                final SmsMessage[] messages = new SmsMessage[pdus.length];

                for (int i = 0; i < pdus.length; i++) {
                    messages[i] = SmsMessage.createFromPdu((byte[])pdus[i]);

                    //str += "SMS from" + messages[i].getOriginatingAddress();                     
                    //str += ":";
                    str += messages[i].getMessageBody().toString();
                    //str += "\n";    
                }
                Log.i(TAG, "Message recieved: " + messages[0].getMessageBody());
                if (messages.length > -1) {
                    Log.i(TAG, "Message recieved: " + messages[0].getMessageBody());
                    mNotificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
                    Notification notifyDetails = new Notification(R.drawable.ic_launcher,"message received",System.currentTimeMillis());
                    //PendingIntent myIntent = PendingIntent.getActivity(context, 0, new Intent(Intent.ACTION_VIEW), 0);
                      PendingIntent myIntent = PendingIntent.getActivity(context, 0 , defineIntent, 0);

                    notifyDetails.setLatestEventInfo(context, str, "", myIntent); 
                    notifyDetails.flags = Notification.FLAG_AUTO_CANCEL;
                    notifyDetails.flags = Notification.DEFAULT_SOUND;
                    mNotificationManager.notify(SIMPLE_NOTFICATION_ID, notifyDetails);
                    Toast.makeText(context, "el rsala " + str, Toast.LENGTH_SHORT).show();
              }
                Intent i = new Intent(context, EncActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("Encrypt", str);
                context.startActivity(i);
            }
         }
       }
}