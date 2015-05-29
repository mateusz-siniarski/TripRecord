package com.gps_cord.routes.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteHelper extends SQLiteOpenHelper{
	private static final String DATABASE_NAME = "activities.db";
	private static final int DATABASE_VERSION = 12;
	
	public SQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	
	
	@Override
	public void onCreate(SQLiteDatabase database) {
		Activities.onCreate(database);
		Coordinates.onCreate(database);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
		Activities.onUpgrade(database, oldVersion, newVersion);
		Coordinates.onUpgrade(database, oldVersion, newVersion);
		
	}

}
