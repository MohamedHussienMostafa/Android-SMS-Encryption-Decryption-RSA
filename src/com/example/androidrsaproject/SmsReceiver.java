package com.example.androidrsaproject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.gsm.SmsMessage;
import android.widget.Toast;
 
public class SmsReceiver extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent) 
    {
        //---get the SMS message passed in---
        Bundle bundle = intent.getExtras();        
        SmsMessage[] msgs = null;
        String str = "";            
        if (bundle != null)
        {
            //---retrieve the SMS message received---
            Object[] pdus = (Object[]) bundle.get("pdus");
            msgs = new SmsMessage[pdus.length];            
            for (int i=0; i<msgs.length; i++){
                msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);                
                //str += "SMS from " + msgs[i].getOriginatingAddress();                     
                //str += " :";
                str += msgs[i].getMessageBody().toString();        
            }
            //---display the new SMS message---
            Toast.makeText(context, "el rsala " + str, Toast.LENGTH_SHORT).show();
            
            Intent i = new Intent(context, EncActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.putExtra("Encrypt", str);
            context.startActivity(i); 
            
        }         
        /*
         * <receiver android:name=".SmsReceiver">
            <intent-filter>
                <action android:name="android.provider.Telephony.SMS_RECEIVED" />
            </intent-filter>
        </receiver>
         */
    }
}