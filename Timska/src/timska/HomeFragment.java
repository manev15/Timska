package timska;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import com.timska.R;


public class HomeFragment extends Fragment {
	
	private TextView user;
	private Button kopce;

	public HomeFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
         user=(TextView)rootView.findViewById(R.id.textView1);
         kopce=(Button)rootView.findViewById(R.id.button1);
         
         kopce.setOnClickListener(new View.OnClickListener() {

			    public void onClick(View v) {
			    	Intent i = getActivity().getIntent();
			    	 String Item = i.getExtras().getString("name");
			         user.setText(Item);


		        }    
			});
        
        return rootView;
    }
}
