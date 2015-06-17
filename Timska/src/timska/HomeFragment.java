package timska;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.timska.FacebokActivity;
import com.timska.R;


public class HomeFragment extends Fragment {
	
	private TextView user;
	private Button kopce;
	private Button fblogin;
	Singleton lol;
	public HomeFragment(){}
	public int ace;
	private String Item="",ae;
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
         user=(TextView)rootView.findViewById(R.id.textView2);
         fblogin=(Button)rootView.findViewById(R.id.fblogin);
      
         fblogin.setOnClickListener(new View.OnClickListener() {
        			
           			@Override
           			public void onClick(View arg0) {		
           				// create class object
           				ace++;
           				Intent i = new Intent(getActivity(),FacebokActivity.class);
           				startActivity(i);
           				
           			
        			}
           		});
       
        return rootView;
    }

	@Override
	public void onResume() {
	    super.onResume();
	    Log.e("kolkoo", "kolko");
	    
	    int io=Singleton.getInstance().br;
	
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
	      
	      
	}
} 

