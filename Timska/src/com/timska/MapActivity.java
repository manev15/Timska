package com.timska;

import java.util.ArrayList;

import timska.MainActivity;
import timska.Singleton;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.timska.R;

import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MapActivity extends FragmentActivity {
	GoogleMap mMap;
	GoogleMap googleMap;

	// action bar
	private ActionBar actionBar;
	private LatLng lokacija1;

	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		
			// Hide the action bar title
			
			 getActionBar().setDisplayHomeAsUpEnabled(true);
			 mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
			 mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
			 mMap.clear();
			 
			 Bundle bundle = getIntent().getExtras();
			 ArrayList<String> value = bundle.getStringArrayList("manev");
			 
			
			 double lat=Singleton.getInstance().lat;
			 double lng=Singleton.getInstance().lng;
			 String grad=Singleton.getInstance().grad;
			 if(lat!=0 && lng!=0)
			 {
				 	setTitle(grad);
			 		mMap.addMarker(new MarkerOptions().title("Center of town").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)).position(new LatLng(lat, lng)));
			 		lokacija1 = new LatLng(lat, lng);
					mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lokacija1, 15));
			 }
			 
			 String ime="";
			 String ace[];
			 String ace1[];
			 for(int i=0;i<value.size();i++)
			 {
				 ime=value.get(i);
				 ace=ime.split(";");
				 ace1=ace[1].split(" ");
					double aaa1 = Double.parseDouble(ace1[0]);
					double aaa2 = Double.parseDouble(ace1[1]);
					mMap.addMarker(new MarkerOptions().title(ace[0]).position(new LatLng(aaa1, aaa2)));
					
				 
			 }
			 

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.map, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		
		Intent intent = new Intent(MapActivity.this,MainActivity.class);
	
			startActivity(intent);
			finish();
		int id = item.getItemId();
		
		
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
