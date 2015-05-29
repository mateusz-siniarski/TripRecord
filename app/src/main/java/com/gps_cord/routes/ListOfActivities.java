package com.gps_cord.routes;

import java.util.List;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.gps_cord.routes.database.Activities;
import com.gps_cord.routes.database.ActivitiesDataSource;
import com.gps_cord.routes.database.CoordinatesDataSource;


@SuppressLint("NewApi")
public class ListOfActivities extends ListActivity {
	
	private ActivitiesDataSource datasource_activities;
	private CoordinatesDataSource datasource_coordinates;
	ArrayAdapter<MyActivity> adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);
		
		getEntireList();
		final int sdkVersion = Build.VERSION.SDK_INT;
		if (sdkVersion >= Build.VERSION_CODES.HONEYCOMB)
		{
			getActionBar().setIcon(R.drawable.list);
		}
		datasource_activities = new ActivitiesDataSource(this);
		datasource_coordinates = new CoordinatesDataSource(this);
		datasource_activities.open();
		
		ListView listView = getListView();
		listView.setOnItemLongClickListener(new OnItemLongClickListener() {
			
			@Override
			public boolean onItemLongClick(AdapterView<?> adapterView, View view,
					int position, long id) {
				deleteActivityDialog(position);
				return true;
			}
		});
		

	}
	
	
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		datasource_activities.close();
	}



	private void deleteActivityDialog(final int position) {
		new AlertDialog.Builder(this)
	        .setTitle("Delete activity?")
	        .setMessage("Are you sure you want to delete this activity?")
	        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int which) { 
	            	adapter = (ArrayAdapter<MyActivity>) getListAdapter();
					MyActivity myact = (MyActivity) adapter.getItem(position);
					datasource_activities.deleteActivity(myact);
			        adapter.remove(myact);
			        datasource_coordinates.open();
			        datasource_coordinates.deleteOnID(myact);
			        datasource_coordinates.close();
			        
	            }
	         })
	        .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int which) { 
	                // Do nothing
	            }
	         })
	        .setIcon(android.R.drawable.ic_dialog_alert)
	         .show();
	}
	
	public void getEntireList()	{
		datasource_activities = new ActivitiesDataSource(this);
		datasource_activities.open();
		
		List<MyActivity> values = datasource_activities.getAllActivities();
		//List<MyActivity> values = datasource.getAllShows();

	    adapter = new ArrayAdapter<MyActivity>(this, android.R.layout.simple_list_item_1, values);
	    setListAdapter(adapter);
	}
	
	
	

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		
		MyActivity myact = (MyActivity) getListAdapter().getItem(position);
		
		Intent intent = new Intent(this, DataOfActivity.class);
		intent.putExtra(Activities.COLUMN_ID, myact.get_id());
		intent.putExtra(Activities.COLUMN_ACTIVITY_TYPE, myact.getType());
		intent.putExtra(Activities.COLUMN_ACTIVITY_DISTANCE, myact.getDistance());
		intent.putExtra(Activities.COLUMN_ACTIVITY_TIME_START, myact.getTime_start());
		intent.putExtra(Activities.COLUMN_ACTIVITY_TIME_STOP, myact.getTime_stop());
		intent.putExtra(Activities.COLUMN_AVG_SPEED, myact.getAvgSpeed());
		intent.putExtra(Activities.COLUMN_MAX_SPEED, myact.getMaxSpeed());
		intent.putExtra(Activities.COLUMN_MAX_ALTITUDE, myact.getMaxAltitude());
		intent.putExtra(Activities.COLUMN_MIN_ALTITUDE, myact.getMinAltitude());
		
		startActivity(intent);
	}
	
	

}
