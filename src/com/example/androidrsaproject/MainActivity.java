package com.example.androidrsaproject;


import java.math.BigInteger;

import android.app.Activity;
import android.content.Intent;

import android.os.Bundle;

import android.telephony.SmsManager;

import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

  

   public class MainActivity extends Activity {


      private EditText texttoencrypt;
      
      private EditText phoneno;
      
      private Button clicktoencrypt;

      private Button clicktosend;
      
      @Override

      protected void onCreate(Bundle savedInstanceState) {

          super.onCreate(savedInstanceState);

          setContentView(R.layout.activity_main);
          
          
		   /** Here , To excute Android Service to Recive Incoming Message . */
          //startService(new Intent(MainActivity.this,smsService.class));
          
          texttoencrypt = (EditText) findViewById(R.id.textToEncrypt);
          texttoencrypt.setHint("Encrypt Your Message");
          
          phoneno = (EditText) findViewById(R.id.phoneno);
          phoneno.setHint("Who will recive your Enc. Message");

          clicktoencrypt = (Button) findViewById(R.id.clicktoencrypt);
          clicktoencrypt.setOnClickListener(new OnClickListener() {
  			
  			@Override
  			public void onClick(View arg0) {
  				// TODO Auto-generated method stub

  				/*
  				 * Here , To Encrypt Message "Example to Check Encryption" in Another Activity.
  				 */
  				RSA rsa = new RSA(1024);
  				
  				String text1 = texttoencrypt.getText().toString();
  				System.out.println("Plaintext: " + text1);
  				
  			    BigInteger plaintext = new BigInteger(text1.getBytes());
  			    BigInteger ciphertext = rsa.encrypt(plaintext);
  			    //System.out.println("Ciphertext: " + ciphertext);
  			    Toast.makeText(getBaseContext(), "Ciphertext: " + ciphertext, Toast.LENGTH_SHORT).show();
  			    Intent intent = new Intent(MainActivity.this, EncActivity.class);
				intent.putExtra("Encrypt", ciphertext.toString());
				startActivity(intent);
  					
  			}
  		});

          
          clicktosend = (Button) findViewById(R.id.Send);
          clicktosend.setOnClickListener(new OnClickListener() {
        	  
  			@Override
  			public void onClick(View v) {
   
  				String phoneNo = phoneno.getText().toString();
  			  
  				/*
  				 * Here , To Encrypt Message And Send it .
  				 */
  			    RSA rsa = new RSA(1024);
				String text1 = texttoencrypt.getText().toString();
				System.out.println("Plaintext: " + text1);

			    BigInteger plaintext = new BigInteger(text1.getBytes());
			    BigInteger ciphertext = rsa.encrypt(plaintext);
			    Toast.makeText(getBaseContext(), "Ciphertext: " + ciphertext, Toast.LENGTH_SHORT).show();

  			  try {
	  				SmsManager smsManager = SmsManager.getDefault();
	  				smsManager.sendTextMessage(phoneNo, null, ciphertext.toString(), null, null);
	  				Toast.makeText(getApplicationContext(), "SMS Sent!",
	  							Toast.LENGTH_LONG).show();
  			  	} catch (Exception e) {
	  				Toast.makeText(getApplicationContext(),
	  					"SMS faild, please try again later!",
	  					Toast.LENGTH_LONG).show();
	  				e.printStackTrace();
  			  	}

  			}
  		});

  }
      
      @Override

      public boolean onCreateOptionsMenu(Menu menu) {

          getMenuInflater().inflate(R.menu.main, menu);

          return true;

      }
 }
