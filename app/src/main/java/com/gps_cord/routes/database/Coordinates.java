package com.gps_cord.routes.database;

import android.database.sqlite.SQLiteDatabase;

public class Coordinates {
	
	public static final String TABLE_COORDINATES = "coordinates";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_COUNTER = "counter";
	public static final String COLUMN_LATITUDE = "latitude";
	public static final String COLUMN_LONGITUDE = "longitude";
	
	/*litt svensk løsning - dansk i følge Karzan
	 * DET VIKTIGSTE ER AT DEN FUNGERER!!!!!!!
	 * */
	private static final String DATABASE_CREATE_COORDINATES =

			"create table " + TABLE_COORDINATES + "(" + 
			COLUMN_ID + " integer not null, " +
			COLUMN_COUNTER + " integer primary key autoincrement, " +
			COLUMN_LATITUDE + " real not null, " +
			COLUMN_LONGITUDE + " real not null " + ");";
	
	public static void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE_COORDINATES);
		
	}

	public static void onCreate(SQLiteDatabase database, int oldVersion,
			int newVersion) {
		database.execSQL(DATABASE_CREATE_COORDINATES);
		
	}

	public static void onUpgrade(SQLiteDatabase database, int oldVersion,
			int newVersion) {
		database.execSQL("DROP TABLE IF EXISTS " + TABLE_COORDINATES);
		onCreate(database);
		
	}

}
