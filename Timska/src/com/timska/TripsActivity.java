package com.timska;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import timska.MainActivity;
import timska.Singleton;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.timska.ListAdapter;
import com.timska.dao.TodoDAO;
import com.timska.data.Todo;



/**
 * Main activity which displays a list of TODOs.
 * 
 * @author itcuties
 *
 */
public class TripsActivity extends ListActivity {
	private static final int OK_MENU_ITEM = Menu.FIRST;
	private static final int SAVE_MENU_ITEM = OK_MENU_ITEM + 1;
	private static final int BACK_MENU_ITEM = SAVE_MENU_ITEM + 1;
	// DAO
	private TodoDAO dao;
	private String lokacija,lokacijaZaGEO;
	private Geocoder geocoder;
	final Context context = this;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		// Create DAO object
		dao = new TodoDAO(this);
		Singleton.getInstance().br++;
		// Set the list adapter and get TODOs list via DAO
		setListAdapter(new ListAdapter(this, dao.getTodos()));
		Date today = Calendar.getInstance().getTime();
		  SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		  String folderName = formatter.format(today);
		//  Log.e("denesna data",folderName);
			 geocoder = new Geocoder(this); 

		  
	int a=getListAdapter().getCount();
	boolean bool=false;
	int i =0;
			while(i!=a)
			{      String[]denesna=folderName.split("-");
				  Log.e("denesna data",denesna[0]);
				  Log.e("denesna data",denesna[1]);
				  Log.e("denesna data",denesna[2]);
			Todo todo1 = (Todo)getListAdapter().getItem(i);
			 String aa=todo1.getText();
			String[]niza=aa.split(" ");
			
			Log.e("niza",niza[0]);
			//String[] godina=niza[2].split(" ");
			String god[]=niza[0].split("-");
			Log.e("god",god[0]);
			Log.e("god",god[1]);
		
			Log.e("god",god[2]);
			
		 lokacija = niza[1]+" "+niza[2];
		  lokacijaZaGEO=niza[1]+" "+niza[2];
			Log.e("lokacija",lokacija);
			
		  
		  if(denesna[0].equals(god[0])&& denesna[1].equals(god[1]) && denesna[2].equals(god[2]) )
		  {   Log.e("Severinaaaaaa", "severinaaaa");
//			  AlertDialog.Builder builder = new AlertDialog.Builder(this);
//			// Add the buttons
//			builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
//				private double lat;
//	       		private double lng;
//			           public void onClick(DialogInterface dialog, int id) {
//			        	   
//			               // User clicked OK button
//			        	   try {
//
//			   		    	List<Address> addresses= geocoder.getFromLocationName(lokacija, 1);
//
//			   		    	if (addresses != null && !addresses.isEmpty()) {
//
//			   		    	 Address address = addresses.get(0);
//			                  lat=address.getLatitude();
//			   		    	 lng=address.getLongitude();
//			   		    	
//			   		    	}
//			   		    	
//			   		    	} 
//			   		    	catch (IOException e)    {
//			   		    	  
//			   		    	}
//			   				  Intent intent = new Intent(TripsActivity.this, MainActivity.class);
//			   				   intent.putExtra("lat",lat);
//			   				   intent.putExtra("lng", lng);
//			   				    startActivity(intent);
//			        		
//			        	   
//			        	   
//			           }
//			       });
//			
//			// Create the AlertDialog
//			AlertDialog dialog = builder.create();
//			  
		  AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					context);
	 
				// set title
				alertDialogBuilder.setTitle("Today's trip");
	 
				// set dialog message
				alertDialogBuilder
					.setMessage(lokacija+"\n \n Show my trip")
					.setCancelable(false)
					.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
						private double lat;
			       		private double lng;
						public void onClick(DialogInterface dialog,int id) {
					   		try {

						    	List<Address> addresses= geocoder.getFromLocationName(lokacijaZaGEO, 1);

						    	if (addresses != null && !addresses.isEmpty()) {

						    	 Address address = addresses.get(0);
				                 lat=address.getLatitude();
						    	 lng=address.getLongitude();
						    	
						    	}
						    	
						    	} 
						    	catch (IOException e)    {
						    	  
						    	}
								  Intent intent = new Intent(TripsActivity.this, MyTrip.class);
								   intent.putExtra("lat",lat);
								   intent.putExtra("lng", lng);
								    startActivity(intent);
							
						}
					  })
					.setNegativeButton("No",new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int id) {
							// if this button is clicked, just close
							// the dialog box and do nothing
							dialog.cancel();
						}
					});
	 
					// create alert dialog
					AlertDialog alertDialog = alertDialogBuilder.create();
	 
					// show it
					alertDialog.show();
					bool=true;
				}
		
			
				
		  if(bool)
			{
				
				i=a;
			}
		  else
		  i++;
			}
		if(bool==false)
		{
				  AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
							context);
			 
						// set title
						alertDialogBuilder.setTitle("No trips today");
			 
						// set dialog message
						alertDialogBuilder
							.setMessage("Create new trip?")
							.setCancelable(false)
							.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,int id) {
									Intent intent = new Intent(TripsActivity.this, AddTodoActivity.class); 
									startActivity(intent);	        		
									
									
								}
							  })
							.setNegativeButton("No",new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,int id) {
									// if this button is clicked, just close
									// the dialog box and do nothing
									dialog.cancel();
								}
							});
			 
							// create alert dialog
							AlertDialog alertDialog = alertDialogBuilder.create();
			 
							// show it
							alertDialog.show();
				  
				  
			  
		}
		
		
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO item that was clicked
		Todo todo = (Todo)getListAdapter().getItem(position);
		
		// Delete TODO object from the database
		dao.deleteTodo(todo.getId());
		
		// Set the list adapter and get TODOs list via DAO
		setListAdapter(new ListAdapter(this, dao.getTodos()));
		
		// Display success information
		Toast.makeText(getApplicationContext(), "Deleted!", Toast.LENGTH_LONG).show();
		
	}
	
	/* ************************************************************* *
	 * Menu service methods
	 * ************************************************************* */ 
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		
		menu.add(0, OK_MENU_ITEM, 0, "Add Trip");
	
		return super.onCreateOptionsMenu(menu);
	
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Since we have only ONE option this code is not complicated :)
		
		// Create an intent
		Intent intent = new Intent(this, MainActivity.class);
		// Start activity
		startActivity(intent);
		// Finish this activity
		this.finish();
		switch (item.getItemId()) {
		case OK_MENU_ITEM:
			Intent intent1 = new Intent(this, AddTodoActivity.class);
			// Start activity
			startActivity(intent1);
			break;
		case SAVE_MENU_ITEM:
			
			break;
		case BACK_MENU_ITEM:
			
			break;
		}
		// Close the database
		dao.close();
		
		return super.onOptionsItemSelected(item);
	}

}
