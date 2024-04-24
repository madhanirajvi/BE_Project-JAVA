package com.example.maliba;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

public class Uploadimage extends Activity{
	Button b1,b2;
	ImageView e1;
	final int SELECT_PHOTO = 1;
	Spinner s1;
	EditText e2;
	
	public static String NAMESPACE = "http://tempuri.org/";
	public static String  URL = "http://192.168.1.34/TourismHelpDesk/WebService.asmx"; 
	
	public static String SOAP_ACTION = "http://tempuri.org/uploadimage";
	String METHOD_NAME = "uploadimage"; 
	
	String path="";
String name="";
	
	
	String urlServer = "http://192.168.1.34/TourismHelpDesk/Fileupload.aspx";
	 String lineEnd = "\r\n";
	 String twoHyphens = "--";
	 String boundary =  "*****";
	 String FileName ; 
	 int bytesRead, bytesAvailable, bufferSize;
	 byte[] buffer;
	 int maxBufferSize = 1*1024*1024;
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.uploadimage);
		
		b1=(Button)findViewById(R.id.button1);
		b2=(Button)findViewById(R.id.button2);
		s1=(Spinner)findViewById(R.id.spinner1);
		e1=(ImageView)findViewById(R.id.imageView1);
		e2=(EditText)findViewById(R.id.editText1);
		
		b2.setOnClickListener(new View.OnClickListener() {
			


			@Override
			public void onClick(View arg0) {
		
				
		new DoFeedback().execute();
				
			        
			}
				 
			
		});
		
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
						 path= getRealPathFromURI(Uploadimage.this, imageUri);
						 File f1 = new File(path);
						 name=f1.getName();
						 
						e1.setImageBitmap(selectedImage);
						new douploaddata().execute();
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}

	            }
	        }
	   
	   
	 
	
	
	 
}

     public String getRealPathFromURI(Context context, Uri contentUri) {
     	  Cursor cursor = null;
     	  try { 
     	    String[] proj = { MediaStore.Images.Media.DATA };
     	    cursor = context.getContentResolver().query(contentUri,  proj, null, null, null);
     	    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
     	    cursor.moveToFirst();
     	    return cursor.getString(column_index);
     	  } finally {
     	    if (cursor != null) {
     	      cursor.close();
     	    }
     	  }
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
	     
	      
	       
	       request.addProperty("name",name);
	       request.addProperty("text", e2.getText().toString());
	       SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
	       SimpleDateFormat df1 = new SimpleDateFormat("HH:mm:ss");
	       String formattedDate = df.format(Calendar.getInstance().getTime());
	       request.addProperty("date", formattedDate);
	       request.addProperty("time",df1.format(Calendar.getInstance().getTime()) );
	       request.addProperty("visibility", s1.getSelectedItem().toString());
	     		     
	        
			  
	            	        
	        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
	        envelope.dotNet=true;
	        envelope.setOutputSoapObject(request);
	        
	        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
	        
	        androidHttpTransport.call(SOAP_ACTION, envelope);
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
		pdia = new ProgressDialog(Uploadimage.this);
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
		Toast.makeText(Uploadimage.this, "Image Added Successfully", 1).show();
		
	}


	


} 


class douploaddata extends AsyncTask<Void, Void, String>
{
	private ProgressDialog pdia;
	
	protected String doInBackground(Void... params) 
	{
		try
		{	
			       FileInputStream fileInputStream = new FileInputStream(path);
			    
			       URL url = new URL(urlServer);
			       HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			    
			       // Allow Inputs &amp; Outputs.
			       connection.setDoInput(true);
			       connection.setDoOutput(true);
			       connection.setUseCaches(false);
			    
			       // Set HTTP method to POST.
			       connection.setRequestMethod("POST");
			    
			       connection.setRequestProperty("Connection", "Keep-Alive");
			       connection.setRequestProperty("Content-Type", "multipart/form-data;boundary="+boundary);
			    
			       DataOutputStream outputStream = new DataOutputStream( connection.getOutputStream() );
			       outputStream.writeBytes(twoHyphens + boundary + lineEnd);
			       outputStream.writeBytes("Content-Disposition: form-data; name=\"uploadedfile\";filename=\"" + path +"\"" + lineEnd);
			       outputStream.writeBytes(lineEnd);
			    
			       bytesAvailable = fileInputStream.available();
			       bufferSize = Math.min(bytesAvailable, maxBufferSize);
			       buffer = new byte[bufferSize];
			    
			       // Read file
			       bytesRead = fileInputStream.read(buffer, 0, bufferSize);
			    
			       while (bytesRead > 0)
			       {
			           outputStream.write(buffer, 0, bufferSize);
			           bytesAvailable = fileInputStream.available();
			           bufferSize = Math.min(bytesAvailable, maxBufferSize);
			           bytesRead = fileInputStream.read(buffer, 0, bufferSize);
			           
			       }
			    
			       outputStream.writeBytes(lineEnd);
			       outputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
			    
			       // Responses from the server (code and message)
			       int serverResponseCode = connection.getResponseCode();
			       String serverResponseMessage = connection.getResponseMessage();
			    
			       fileInputStream.close();
			       outputStream.flush();
			       outputStream.close();
			       return serverResponseMessage;
			       
			   }
			   catch (Exception ex)
			   {
				   return(ex.getMessage());
			       //Log.d("error", ex.getMessage());
			   }
			 
	   
		
	}
	

	protected void onPreExecute() 
	{
		super.onPreExecute();
		pdia = new ProgressDialog(Uploadimage.this);
		pdia.setCanceledOnTouchOutside(false);
        pdia.setMessage("Uploading in...");
        pdia.show(); 
	}
	
	protected void onPostExecute(String result) 
	{	
		super.onPostExecute(result);
		pdia.dismiss();
		//Toast.makeText(F_uploadhelper.this, result, 1).show();
	
		
	}	
}

}

