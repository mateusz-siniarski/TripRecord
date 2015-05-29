package com.gps_cord.routes;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.widget.Toast;

import com.gps_cord.routes.database.ActivitiesDataSource;

public class Test extends ActionBarActivity {
	
	private ActivitiesDataSource datasource;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);
		
		datasource = new ActivitiesDataSource(this);

		datasource.open();
		
		
		float pi = (float) 3.14;
		//int two = 2;
		
		//addToTable("Test", pi);
		long _id = addToTable("Trying", pi);

		String type = getTypeFromTable(_id);
		float distance = getDistanceFromTable(_id);
		
		
		
		Toast toast = Toast.makeText(this, _id+" "+type+" "+distance, Toast.LENGTH_SHORT);
		toast.show();
		
		datasource.close();
		
		
		/*Toast toast = new Toast(this);
		toast.setText( type );*/
		
		/*float distance = datasource.getDistance();
		Toast toast = new Toast(this);
		toast.setText(Float.toString( distance ));*/
	}

	
	public long addToTable(String type, float distance)	{
		return datasource.createActivity(type, distance, (long)-1,(long)-1,(float)-1,(float)-1,(float)-1,(float)-1);
		
	}
	
	public String getTypeFromTable(long _id)	{
		MyActivity myact = datasource.getActivity(_id);
		return myact.getType();
	}
	
	public float getDistanceFromTable(long _id)	{
		MyActivity myact = datasource.getActivity(_id);
		return myact.getDistance();
	}
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.test, menu);
		return true;
	}

}
