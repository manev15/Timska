package timska;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.timska.FacebokActivity;
import com.timska.PhotoActivity;
import com.timska.R;
import com.timska.TripsActivity;


public class HomeFragment extends Fragment {
	
	private TextView user;
	private Button kopce;
	private Button fblogin;
	final Context cont=getActivity();
	Singleton lol;
	public HomeFragment(){}
	public int ace;
	private String Item="",ae;
	private ImageButton post1;
	private ImageButton login;
	private ImageButton find;
	private Button upcoming;
	private ImageButton popular;
	private ImageButton near;
	private ImageButton photo;
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
         user=(TextView)rootView.findViewById(R.id.textView2);
         fblogin=(Button)rootView.findViewById(R.id.fblogin);
         upcoming= (Button)rootView.findViewById(R.id.button2);
         post1=(ImageButton)rootView.findViewById(R.id.postb);
         login=(ImageButton)rootView.findViewById(R.id.imageButton2);
     //    find=(ImageButton)rootView.findViewById(R.id.imageButton5);
         popular=(ImageButton)rootView.findViewById(R.id.imageButton1);
         near=(ImageButton)rootView.findViewById(R.id.imageButton4);
         find=(ImageButton)rootView.findViewById(R.id.imageButton5);
         photo=(ImageButton)rootView.findViewById(R.id.imageButton6);
         String ime = Singleton.getInstance().ime;
         int ace1=ime.length();
         if(ace1==0)
         {     
        	 user.setText("You are not logged in");
        	   login.setVisibility(View.VISIBLE);
        	   post1.setVisibility(View.GONE);
        
         }
	    else
	    {
	      
	      user.setText("Logged as: "+ime);
     	 login.setVisibility(View.GONE);
     	   post1.setVisibility(View.VISIBLE);
	    }    
         

         popular.setOnClickListener(new View.OnClickListener() {
 			
    			@Override
    			public void onClick(View arg0) {		
    				Fragment newFragment = new PopularPlacesFragment();
    				FragmentTransaction transaction = getFragmentManager().beginTransaction();

    				// Replace whatever is in the fragment_container view with this fragment,
    				// and add the transaction to the back stack
    				transaction.replace(R.id.frame_container, newFragment);
    				transaction.addToBackStack(null);
    				getActivity().setTitle("Popular places");
    				// Commit the transaction
    				transaction.commit();
 			}
    		});
     
         
         near.setOnClickListener(new View.OnClickListener() {
  			
 			@Override
 			public void onClick(View arg0) {		
 				Fragment newFragment = new NearPlacesFragment();
 				FragmentTransaction transaction = getFragmentManager().beginTransaction();

 				// Replace whatever is in the fragment_container view with this fragment,
 				// and add the transaction to the back stack
 				transaction.replace(R.id.frame_container, newFragment);
 				transaction.addToBackStack(null);
 				getActivity().setTitle("Near places");
 				// Commit the transaction
 				transaction.commit();
			}
 		});
  
         find.setOnClickListener(new View.OnClickListener() {
   			
  			@Override
  			public void onClick(View arg0) {		
  				Fragment newFragment = new FindPlacesFragment();
  				FragmentTransaction transaction = getFragmentManager().beginTransaction();

  				// Replace whatever is in the fragment_container view with this fragment,
  				// and add the transaction to the back stack
  				transaction.replace(R.id.frame_container, newFragment);
  				transaction.addToBackStack(null);
  				getActivity().setTitle("Find places");
  				// Commit the transaction
  				transaction.commit();
 			}
  		});
         
         photo.setOnClickListener(new View.OnClickListener() {
    			
   			@Override
   			public void onClick(View arg0) {		
   				Intent i = new Intent(getActivity(),PhotoActivity.class);
				startActivity(i);
  			}
   		});
         
         login.setOnClickListener(new View.OnClickListener() {
 			
    			@Override
    			public void onClick(View arg0) {		
    				
    				Intent i = new Intent(getActivity(),FacebokActivity.class);
    				startActivity(i);
    				
    			
 			}
    		});
         
         post1.setOnClickListener(new View.OnClickListener() {
 			
    			@Override
    			public void onClick(View arg0) {		
    				// create class object
    				
    				Intent i = new Intent(getActivity(),FacebokActivity.class);
    				startActivity(i);
    				
    			
 			}
    		});
        
         upcoming.setOnClickListener(new View.OnClickListener() {
  			
 			@Override
 			public void onClick(View arg0) {		
 				// create class object
 				
 				Intent i = new Intent(getActivity(),TripsActivity.class);
 				startActivity(i);
 				
 			
			}
 		});
         

       
        return rootView;
    }

	@Override
	public void onResume() {
	    super.onResume();
	    Log.e("kolkoo", "kolko");
	    
	  /*  int io=Singleton.getInstance().br;
	
	    if(io!=0)
	    {
	     String imee=Singleton.getInstance().ime;
//	    Intent i = getActivity().getIntent();
//	    Item=i.getExtras().getString("name");
 	    user.setText("Logged as: "+imee);
 	    fblogin.setVisibility(View.GONE);
	    }
	    else
	    {
	      user.setText("You are not logged in");
	    }    
	      
	 */     
	}
} 

