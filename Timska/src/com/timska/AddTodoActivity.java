package com.timska;
import timska.MainActivity;
import timska.Singleton;

import com.timska.dao.TodoDAO;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

public class AddTodoActivity extends Activity implements OnClickListener {

	// GUI components
	private EditText todoText;		// Text field
	private Button addNewButton;	// Add new button
	private Button backButton;		// Back button
	
	// DAO
	private TodoDAO dao;
	private DatePicker data;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_todo);
	
		// Create DAO object
		dao = new TodoDAO(this);
		
		todoText 		= (EditText)findViewById(R.id.newTodoText);
		addNewButton 	= (Button)findViewById(R.id.addNewTodoButton);
		backButton		= (Button)findViewById(R.id.menuGoBackButton);
		
		addNewButton.setOnClickListener(this);
		backButton.setOnClickListener(this);
		data = (DatePicker)findViewById(R.id.datePicker1);
	}

	@Override
	public void onClick(View v) {
		// If add button was clicked
		if (addNewButton.isPressed()) {
			// Get entered text
			
			
			int den = data.getDayOfMonth();
			int mesec = data.getMonth() + 1;
			 int godina = data.getYear();
			
			 String day=den+"";
			 String mounth=mesec+"";
			 String year=godina+"";
			 String golemina=day.length()+"";
			 Log.d("goleminaaaa", golemina);
			 if(day.length()==1)
			 {
				 day="0"+day;
				 
			 }
			 if(mounth.length()==1)
			 {
				 mounth="0"+mounth;
				 
			 }
			 
			 Log.e("den", day);
			 Log.e("mesec", mounth);
			 Log.e("godina", year);
			 String tripdate=day+"-"+mounth+"-"+year;
			 Log.e("cela", tripdate);
			 
			 String todoTextValue = tripdate+" "+todoText.getText().toString();
				todoText.setText("");
			// Add text to the database
			dao.createTodo(todoTextValue);
			
			// Display success information
			Toast.makeText(getApplicationContext(), "New Trip added!", Toast.LENGTH_LONG).show();
			
		} else if (backButton.isPressed()) {
			// When back button is pressed
			// Create an intent
			Intent intent = new Intent(this, MainActivity.class);
			// Start activity
			Singleton.getInstance().br++;
			startActivity(intent);
			// Finish this activity
			this.finish();
			
			// Close the database
			dao.close();
		}
		
	}
	
}
