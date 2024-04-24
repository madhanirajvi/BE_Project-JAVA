
package com.example.maliba;



import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	Button submit,button2;
	EditText e1,e2;
	SQLiteDatabase db;

	TextView t3;
	public static String  user="";
	
	public static String NAMESPACE = "http://tempuri.org/";
	public static String  URL = "http://192.168.1.34/TourismHelpDesk/WebService.asmx";
	public static String  imgaddress = "http://192.168.1.34/TourismHelpDesk/img/"; 
	
	public static String SOAP_ACTION = "http://tempuri.org/loginuser";
	String METHOD_NAME = "loginuser"; 
	 String Uname,Password;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_main);
		
		e1= (EditText) findViewById(R.id.editText1);
		e2= (EditText) findViewById(R.id.editText2);
		submit = (Button) findViewById(R.id.submit);
		
		t3= (TextView) findViewById(R.id.textView3);
		
		db= openOrCreateDatabase("TourismHelpDesk.db",MODE_PRIVATE, null);
		
		
		if(!isMyServiceRunning(backgroundservice.class))
		{
			startService(new Intent(MainActivity.this,backgroundservice.class));	
		}
		
		submit.setOnClickListener(new View.OnClickListener() {
				
		@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				                              
			
			if (e1.getText().toString().equals("")||e2.getText().toString().equals(""))
			{
				Toast.makeText(MainActivity.this,"all data required", 1).show();
			}
			
			else
			{

				new DoLogin().execute();
			}		
	
		}
			
		});
	
	}
	public boolean isMyServiceRunning(Class<?> serviceClass) {
	    ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
	    for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
	        if (serviceClass.getName().equals(service.service.getClassName())) {
	            return true;
	        }
	    }
	    return false;
	}
	class DoLogin extends AsyncTask<Void, Void, String>
	{
		private ProgressDialog pdia;
		
		@Override
		protected String doInBackground(Void... params) {
			// TODO Auto-generated method stub
			
			try
			{
				SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		     
		      
		       
		       request.addProperty("Uname", e1.getText().toString());
		       request.addProperty("Password", e2.getText().toString());
		      
		            	        
		        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		        envelope.dotNet=true;
		        envelope.setOutputSoapObject(request);
		        
		        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
		        
		        androidHttpTransport.call(SOAP_ACTION, envelope);
		        SoapPrimitive resultstr = (SoapPrimitive)envelope.getResponse();
		        
		        Log.d("message","MEssage : "+resultstr.toString());
		        return resultstr.toString();
		        
//		        Toast.makeText(Reg.this, resultstr.toString(), Toast.LENGTH_SHORT).show();
			}
			catch(Exception e)
			{
				Log.e("error13", e.toString());
				return "fail,fail";
				
			}
				
			
			
		}
		
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pdia = new ProgressDialog(MainActivity.this);
			pdia.setCanceledOnTouchOutside(false);
	        pdia.setMessage("Loading...");
	        pdia.show(); 
		}
		
		
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);    	    	
			
			pdia.dismiss();
			if(result.equals("true"))
			{
				MainActivity.user=e1.getText().toString();
				startActivity(new Intent(MainActivity.this,Home.class));	
			}
			else 
			{
				Toast.makeText(getApplicationContext(), "Invalid Username or Password",1).show();
			}
			}
			
			
			
		}



	}
	




