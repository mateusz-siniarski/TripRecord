package com.gps_cord.routes;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MyActivity {
	private long _id;
	private String type;
	private float distance;
	private long time_start;
	private long time_stop;
	private float avgSpeed;
	private float maxSpeed;
	private float maxAltitude;
	private float minAltitude;
	
	public long get_id()	{
		return _id;
	}
	
	public String getType()	{
		return type;
	}
	
	public float getDistance()	{
		return distance;
	}
	
	
	public void set_id(long _id)	{
		this._id = _id;
	}
	
	public void setType(String type)	{
		this.type = type;
	}
	
	public void setDistance(float distance)	{
		this.distance = distance;
	}
	
	@Override
	public String toString() {
		return type + " - " + getDate(time_start);
	}

	public float getAvgSpeed() {
		return avgSpeed;
	}

	public void setAvgSpeed(float avgSpeed) {
		this.avgSpeed = avgSpeed;
	}

	public float getMaxSpeed() {
		return maxSpeed;
	}

	public void setMaxSpeed(float maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

	public float getMaxAltitude() {
		return maxAltitude;
	}

	public void setMaxAltitude(float maxAltitude) {
		this.maxAltitude = maxAltitude;
	}

	public float getMinAltitude() {
		return minAltitude;
	}

	public void setMinAltitude(float minAltitude) {
		this.minAltitude = minAltitude;
	}

	public long getTime_start() {
		return time_start;
	}

	public void setTime_start(int time_start) {
		this.time_start = time_start;
	}

	public long getTime_stop() {
		return time_stop;
	}

	public void setTime_stop(int time_stop) {
		this.time_stop = time_stop;
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
