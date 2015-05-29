package com.gps_cord.routes.database;

import android.database.sqlite.SQLiteDatabase;

public class Activities {
	public static final String TABLE_ACTIVITIES = "activities";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_ACTIVITY_TYPE = "activity_type";
	public static final String COLUMN_ACTIVITY_DISTANCE = "distance";
	public static final String COLUMN_ACTIVITY_TIME_START = "time_start";
	public static final String COLUMN_ACTIVITY_TIME_STOP = "time_stop";
	public static final String COLUMN_AVG_SPEED = "avg_speed";
	public static final String COLUMN_MAX_SPEED = "max_speed";
	public static final String COLUMN_MAX_ALTITUDE = "max_altitude";
	public static final String COLUMN_MIN_ALTITUDE = "min_altitude";
	
	private static final String DATABASE_CREATE_ACTIVITIES =

			"create table " + TABLE_ACTIVITIES + "(" + 
			COLUMN_ID + " integer primary key autoincrement, " +
			COLUMN_ACTIVITY_TYPE + " text not null, " +
			COLUMN_ACTIVITY_DISTANCE + " real not null, " +
			COLUMN_ACTIVITY_TIME_START + " datetime not null, " +
			COLUMN_ACTIVITY_TIME_STOP + " datetime not null, " +
			COLUMN_AVG_SPEED + " real not null, " +
			COLUMN_MAX_SPEED + " real not null, " +
			COLUMN_MAX_ALTITUDE + " real not null, " +
			COLUMN_MIN_ALTITUDE + " real not null" + ");";


	public static void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE_ACTIVITIES);
		
	}

	public static void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
		database.execSQL("DROP TABLE IF EXISTS " + TABLE_ACTIVITIES);
		onCreate(database);
		
	}
	
	
	

}
