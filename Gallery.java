
package com.example.maliba;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Gallery extends Activity {
	Button b1,b2;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gallery);
		
		
	
		b1= (Button) findViewById(R.id.button1);
		b2= (Button) findViewById(R.id.button2);
		
	
		
		b1.setOnClickListener(new View.OnClickListener() {
				
		@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				                              
			
				
					
					Intent i=new Intent(Gallery.this,Privateimage.class);
					startActivity(i);
					
					
				
			}
		});
		
		b2.setOnClickListener(new View.OnClickListener() {
			
			@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					                              
				
					
						
						Intent i=new Intent(Gallery.this,Publicimage.class);
						startActivity(i);
						
						
					
				}
			});
	}

}
