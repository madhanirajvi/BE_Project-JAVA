package com.example.maliba;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;
public class backgroundservice  extends Service 
{	
	public static boolean maxspeed=false; 
	public static double speed;
	public static double lntd;
	public static double altd;
	public static double temp;
	SQLiteDatabase db;
	public static final String MyPREFERENCES = "MyPrefs" ;
	SharedPreferences sharedpreferences;
	public String AppMode="";
	TimerTask  timerTask2;
	Timer timer=new Timer();
	Handler handler2=new Handler();
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
		if (sharedpreferences.contains("AppStatus"))
        {               
			AppMode=sharedpreferences.getString("AppStatus", "NO");
        }
		timerTask2=new TimerTask() {
			@Override
			public void run() {
				handler2.post(new Runnable() {
					@Override
					public void run() {
						getloc();
						Location locationA = new Location("Current");

						locationA.setLatitude(altd);
						locationA.setLongitude(lntd);
Log.d("Location",altd+"-"+lntd);
						
						try{
							db=openOrCreateDatabase("maliba.db", SQLiteDatabase.CREATE_IF_NECESSARY, null);
							db.execSQL("create table if not exists tempdata(hid numeric,hname text,stid text,ctid text,longitude text,latitude text,desc text,to_show text)");
							Cursor c=db.rawQuery("select hname,latitude,longitude from tempdata",null);
							while(c.moveToNext())
							{
								if(lntd!=0 && altd!=0)
								{
								Location locationB = new Location("Historicalplace");

								locationB.setLatitude(Double.parseDouble(c.getString(1)));
								locationB.setLongitude(Double.parseDouble(c.getString(2)));

								float distance = locationA.distanceTo(locationB);
								Log.d("distance", String.valueOf(distance));
								if(distance<=5000)
								{
									Intent intent = new Intent(backgroundservice.this, Home.class);
								    PendingIntent contentIntent = PendingIntent.getActivity(backgroundservice.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

								    NotificationCompat.Builder b = new NotificationCompat.Builder(backgroundservice.this);

								    b.setAutoCancel(true)
								     .setDefaults(Notification.DEFAULT_ALL)
								     .setWhen(System.currentTimeMillis())         
								     .setSmallIcon(R.drawable.ic_launcher)
								     .setTicker("Historical Place:"+c.getString(0))            
								     .setContentTitle("Nearer Historical Place")
								     .setContentText(c.getString(0)+" is nearer place.")
								     .setDefaults(Notification.DEFAULT_LIGHTS| Notification.DEFAULT_SOUND)
								     .setContentIntent(contentIntent)
								     .setContentInfo("Info");


								    NotificationManager notificationManager = (NotificationManager) backgroundservice.this.getSystemService(Context.NOTIFICATION_SERVICE);
								    notificationManager.notify(1, b.build());
								}
								}
							}
							
							
							}catch(Exception e)
							{
								//Toast.makeText(getApplicationContext(), e.toString(), 100).show();
							}
						
						
					}
				});				
			}			
		};		
		timer.scheduleAtFixedRate(timerTask2, 1000, 10000);	
		return START_STICKY;
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	public void getloc()
    {	 
    	LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
    	LocationListener locationListener = new LocationListener() 
    	{
            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            @Override
    	    public void onProviderEnabled(String provider) {
            }

    	    @Override
    	    public void onProviderDisabled(String provider) {
    	    }

    	    @Override
    	    public void onLocationChanged(Location location) {
    	    	if(location!=null)
    	    	{
    	    		
	    	    	lntd=location.getLongitude();
	    	    	altd=location.getLatitude();
	    	        speed = location.getSpeed();
    	    	}
    	    }
    	};
    	locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);    	
	}
}