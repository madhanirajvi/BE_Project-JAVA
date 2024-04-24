package com.example.maliba;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Tracking extends Activity{
	
	     
	    Button b1;
	     
	   
	    GPSTracker gps;
	     
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.gpstracker);
	         
	      b1 = (Button) findViewById(R.id.button1);
	         
	        // show location button click event
	        b1.setOnClickListener(new View.OnClickListener() {
	             
	            @Override
	            public void onClick(View arg0) {        
	                // create class object
	                gps = new GPSTracker(Tracking.this);
	 
	                // check if GPS enabled     
	                if(gps.canGetLocation()){
	                     
	                    double latitude = gps.getLatitude();
	                    double longitude = gps.getLongitude();
	                     
	                    // \n is for new line
	                    Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();    
	                }else{
	                    // can't get location
	                    // GPS or Network is not enabled
	                    // Ask user to enable GPS/network in settings
	                    gps.showSettingsAlert();
	                }
	                 
	            }
	        });
	    }
	     
	}
