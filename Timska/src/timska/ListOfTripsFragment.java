package timska;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.timska.R;

public class ListOfTripsFragment extends Fragment {
	
	public ListOfTripsFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_listoftrips, container, false);
         
        return rootView;
    }
}
