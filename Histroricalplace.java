package com.example.maliba;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;








import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;


	
public class Histroricalplace extends Activity {



		ArrayList<String> items = new ArrayList<String>();
		ArrayList<String> filterData = new ArrayList<String>();
		ArrayAdapter<String> ad;
		ListView l12;
		EditText t1;
		public static String name="";
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			
			
			setContentView(R.layout.historicalplaces);
			
			l12 = (ListView) findViewById(R.id.listView1);
			t1=(EditText)findViewById(R.id.editText1);
			
			new getimages().execute();
			t1.addTextChangedListener(new TextWatcher() {

				   @Override
				   public void afterTextChanged(Editable s) {
					   
					   filter(s.toString());
					   
				   }

				   @Override    
				   public void beforeTextChanged(CharSequence s, int start,
				     int count, int after) {
				   }

				   @Override    
				   public void onTextChanged(CharSequence s, int start,
				     int before, int count) {
				      if(s.length() != 0)
				    	  filter(s.toString());
				   }
				  });

		l12.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
						long arg3) {
					// TODO Auto-generated method stub
					String a[]=items.get(arg2).split("-");
				name=a[0];
				startActivity(new Intent(Histroricalplace.this,historicalplaceclick.class));
				}
				});	
		}
		public void filter(String constraint) {
			constraint=constraint.toLowerCase();
			   filterData.clear();
			   if (constraint.length() == 0) {
			       filterData.addAll(items);
			   }else{
			       for (int i=0;i<items.size();i++){
			    	   if(items.get(i).toLowerCase().contains(constraint) )
			    	   {
			    		   filterData.add(items.get(i));
			    	   }
			    	   
			        }
			   }
			   ad=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,filterData);
				l12.setAdapter(ad);
			   }
		class getimages extends AsyncTask<Void, Void, String>
		{
			private ProgressDialog pdia;
			
			@Override
			protected String doInBackground(Void... params) {
				// TODO Auto-generated method stub
				
				try
				{
					SoapObject request = new SoapObject(MainActivity.NAMESPACE, "gethistoricalplaces");
			     
			      
			       
			       request.addProperty("Images","1");
			     
			      
			            	        
			        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			        envelope.dotNet=true;
			        envelope.setOutputSoapObject(request);
			        
			        HttpTransportSE androidHttpTransport = new HttpTransportSE(MainActivity.URL);
			        
			        androidHttpTransport.call("http://tempuri.org/gethistoricalplaces", envelope);
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
				pdia = new ProgressDialog(Histroricalplace.this);
				pdia.setCanceledOnTouchOutside(false);
		        pdia.setMessage("Loading...");
		        pdia.show(); 
			}
			
			
			@Override
			protected void onPostExecute(String result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);    	    	
				
				pdia.dismiss();
				
				String[] a1=result.split("~");
				items.clear();
				for(int i =0;i<a1.length;i++)
				{
					String[] a2=a1[i].split("`");
			
					Item item1= new Item();
					item1.setId(a2[0]);
					item1.setName(a2[1]);
					item1.setState(a2[2]);
					item1.setCity(a2[3]);
					item1.setType(a2[6]);
					//item1.setType(a2[6]);
					//item1.setdate(a2[3]);
					//item1.setLink(MainActivity.imgaddress+a2[2]);
				//	item1.setDesc(a2[7]);
					items.add(a2[1]+"-"+a2[3]+"-"+a2[6]);
				}
			
			ad=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,items);
				l12.setAdapter(ad);
				}
				
				
				
			}

		class getimagesdesc extends AsyncTask<Void, Void, String>
		{
			private ProgressDialog pdia;
			
			@Override
			protected String doInBackground(Void... params) {
				// TODO Auto-generated method stub
				
				try
				{
					SoapObject request = new SoapObject(MainActivity.NAMESPACE, "	gethistoricalplaceclick");
			     
			      
			       
			       request.addProperty("hname",name);
			     
			      
			            	        
			        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			        envelope.dotNet=true;
			        envelope.setOutputSoapObject(request);
			        
			        HttpTransportSE androidHttpTransport = new HttpTransportSE(MainActivity.URL);
			        
			        androidHttpTransport.call("http://tempuri.org/gethistoricalplaces", envelope);
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
				pdia = new ProgressDialog(Histroricalplace.this);
				pdia.setCanceledOnTouchOutside(false);
		        pdia.setMessage("Loading...");
		        pdia.show(); 
			}
			
			
			@Override
			protected void onPostExecute(String result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);    	    	
				
				pdia.dismiss();
				
				String[] a1=result.split("~");
				items.clear();
				for(int i =0;i<a1.length;i++)
				{
					String[] a2=a1[i].split("`");
			
					Item item1= new Item();
					item1.setId(a2[0]);
					item1.setName(a2[1]);
					item1.setState(a2[2]);
					item1.setCity(a2[3]);
					
					//item1.setType(a2[6]);
					//item1.setdate(a2[3]);
					//item1.setLink(MainActivity.imgaddress+a2[2]);
				//	item1.setDesc(a2[7]);
					items.add(a2[1]+"-"+a2[3]);
				}
			
			ad=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,items);
				l12.setAdapter(ad);
				}
				
				
				
			}


	}


