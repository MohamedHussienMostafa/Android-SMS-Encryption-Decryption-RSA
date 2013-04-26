package com.example.androidrsaproject;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.IBinder;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class smsService extends Service {

	public static final String SMS_EXTRA_NAME = "pdus";

	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	 @Override
	 public void onCreate() {
	  // TODO Auto-generated method stub
		 
    	    IntentFilter filter = new IntentFilter();
	        filter.setPriority(2147483647);
	        filter.addAction("android.provider.Telephony.SMS_RECEIVED");
	        registerReceiver(receiver, filter);		
		}

	 
	 private final BroadcastReceiver receiver = 
			 new BroadcastReceiver() {
		 
		   @Override
		   public void onReceive(Context context, Intent intent) {
		      String action = intent.getAction();
		      if(action.equals("android.provider.Telephony.SMS_RECEIVED")){
		        //action for sms received
		    	  Toast.makeText( context, "2ra el rsala" , Toast.LENGTH_SHORT ).show();
		    	  Bundle extras = intent.getExtras();
		          String messages = "";
		          String address= "";
		          
		          if ( extras != null )
		          {  
		              Object[] smsExtra = (Object[]) extras.get( SMS_EXTRA_NAME );
		              for ( int i = 0; i < smsExtra.length; ++i )
		              {
		              	SmsMessage sms = SmsMessage.createFromPdu((byte[])smsExtra[i]);
		              	String body = sms.getMessageBody().toString();
		              	address = sms.getOriginatingAddress();                         
		                  messages = body;   
		              }      
		              
		             // if (messages.contains("SM")){
		              
		              Toast.makeText( context, "SM gat" , Toast.LENGTH_SHORT ).show();
		              Toast.makeText( context, "messages" +  messages, Toast.LENGTH_SHORT ).show();
		              //Intent intent1 = new Intent(smsService.this, EncActivity.class);
						//intent1.putExtra("Encrypt", messages);
						
		              Intent dialogIntent = new Intent(getBaseContext(), EncActivity.class);
		              dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		              dialogIntent.putExtra("Encrypt", messages);
		              getApplication().startActivity(dialogIntent);


		              //}
		          }
		      }
		   }
	 };
}
