package com.example.maliba;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class historicalplaceclick extends Activity{
	String name=Histroricalplace.name.trim();
	TextView t1,t2;
	LinearLayout l1;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.placeclick);
		l1= (LinearLayout)findViewById(R.id.l1);
		t1=(TextView)findViewById(R.id.textView1);
		t2=(TextView)findViewById(R.id.textView2);
		Toast.makeText(getApplicationContext(), name, 100).show();
			new getimagesdesc().execute();
	}
	
	class getimagesdesc extends AsyncTask<Void, Void, String>
	{
		private ProgressDialog pdia;
		
		@Override
		protected String doInBackground(Void... params) {
			// TODO Auto-generated method stub
			
			try
			{
				SoapObject request = new SoapObject(MainActivity.NAMESPACE, "gethistoricalplaceclick");
		     
		      
		       
		       request.addProperty("hname",name);
		     
		      
		            	        
		        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		        envelope.dotNet=true;
		        envelope.setOutputSoapObject(request);
		        
		        HttpTransportSE androidHttpTransport = new HttpTransportSE(MainActivity.URL);
		        
		        androidHttpTransport.call("http://tempuri.org/gethistoricalplaceclick", envelope);
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
			pdia = new ProgressDialog(historicalplaceclick.this);
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
		
			
			for(int i =0;i<a1.length;i++)
			{
				String[] a2=a1[i].split("`");
		
				t1.setText(a2[1]+"-"+a2[2]);
				t2.setText(a2[3]);
				
				ImageView i1 = new ImageView(historicalplaceclick.this);
				
				
				Picasso.with(getApplicationContext()).load(MainActivity.imgaddress+a2[4]).into(i1);
				l1.addView(i1);
				i1.getLayoutParams().height=500;
				i1.getLayoutParams().width=500;
				
				//item1.setType(a2[6]);
				//item1.setdate(a2[3]);
				//item1.setLink(MainActivity.imgaddress+a2[2]);
			//	item1.setDesc(a2[7]);
				
			}
		
		
			}
			
			
			
		}

}
