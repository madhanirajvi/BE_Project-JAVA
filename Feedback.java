package com.example.maliba;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



public class Feedback extends Activity {
		
		EditText e1;
		Button b1;
		
	
		public static String NAMESPACE = "http://tempuri.org/";
		public static String  URL = "http://192.168.1.34/TourismHelpDesk/WebService.asmx"; 
		
		public static String SOAP_ACTION = "http://tempuri.org/insertfeedback";
		String METHOD_NAME = "insertfeedback"; 

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
		setContentView(R.layout.feedback);
			
			b1=(Button)findViewById(R.id.button1);
			e1=(EditText)findViewById(R.id.editText1);
			
			
			
			
b1.setOnClickListener(new View.OnClickListener() {
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		new DoFeedback().execute();
		
		
		
			
	}
});
			
			
			
		}
		class DoFeedback extends AsyncTask<Void, Void, String>
		{
			private ProgressDialog pdia;
			
			@Override
			protected String doInBackground(Void... params) {
				// TODO Auto-generated method stub
				
				try
				{
					SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
			     
			      
			       
			       request.addProperty("data", e1.getText().toString());
			       SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
			       SimpleDateFormat df1 = new SimpleDateFormat("HH:mm:ss");
			       String formattedDate = df.format(Calendar.getInstance().getTime());
			       request.addProperty("date", formattedDate);
			       request.addProperty("time",df1.format(Calendar.getInstance().getTime()) );
			       request.addProperty("posted_by", MainActivity.user);
			       				     
       	        
					  
			            	        
			        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			        envelope.dotNet=true;
			        envelope.setOutputSoapObject(request);
			        
			        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
			        
			        androidHttpTransport.call(SOAP_ACTION, envelope);
			        SoapPrimitive resultstr = (SoapPrimitive)envelope.getResponse();
			        
			        Log.d("message","MEssage : "+resultstr.toString());
			        return resultstr.toString();
			        
//			        Toast.makeText(Reg.this, resultstr.toString(), Toast.LENGTH_SHORT).show();
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
				pdia = new ProgressDialog(Feedback.this);
				pdia.setCanceledOnTouchOutside(false);
		        pdia.setMessage("Loading...");
		        pdia.show(); 
			}
			
			
			@Override
			protected void onPostExecute(String result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);   
				
				finish();
				pdia.dismiss();
				Toast.makeText(Feedback.this, "FeedBack Added Successfully", 1).show();
				
			}



		}
		}

	
