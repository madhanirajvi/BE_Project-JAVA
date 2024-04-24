package com.example.maliba;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;



import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

public class GalleryView extends Activity {
	
	ArrayList<Item> items = new ArrayList<Item>();
	ListView l12;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		
		setContentView(R.layout.galleryview);
		
		l12 = (ListView) findViewById(R.id.listView1);
		
		new getimages().execute();
		
		l12.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				ScrollView sv = new ScrollView(GalleryView.this);
				LinearLayout l1 = new LinearLayout(GalleryView.this);
				
				sv.addView(l1);
				l1.setOrientation(1);
				ImageView i11 = new ImageView(GalleryView.this);
				
				TextView t11 = new TextView(GalleryView.this);
				
				t11.setTextColor(getResources().getColor(android.R.color.white));
				t11.setText(items.get(arg2).getDesc());
				l1.addView(t11);
				l1.addView(i11);
				
				Picasso.with(getApplicationContext()).load(items.get(arg2).getLink()).into(i11);
			
				
				new AlertDialog.Builder(GalleryView.this)
			    .setTitle(items.get(arg2).getTitle())
			    .setView(sv)
			    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int which) { 
			            // continue with delete
			        }
			     })
			   
			    .setIcon(R.drawable.ic_launcher)
			     .show();
				
			}
		});
	}
	class getimages extends AsyncTask<Void, Void, String>
	{
		private ProgressDialog pdia;
		
		@Override
		protected String doInBackground(Void... params) {
			// TODO Auto-generated method stub
			
			try
			{
				SoapObject request = new SoapObject(MainActivity.NAMESPACE, "gethistoricalimages");
		     
		      
		       
		       request.addProperty("Images","1");
		     
		      
		            	        
		        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		        envelope.dotNet=true;
		        envelope.setOutputSoapObject(request);
		        
		        HttpTransportSE androidHttpTransport = new HttpTransportSE(MainActivity.URL);
		        
		        androidHttpTransport.call("http://tempuri.org/gethistoricalimages", envelope);
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
			pdia = new ProgressDialog(GalleryView.this);
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
				item1.setTitle(a2[1]);
				item1.setdate(a2[3]);
				item1.setLink(MainActivity.imgaddress+a2[2]);
				item1.setDesc(a2[4]);
				items.add(item1);
			}
		
			NewsRowAdapter a12 = new NewsRowAdapter(GalleryView.this,R.layout.row,items);
			l12.setAdapter(a12);
			}
			
			
			
		}



}
