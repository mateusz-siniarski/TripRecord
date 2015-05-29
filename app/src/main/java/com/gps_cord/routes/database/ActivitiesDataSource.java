package com.gps_cord.routes.database;



import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.gps_cord.routes.MyActivity;

public class ActivitiesDataSource {
	private SQLiteDatabase database;
	private SQLiteHelper dbHelper;
	
	private String[] allActivityColumns = { Activities.COLUMN_ID,
			Activities.COLUMN_ACTIVITY_TYPE,
			Activities.COLUMN_ACTIVITY_DISTANCE,
			Activities.COLUMN_ACTIVITY_TIME_START,
			Activities.COLUMN_ACTIVITY_TIME_STOP,
			Activities.COLUMN_AVG_SPEED,
			Activities.COLUMN_MAX_SPEED,
			Activities.COLUMN_MAX_ALTITUDE,
			Activities.COLUMN_MIN_ALTITUDE
			};
	
	public ActivitiesDataSource(Context context)	{
		dbHelper = new SQLiteHelper(context);
	}
	
	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}
	
	public void close()	{
		dbHelper.close();
	}
	
	public long createActivity(String type, float distance, long time_start, long time_stop, float avg_speed, 
		float max_speed, float max_altitude, float min_altitude) {
		ContentValues values = new ContentValues();
		
		values.put(Activities.COLUMN_ACTIVITY_TYPE, type);
		values.put(Activities.COLUMN_ACTIVITY_DISTANCE, distance);
		values.put(Activities.COLUMN_ACTIVITY_TIME_START, time_start);
		values.put(Activities.COLUMN_ACTIVITY_TIME_STOP, time_stop);
		values.put(Activities.COLUMN_AVG_SPEED, avg_speed);
		values.put(Activities.COLUMN_MAX_SPEED, max_speed);
		values.put(Activities.COLUMN_MAX_ALTITUDE, max_altitude);
		values.put(Activities.COLUMN_MIN_ALTITUDE, min_altitude);
		
		long insertId = database.insert(Activities.TABLE_ACTIVITIES, null, values);
		
		return insertId;
	}
	
	
	public MyActivity getActivity(long _id)	{
		Cursor cursor = database.query(Activities.TABLE_ACTIVITIES,
		        allActivityColumns, Activities.COLUMN_ID + " = " + _id, null,
		        null, null, null);
		    cursor.moveToFirst();
		    MyActivity newActivity = cursorToMyActivity(cursor);
		    cursor.close();
		    return newActivity;
	}
	

	public float getDistance() {
		Cursor cursor = database.query(Activities.TABLE_ACTIVITIES,
				allActivityColumns, 
				null, null, null, null, null);
		cursor.moveToFirst();
		float distance = cursor.getFloat(1);
		cursor.close();
		
		return distance;
	}
	
	public List<MyActivity> getAllActivities() {
		List<MyActivity> activities = new ArrayList<MyActivity>();
		
		Cursor cursor = database.query(Activities.TABLE_ACTIVITIES,
										allActivityColumns, 
										null, null, null, null, Activities.COLUMN_ID + " DESC");
	
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			MyActivity myact = cursorToMyActivity(cursor);
			activities.add(myact);
			cursor.moveToNext();
		}
		// Make sure to close the cursor
		cursor.close();
		
		return activities;
	}
	
	public void deleteActivity(MyActivity myact)	{
		long id = myact.get_id();
		database.delete(Activities.TABLE_ACTIVITIES, Activities.COLUMN_ID + " = " + id, null);
	}
	
	
	private MyActivity cursorToMyActivity(Cursor cursor) {
	    MyActivity myact = new MyActivity();
	    myact.set_id(cursor.getLong(0));
	    myact.setType(cursor.getString(1));
	    myact.setDistance(cursor.getFloat(2));
	    myact.setTime_start(cursor.getInt(3));
	    myact.setTime_stop(cursor.getInt(4));
	    myact.setAvgSpeed(cursor.getFloat(5));
	    myact.setMaxSpeed(cursor.getFloat(6));
	    myact.setMaxAltitude(cursor.getFloat(7));
	    myact.setMinAltitude(cursor.getFloat(8));
	    
	    return myact;
	  }
	
	
	
	
}
