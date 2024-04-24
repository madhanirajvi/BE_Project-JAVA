package com.example.maliba;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import com.example.maliba.GalleryView.getimages;
import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class Publicimage extends Activity    {ArrayList<Item> items = new ArrayList<Item>();
ListView l12;

@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	
	
	setContentView(R.layout.publicimage);
	
	l12 = (ListView) findViewById(R.id.listView1);
	
	
	new getimages().execute();
	
	l12.setOnItemClickListener(new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			ScrollView sv = new ScrollView(Publicimage.this);
			LinearLayout l1 = new LinearLayout(Publicimage.this);
			
			sv.addView(l1);
			l1.setOrientation(1);
			ImageView i11 = new ImageView(Publicimage.this);
			
			TextView t11 = new TextView(Publicimage.this);
			
			t11.setTextColor(getResources().getColor(android.R.color.white));
			t11.setText(items.get(arg2).getDesc());
			l1.addView(t11);
			l1.addView(i11);
			
			
			Picasso.with(getApplicationContext()).load(items.get(arg2).getLink()).into(i11);
			
			new AlertDialog.Builder(Publicimage.this)
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
			SoapObject request = new SoapObject(MainActivity.NAMESPACE, "getpublicimages");
	     
	      
	       
	       request.addProperty("Images","1");
	     
	      
	            	        
	        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
	        envelope.dotNet=true;
	        envelope.setOutputSoapObject(request);
	        
	        HttpTransportSE androidHttpTransport = new HttpTransportSE(MainActivity.URL);
	        
	        androidHttpTransport.call("http://tempuri.org/getpublicimages", envelope);
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
		pdia = new ProgressDialog(Publicimage.this);
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
			item1.setdate(a2[2]);
			item1.setLink(MainActivity.imgaddress+a2[1]);
			item1.setDesc(a2[2]);
			items.add(item1);
		}
	
		NewsRowAdapter a12 = new NewsRowAdapter(Publicimage.this,R.layout.row,items);
		l12.setAdapter(a12);
		}
		
		
		
	}



	/*Button b1;
	ImageView e1;
	final int SELECT_PHOTO = 1;
	RadioButton r1,r2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.publicimage);
		
		b1=(Button)findViewById(R.id.button1);
		
		e1=(ImageView)findViewById(R.id.imageView1);
		

b1.setOnClickListener(new View.OnClickListener() {
	


	@Override
	public void onClick(View arg0) {
		
		Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
		photoPickerIntent.setType("image/*");
		startActivityForResult(photoPickerIntent, SELECT_PHOTO);
	        
	}
		 
	
});
		
	}
	 @Override
	 protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) { 
	        super.onActivityResult(requestCode, resultCode, imageReturnedIntent); 

	        switch(requestCode) { 
	        case SELECT_PHOTO:
	            if(resultCode == RESULT_OK){
					try {
						final Uri imageUri = imageReturnedIntent.getData();
						final InputStream imageStream = getContentResolver().openInputStream(imageUri);
						final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
						e1.setImageBitmap(selectedImage);
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}

	            }
	        }
	   

	   
	 
	
	 }*/
}
