package com.timska;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import timska.MainActivity;
import timska.Singleton;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.timska.dao.TodoDAO;

public class AddTodoActivity extends Activity implements OnClickListener {

	// GUI components
	private EditText todoText;		// Text field
	private Button addNewButton;	// Add new button

	// Back button
	
	// DAO
	private TodoDAO dao;
	private DatePicker data;
	private ImageButton kopce;
	
	private EditText fromDateEtxt;
//	private Button addNewButton;
	private Button addTrip;
	
	
	private DatePickerDialog fromDatePickerDialog;
	private EditText city;
	private boolean flag = false;
	private boolean flag1 = false;
	
	private TextView prc;
	
	private SimpleDateFormat dateFormatter;
	private Button backButton;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_todo);
	
		// Create DAO object
		dao = new TodoDAO(this);
		
	//	todoText 		= (EditText)findViewById(R.id.newTodoText);
		//addNewButton 	= (Button)findViewById(R.id.addNewTodoButton);
	
	backButton		= (Button)findViewById(R.id.menuGoBackButton);
	dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
		
		findViewsById();
		
		setDateTimeField();
		
	//	addNewButton.setOnClickListener(this);

		
	//	data = (DatePicker)findViewById(R.id.datePicker1);

    
	
	city.addTextChangedListener(watc);
	addTrip.setOnClickListener(this);
	backButton.setOnClickListener(this);
//	fromDateEtxt.setOnClickListener(this);

		
	}

	@Override
	public void onClick(View v) {
		// If add button was clicked
		if (addTrip.isPressed()) {
			// Get entered text
			
			 String todoTextValue = fromDateEtxt.getText().toString()+" "+city.getText().toString();
			 String ace=city.getText().toString();
			 fromDateEtxt.setHint("Choose your date");
				city.setHint("City Country");

				// String todoTextValue = tripdate+" "+todoText.getText().toString();
			//		todoText.setText("");
				// Add text to the database
			
				dao.createTodo(todoTextValue);
				
				// Display success information
				Toast.makeText(getApplicationContext(), "New Trip added!", Toast.LENGTH_LONG).show();
				city.setText("");
				fromDateEtxt.setText("");
				fromDateEtxt.setInputType(InputType.TYPE_NULL);
				fromDateEtxt.requestFocus();
			//	Toast.makeText(getApplicationContext(), todoTextValue.toString(), Toast.LENGTH_LONG).show();
				
				showNotification(ace);
			
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
	
 final TextWatcher watc = new TextWatcher() {
	
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
		StringBuilder sb = new StringBuilder();
		// TODO Auto-generated method stub
		if(s.toString().length()>0)
		{
			String [] niza =s.toString().split(" ");
			//sb.append(niza[0]);
			//city.setError(" broj:"+niza.length);
			if(niza.length<2)
			{
				city.setError("Type City/ /Country");
				addTrip.setEnabled(false);
				if(niza[0].contains("0")|| niza[0].contains("1")|| niza[0].contains("2")|| niza[0].contains("3")|| niza[0].contains("4")|| niza[0].contains("5")|| niza[0].contains("6")|| niza[0].contains("7")|| niza[0].contains("8")|| niza[0].contains("9"))
				{
					city.setError("You've entered number in the city.Type only alphabet letters");
					addTrip.setEnabled(false);
				}
			}
			else if(niza.length==2)
			{
			
			if( niza[1].contains("0")|| niza[1].contains("1")|| niza[1].contains("2")|| niza[1].contains("3")|| niza[1].contains("4")|| niza[1].contains("5")|| niza[1].contains("6")|| niza[1].contains("7")|| niza[1].contains("8")|| niza[1].contains("9"))
				{
					city.setError("You've entered number in the country.Type only alphabet letters");
					addTrip.setEnabled(false);
				
				}
				else
				{
					city.setError(null);
					flag=true;
					if(flag1)
					{
						addTrip.setEnabled(true);
						
					}
				}
				
			}
			else
			{
				city.setError("Type City/ /Country");
				
			}
		}
	
		
		
	}
};

	private void findViewsById() {
		fromDateEtxt = (EditText) findViewById(R.id.etxt_fromdate);	
		fromDateEtxt.setInputType(InputType.TYPE_NULL);
		fromDateEtxt.requestFocus();
		addTrip =(Button)findViewById(R.id.addNewTodoButton);
		city =(EditText)findViewById(R.id.newTodoText);
		addTrip.setEnabled(false);
		prc =(TextView)findViewById(R.id.prc);
	
	}

	private void setDateTimeField() {
		
	
		
		Calendar newCalendar = Calendar.getInstance();
		fromDatePickerDialog = new DatePickerDialog(this, new OnDateSetListener() {

	        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
	            Calendar newDate = Calendar.getInstance();
	            Calendar nowDate = Calendar.getInstance(TimeZone.getDefault());
	          
	            newDate.set(year, monthOfYear, dayOfMonth);
	            int year1 = year;
	            int year2 = nowDate.get(Calendar.YEAR);
	            int month1 = monthOfYear;
	            int month2 =nowDate.get(Calendar.MONTH);
	            int day1 = dayOfMonth;
	            int day2 = nowDate.get(Calendar.DAY_OF_MONTH);
	            fromDateEtxt.setError(null);
	            if(year1<year2 || (month1<month2 && year1==year2) || (year1==year2 && month1==month2 && day1<day2))
	            {
	            //	fromDateEtxt.setText("Your date trip is not correct!!Try Again");
	            	//fromDateEtxt.setTextColor(Color.RED);
	            	fromDateEtxt.setError("Not Correct date for trip");
	            	addTrip.setEnabled(false);
	         	
	            	
	            }else{
	            fromDateEtxt.setText(dateFormatter.format(newDate.getTime()));
	            flag1=true;
	            if(flag)
	            addTrip.setEnabled(true);
	            
	            }
	        }

	    },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
		
		fromDateEtxt.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				if(view == fromDateEtxt) {
					fromDatePickerDialog.show();
				} 
			}
		});
	}
	public void showNotification(String ace){

		// define sound URI, the sound to be played when there's a notification

		
		// intent triggered, you can add other intent for other actions
		Intent intent = new Intent(AddTodoActivity.this, TripsActivity.class);
		PendingIntent pIntent = PendingIntent.getActivity(AddTodoActivity.this, 0, intent, 0);
		
		// this is it, we'll build the notification!
		// in the addAction method, if you don't want any icon, just set the first param to 0
		Notification mNotification = new Notification.Builder(this)
			
			.setContentTitle("New Trip Added!")
			.setContentText(ace)
			.setSmallIcon(R.drawable.hoho)
			.setContentIntent(pIntent)
	
//			
//		.addAction(0,"View", pIntent)
//			.addAction(0, "Remind", pIntent)
			
			.build();
		
		NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

		// If you want to hide the notification after it was selected, do the code below
		// myNotification.flags |= Notification.FLAG_AUTO_CANCEL;
		
		notificationManager.notify(0, mNotification);
	}
	
	
	
}
