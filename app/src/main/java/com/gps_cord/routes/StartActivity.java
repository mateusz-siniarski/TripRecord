package com.gps_cord.routes;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.gps_cord.routes.GPSService.MyLocationListener;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class StartActivity extends ActionBarActivity implements OnItemSelectedListener{
	String tag = "logg";
	String mode = "Drive";
	GoogleMap map;
	
	LocationManager lm;
	LocationListener locationListener;
	
	double lat = 0;
	double lng = 0;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		/*
		Spinner spinner = (Spinner) findViewById(R.id.layers_spinner);
        
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.activity_modes_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        
        spinner.setOnItemSelectedListener(this);
        */
        
        /**
         * Maps is being drawed and position listener starts
         *
		 */
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
		//map = mapFragment.getMap();
        
		lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		locationListener = new MyLocationListener();
		
	}
	
	public void driveActivity(View v) {
		 Intent intent = new Intent(this, GPSActivity.class);
	     intent.putExtra("mode", "Drive");
	     //sends last known position data to next activity
	     intent.putExtra("lat",lat);
	     intent.putExtra("lng", lng);
	        
	     startActivity(intent);
		
		
	}

	public void goActivity(View v) {
		Intent intent = new Intent(this, GPSActivity.class);
	     intent.putExtra("mode", "Go");
	     //sends last known position data to next activity
	     intent.putExtra("lat",lat);
	     intent.putExtra("lng", lng);
	        
	     startActivity(intent);
	
	
	}
	

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		lm.removeUpdates(locationListener);
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10*1000, 0, locationListener);
	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.list, menu);
		getMenuInflater().inflate(R.menu.settings, menu);
		return true;
	}
	
	public void startActivity(View v)	{
		/*RadioGroup radioActivity = (RadioGroup) findViewById(R.id.radioActivity);
		int selectedRadio = radioActivity.getCheckedRadioButtonId();
		
		Log.i(tag,Integer.toString(selectedRadio));
		
		RadioButton radioActivityButton = (RadioButton) findViewById(selectedRadio);
        String radioButton = (String) radioActivityButton.getText();
        */
       
		
		
	}
	
	public void showList(MenuItem menuitem) 	{
		Intent listIntent = new Intent(this, ListOfActivities.class);
		startActivity(listIntent);
	}
	public void showSettings(MenuItem menuitem) 	{
		Intent settingsIntent = new Intent(this, SettingsActivity.class);
		startActivity(settingsIntent);
	}
	
	
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		switch ( (String) parent.getItemAtPosition(position) ) {
		case "Drive":
			mode = "Drive";
			break;
		case "Go":
			mode = "Go";
		default:
			break;
    }
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		mode = "Drive";
		
	}
	/**
	 * Listener for location data, updates every 10 seconds
	 * it shows current location with blue my location marker
	 * @author Mateusz
	 *
	 */
	public class MyLocationListener implements LocationListener {

    	@Override
    	public void onLocationChanged(Location loc) {
    		lat = loc.getLatitude();
    		lng = loc.getLongitude();
    		
    		LatLng ltlg = new LatLng(lat, lng);

            map.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(ltlg, 15, 0, 0)));
            map.setMyLocationEnabled(true);

    	}

    	@Override
    	public void onProviderDisabled(String arg0) {
    		// TODO Auto-generated method stub

    	}

    	@Override
    	public void onProviderEnabled(String arg0) {
    		// TODO Auto-generated method stub

    	}

    	@Override
    	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
    		// TODO Auto-generated method stub

    	}

    }


}
