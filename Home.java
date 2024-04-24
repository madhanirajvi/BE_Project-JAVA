package com.example.maliba;


import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

public class Home extends Activity {
	Button b1,b2,b3,b4,b5,b6;
	SQLiteDatabase db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
				
		
	b1 = (Button) findViewById(R.id.button1);
	b2 = (Button) findViewById(R.id.button2);
	b3 = (Button) findViewById(R.id.button3);
	b4 = (Button) findViewById(R.id.button4);
	b5 = (Button) findViewById(R.id.button5);
	b6=(Button) findViewById(R.id.button6);
	try{
	db=openOrCreateDatabase("maliba.db", SQLiteDatabase.CREATE_IF_NECESSARY, null);
	db.execSQL("create table if not exists tempdata(hid numeric,hname text,stid text,ctid text,longitude text,latitude text,desc text,to_show text)");
	
	}catch(Exception e)
	{
		Toast.makeText(getApplicationContext(), e.toString(), 100).show();
	}
	new getimages().execute();
		b1.setOnClickListener(new View.OnClickListener() {
				
		@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				                              
			
			Intent i=new Intent(Home.this,Feedback.class);
			startActivity(i);
				
			}
		});
		
		b2.setOnClickListener(new View.OnClickListener() {
			
			@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					                              
				
				Intent i=new Intent(Home.this,Gallery.class);
				startActivity(i);
				
					
				}
			});
		
		b3.setOnClickListener(new View.OnClickListener() {
			
			@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
				Intent i=new Intent(Home.this,GalleryView.class);
				startActivity(i);
				                           
				
				//Intent i=new Intent(MainActivity.this,Home.class);
				//startActivity(i);
					
				}
			});
		
b5.setOnClickListener(new View.OnClickListener() {
			
			@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					                              
				Intent i=new Intent(Home.this,Uploadimage.class);
				startActivity(i);
					
					
				}
			});
		
		b4.setOnClickListener(new View.OnClickListener() {
			
			@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					                              
				Intent i=new Intent(Home.this,MainActivity.class);
				startActivity(i);
					
					
				}
			});
		
	b6.setOnClickListener(new View.OnClickListener() {
			
			@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					                              
				
				Intent i=new Intent(Home.this,Histroricalplace.class);
				startActivity(i);
				
					
				}
			});
		

}	class getimages extends AsyncTask<Void, Void, String>
{
	private ProgressDialog pdia;
	
	@Override
	protected String doInBackground(Void... params) {
		// TODO Auto-generated method stub
		
		try
		{
			SoapObject request = new SoapObject(MainActivity.NAMESPACE, "gethistoricalplacestable");
	     
	      
	       
	       request.addProperty("Images","1");
	     
	      
	            	        
	        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
	        envelope.dotNet=true;
	        envelope.setOutputSoapObject(request);
	        
	        HttpTransportSE androidHttpTransport = new HttpTransportSE(MainActivity.URL);
	        
	        androidHttpTransport.call("http://tempuri.org/gethistoricalplacestable", envelope);
	        SoapPrimitive resultstr = (SoapPrimitive)envelope.getResponse();
	        
	        Log.d("message","MEssage : "+resultstr.toString());
	        return resultstr.toString();
	        
//	        Toast.makeText(Reg.this, resultstr.toString(), Toast.LENGTH_SHORT).show();
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
		pdia = new ProgressDialog(Home.this);
		pdia.setCanceledOnTouchOutside(false);
        pdia.setMessage("Loading...");
        pdia.show(); 
	}
	
	
	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);    	    	
		
		pdia.dismiss();
		db.execSQL("delete from tempdata");
		String[] a1=result.split("~");
		try{
		for(int i =0;i<a1.length;i++)
		{
			String[] a2=a1[i].split("`");

			db.execSQL("insert into tempdata values('"+a2[0]+"','"+a2[1]+"','"+a2[2]+"','"+a2[3]+"','"+a2[4]+"','"+a2[5]+"','"+a2[6]+"','"+a2[7]+"')");
			Toast.makeText(getApplicationContext(), "data added", 100).show();
			
		}
		db.close();
		}catch(Exception e)
		{
			Toast.makeText(getApplicationContext(), e.toString(), 100).show();
			
		}
	
		}
		
		
		
	}

	
}
