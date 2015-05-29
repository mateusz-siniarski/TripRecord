package com.gps_cord.routes;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

@SuppressLint("NewApi")
public class SettingsActivity extends ActionBarActivity implements OnItemSelectedListener{
	
	SharedPreferences prefs;
	Editor ed;
	
	public static String units = "Units";
	public static String vibDist = "vibDist";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		
		prefs = getApplicationContext().getSharedPreferences("units_prefs", MODE_WORLD_READABLE);
		ed = prefs.edit();
		
		Spinner spinner = (Spinner) findViewById(R.id.spinner_distance_unit);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.distance_units_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        
        spinner.setOnItemSelectedListener(this);
		
        String savedUnit = prefs.getString(units, "Kilometers");
        int position = adapter.getPosition(savedUnit);
        spinner.setSelection(position);
        
        EditText vibText = (EditText) findViewById(R.id.editText_VibDist);
        
        String savedVibDist = prefs.getString(vibDist, "1.0");
        vibText.setText(savedVibDist);
        
        vibText.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				saveSettingToVibDist(s.toString());
			}
		});
		
	}


	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		
		switch ( (String) parent.getItemAtPosition(position) ) {
		case "Kilometers":
			saveSettingToUnits("Kilometers");
			break;
		case "Miles":
			saveSettingToUnits("Miles");
		default:
			break;
		}
		
	}
		
	private void saveSettingToUnits(String unit)	{
		ed.remove(units);
		ed.putString(units, unit);
		ed.apply();
	}
	
	private void saveSettingToVibDist(String unit)	{
		ed.remove(vibDist);
		ed.putString(vibDist, unit);
		ed.apply();
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		if(!prefs.contains(units))	{
			saveSettingToUnits("Kilometers");
		}
		
	}

}
