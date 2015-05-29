package com.gps_cord.routes;



import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.LatLngBounds.Builder;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.gps_cord.routes.database.Activities;
import com.gps_cord.routes.database.CoordinatesDataSource;


public class DataOfActivity extends ActionBarActivity {
	
	private CoordinatesDataSource datasource;
	private GoogleMap map;

	private List<LatLng> corList;
	
	private long _id;
	private String activityType;
	private float avgSpeed;
	private float maxSpeed;
	private float distance;
	private float minAltitude;
	private float maxAltitude;
	private long timeStart;
	private long timeStop;
	
	private double maxLatitude = 0;
	private double minLatitude = Double.POSITIVE_INFINITY;
	private double maxLongitude = 0;
	private double minLongitude = Double.POSITIVE_INFINITY;
	
	private LatLngBounds.Builder bounds;
	
	View mapView;
	
	SharedPreferences prefs;
	String unitType;
	
	
	@SuppressWarnings("deprecation")
	@SuppressLint("WorldReadableFiles")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_data);
		
		

		SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
		map = mapFragment.getMap();
		mapView = mapFragment.getView();
		
		prefs = getApplicationContext().getSharedPreferences("units_prefs", MODE_WORLD_READABLE);
		unitType = prefs.getString(SettingsActivity.units, "Kilometers");
		
		Bundle extras = getIntent().getExtras();
		_id = extras.getLong(Activities.COLUMN_ID);
		activityType = extras.getString(Activities.COLUMN_ACTIVITY_TYPE);
		avgSpeed = extras.getFloat(Activities.COLUMN_AVG_SPEED);
		maxSpeed = extras.getFloat(Activities.COLUMN_MAX_SPEED);
		distance = extras.getFloat(Activities.COLUMN_ACTIVITY_DISTANCE);
		minAltitude = extras.getFloat(Activities.COLUMN_MIN_ALTITUDE);
		maxAltitude = extras.getFloat(Activities.COLUMN_MAX_ALTITUDE);
		timeStart = extras.getLong(Activities.COLUMN_ACTIVITY_TIME_START);
		timeStop = extras.getLong(Activities.COLUMN_ACTIVITY_TIME_STOP);
		
		setTitle(activityType);
		setIcon(activityType);
		
		String s_date = getDate(timeStart);
		
		long time = timeStop-timeStart;
		
		
		
		
		TextView t_date = (TextView) findViewById(R.id.textView_Date);
		t_date.setText("Date: "+ s_date);
		//t_date.setText(Long.toString(timeStart));
		
		TextView t_distance = (TextView) findViewById(R.id.textView_Distance);
		t_distance.setText("Distance: "+distanceToString(distance));
		
		TextView t_avgSpeed = (TextView) findViewById(R.id.textView_Speed);
		t_avgSpeed.setText("Avg speed: "+speedToString(avgSpeed));
		
		TextView t_maxSpeed = (TextView) findViewById(R.id.textView_MaxSpeed);
		t_maxSpeed.setText("Max speed: "+speedToString(maxSpeed));
		
		TextView t_minAltitude = (TextView) findViewById(R.id.textView_MinAltitude);
		t_minAltitude.setText("Min altitude: "+altToString(minAltitude));
		
		TextView t_maxAltitude = (TextView) findViewById(R.id.textView_MaxAltitude);
		t_maxAltitude.setText("Max altitude: "+altToString(maxAltitude));
		
		TextView t_time = (TextView) findViewById(R.id.textView_time);
		t_time.setText("Time: "+timeFormat(time));
		
		datasource = new CoordinatesDataSource(this);
		datasource.open();
		corList = datasource.getAllCoordinates(_id);
		datasource.close();
		
		
		bounds = new Builder();
		if(!corList.isEmpty())	{
			drawRoute();
			drawStartFinishPoints();
			setBorders();
		}else{
			Toast.makeText(this, "No map data", Toast.LENGTH_SHORT).show();
		}
		
		
	}
	
	
	private void setBorders()	{
		//bounds = new LatLngBounds(new LatLng(minLatitude, minLongitude), new LatLng(maxLatitude, maxLongitude));
		
		//map.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(corList.get(corList.size()-1), 13, 0, 0)));
		//map.moveCamera(CameraUpdateFactory.newLatLngZoom(bounds.build().getCenter(), 10));
		try {
			
			if (mapView.getViewTreeObserver().isAlive()) {
			    mapView.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

			        public void onGlobalLayout() {
			            mapView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
			            map.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds.build(), 100));
			            //map.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds.build(), 100), 1500, null);         
			        }
			    });
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//Toast.makeText(this, "not work :(", Toast.LENGTH_LONG).show();
		}
	}

	private void setIcon(String activityTitle) {
		if(activityTitle.equals("Drive"))	{
			getSupportActionBar().setIcon(R.drawable.car);
		}
		else if(activityTitle.equals("Go"))	{
			getSupportActionBar().setIcon(R.drawable.person);
		}
			
	}
	
	private String distanceToString(float distance)	{
		if(unitType.equals("Kilometers"))	{
			double dist = Math.round(distance*100/1000)/100.00;
			return dist + " km";
		}
		else if(unitType.equals("Miles"))	{
			double dist = Math.round(distance*100/1609.344)/100.00;
			return dist + " mi"; 
		}
		else	
			return distance + " m";
	}
	
	private String speedToString(float speed)	{
		if(unitType.equals("Kilometers"))	{
			double spd = (int) Math.round(speed*3.6*100)/100.00;
			return spd + " km/h";
		}
		else if(unitType.equals("Miles"))	{
			double spd = (int) Math.round(speed*2.237*100)/100.00;
			return spd + " mph"; 
		}
		else	
			return speed + " m/s";
	}
	
	private String altToString(float alt)	{
		if(unitType.equals("Kilometers"))	{
			int al = (int) Math.round(alt);
			return al + " m";
		}
		else if(unitType.equals("Miles"))	{
			int al = (int) Math.round(alt*3.28);
			return al + " ft"; 
		}
		else	
			return alt + " m";
	}
	
	private String timeFormat(long tm)	{
		int hour = 0;
		int minute = 0;
		int second = 0;
		
		String s_hour;
		String s_minute;
		String s_second;
		
		while(tm >= 3600)	{
			hour++;
			tm-=3600;
		}
		while(tm >= 60)	{
			minute++;
			tm-=60;
		}
		while(tm >= 1)	{
			second++;
			tm--;
		}
		
		if(hour < 10)
			s_hour = "0"+hour;
		else
			s_hour = ""+hour;
		
		if(minute < 10)
			s_minute = "0"+minute;
		else
			s_minute = ""+minute;
		
		if(second < 10)
			s_second = "0"+second;
		else
			s_second = ""+second;
		
		return s_hour+":"+s_minute+":"+s_second;
	}
	
	private void drawRoute() {
		
		PolylineOptions rectLine = new PolylineOptions().width(10).color(Color.RED);
		
		
		for(int i=0;i<corList.size();i++)	{
			rectLine.add(corList.get(i));
			bounds.include(corList.get(i));
			
			/*
			setMaxLat(corList.get(i).latitude);
			setMinLat(corList.get(i).latitude);
			setMaxLng(corList.get(i).longitude);
			setMinLng(corList.get(i).longitude);
			*/
			
		}
		map.addPolyline(rectLine);
		
		
	}
	
	private void setMaxLat(double latitude)	{
		if(latitude > maxLatitude)	{
			maxLatitude = latitude;
		}
	}
	private void setMinLat(double latitude)	{
		if(latitude < minLatitude)	{
			minLatitude = latitude;
		}
	}
	private void setMaxLng(double longitude)	{
		if(longitude > maxLongitude)	{
			maxLongitude = longitude;
		}
	}
	private void setMinLng(double longitude)	{
		if(longitude < minLongitude)	{
			minLongitude = longitude;
		}
	}

	public void drawStartFinishPoints()	{
		
		map.addMarker(new MarkerOptions()
						.position(corList.get(0))
						.title("Start")
						.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
		map.addMarker(new MarkerOptions()
						.position(corList.get(corList.size()-1))
						.title("Finish"));
		
	}
	
	public  String getDate(long timestamp) {
        try{
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(timestamp * 1000);
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
            Date currenTimeZone = (Date) calendar.getTime();
            return sdf.format(currenTimeZone);
        }catch (Exception e) {
        }
        return "";
    }
	
	


}
