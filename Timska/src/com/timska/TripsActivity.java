package com.timska;

import timska.MainActivity;
import timska.Singleton;
import android.app.ActionBar;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		// Create DAO object
		dao = new TodoDAO(this);
		Singleton.getInstance().br++;
		// Set the list adapter and get TODOs list via DAO
		setListAdapter(new ListAdapter(this, dao.getTodos()));
		
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
		menu.add(0, SAVE_MENU_ITEM, 0, "Car");
		menu.add(0, BACK_MENU_ITEM, 0, "Micar");
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
