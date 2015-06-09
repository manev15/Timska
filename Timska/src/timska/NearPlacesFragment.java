package timska;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.ByteArrayBuffer;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.ListFragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.timska.FoursquareVenue;
import com.timska.GPSTracker;
import com.timska.R;

public class NearPlacesFragment extends ListFragment {

	// GPSTracker class
	GPSTracker gps;
	private LatLng lokacija1;
	ArrayList<FoursquareVenue> venuesList;
	GoogleMap mMap;
	GoogleMap googleMap;
	final String CLIENT_ID = "3WM1FK1I5WIOTE4HK2CPUJTXV1HS23I2GQ414ZJ5IN5CY0C2";
	final String CLIENT_SECRET = "1D1WHQIY1WUYU4QXBRYV2ZFUP10PIDH0VPRY0NNXMVDVT3YS";
	private String longtitude;
	String a1;
	String a2;
	private ProgressDialog pDialog;
	JSONArray forsquereApiResults = null, venusTraka=null,lokacija=null;
	private static String urlDel1 = "https://api.foursquare.com/v2/venues/search?ll=";
	private static String url = "";
	private static String urlDel2 = "&categoryId=4bf58dd8d48988d16d941735&oauth_token=0WJ1LKKR4NXVAJRT3IXGQCCPMTBF5LHCIE4LADGPINZZ4QCF&v=20150608";
	private static String langutude = "", venues, response, location,name;
	// JSONArray venues;
	private static String latitude = "";
	ArrayAdapter<String> myAdapter;
	HashMap<String, String> lokacii;

	// private double latitute=41.3182956;

	// private double longitude=22.5565718;
	// private double latitute;

	// private double longitude;

	public void PagesFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		gps = new GPSTracker(getActivity());

		// check if GPS enabled
		if (gps.canGetLocation()) {

			double latitude = gps.getLatitude();
			double longitude = gps.getLongitude();
			// String a1=Double.toString(latitude);
			// String a2=Double.toString(longitude);

			a1 = Double.toString(latitude);
			a2 = Double.toString(longitude);

			longtitude = a1 + "," + a2 + "&radius=5000"
					+ "&categoryId=4bf58dd8d48988d16d941735";
			// \n is for new line

			Toast.makeText(
					getActivity(),
					"Your Location is - \nLat: " + latitude + "\nLong: "
							+ longitude, Toast.LENGTH_LONG).show();
		} else {
			// can't get location
			// GPS or Network is not enabled
			// Ask user to enable GPS/network in settings
			gps.showSettingsAlert();
		}

		View rootView = inflater.inflate(R.layout.fragment_nearplaces,
				container, false);
		mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
				.getMap();
		mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
		new fourquare().execute();
		return rootView;
	}

	private class fourquare extends AsyncTask<View, Void, String> {

		String temp;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// Showing progress dialog
			pDialog = new ProgressDialog(getActivity());
			pDialog.setMessage("Ве молиме почекајте...");
			pDialog.setCancelable(true);
			pDialog.show();

		}

		@Override
		protected String doInBackground(View... urls) {
			// make Call to the url
			temp = makeCall("https://api.foursquare.com/v2/venues/search?client_id="
					+ CLIENT_ID
					+ "&client_secret="
					+ CLIENT_SECRET
					+ "&v=20130815&ll=" + longtitude);

			ServiceHandler sh = new ServiceHandler();

			// Making a request to url and getting response
			url = urlDel1 + a1 + "," + a2 + urlDel2;

			String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET, false);

			Log.d("Response: ", "> " + jsonStr);

			// Getting JSON Array node
			forsquereApiResults = JSonParser.parseStringToJsonArray(jsonStr);
			// igraci = JSonParser.parseStringToJsonArray(jsonStr);
			try {
				lokacii = new HashMap<String,String>();
				// null
				JSONObject jsonObj = new JSONObject(jsonStr);
				Log.d("Json", "-" + jsonObj);
				response = jsonObj.getString("response");
				Log.d("Response Json", "-" + response);

				
						venusTraka= JSonParser.parseStringToJsonArray(response);
						JSONObject jsonObjVenus = new JSONObject(response);
						venues = jsonObjVenus.getString("venues");
						
						Log.d("manol i fico mangi", venues);
						JSONArray result = JSonParser.parseStringToJsonArray(venues);
						for (int i = 0; i < result.length(); i++) {
							JSONObject tempRow = null;

							tempRow = result.getJSONObject(i);
							name= tempRow.getString("name");
							Log.d("NAME", name);
							location=tempRow.getString("location");
							Log.d("Location", location);
							
							
//							for(int k = 0; k < 5; k++)
//							{
								JSONObject jsonLocation = new JSONObject(location);
								
								langutude = jsonLocation.getString("lat");
								Log.d("manol i fico mangi", langutude);
								
								latitude = jsonLocation.getString("lng");
								Log.d("manol i fico mangi1", latitude);
								double aa1 = Double.parseDouble(langutude);
								double aa2 = Double.parseDouble(latitude);
								String ace=name+","+" "+langutude+" "+latitude;
							lokacii.put(name,ace);
							//lokacii.put(name,name);
								
							
								
							}
					//	}        
						


			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;
		}

	

		protected void onPostExecute(String result) {
			super.onPostExecute(result);

			// Dismiss the progress dialog
			if (pDialog.isShowing())
				pDialog.dismiss();

			if (temp == null) {
				// we have an error to the call
				// we can also stop the progress bar
			} else {
				// all things went right

				// parseFoursquare venues search result
				venuesList = (ArrayList<FoursquareVenue>) parseFoursquare(temp);

				List<String> listTitle = new ArrayList<String>();

				for (int i = 0; i < venuesList.size(); i++) {
					// make a list of the venus that are loaded in the list.
					// show the name, the category and the city
					
					listTitle.add(i, venuesList.get(i).getName() + ", "
							+ venuesList.get(i).getCategory() + ""
							+ venuesList.get(i).getCity());
					double aa1 = Double.parseDouble(a1);
					double aa2 = Double.parseDouble(a2);
					mMap.addMarker(new MarkerOptions().title("I am here")
							.position(new LatLng(aa1, aa2)));

					lokacija1 = new LatLng(aa1, aa2);
					mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
							lokacija1, 15));
					String niza=lokacii.get(name);
					
					Set<String> keys = lokacii.keySet();  //get all keys
					for(String p: keys)
					{
					 
						Log.e("AAAAAAAAAAAAAA", lokacii.get(p));
						String op[]=lokacii.get(p).split(",");
						String fico = op[0];
						String fico1=op[1];
						String kco[]=fico1.split(" ");
						Log.e("FICO", fico);
						Log.e("kco-0", kco[1]);
						Log.e("kco-1", kco[2]);
						double aaa1 = Double.parseDouble(kco[1]);
					double aaa2 = Double.parseDouble(kco[2]);
						Log.e("aa1", String.valueOf(aaa1));
						mMap.addMarker(new MarkerOptions().title(fico)
								.position(new LatLng(aaa1, aaa2)));
					}
					
			
//					ListAdapter adapter = new SimpleAdapter(getActivity(),lokacii,R.layout.fragment_nearplaces,new String[]
//					{
//						
//						
//						
//						
//					});
									
					
					
					
				}

				// set the results to the list
				// and show them in the xml
				myAdapter = new ArrayAdapter<String>(getActivity(),
						R.layout.row_layout, R.id.listText, listTitle);
				setListAdapter(myAdapter);
			}
		}
	}

	public static String makeCall(String url) {

		// string buffers the url
		StringBuffer buffer_string = new StringBuffer(url);
		String replyString = "";

		// instanciate an HttpClient
		HttpClient httpclient = new DefaultHttpClient();
		// instanciate an HttpGet
		HttpGet httpget = new HttpGet(buffer_string.toString());

		try {
			// get the responce of the httpclient execution of the url
			HttpResponse response = httpclient.execute(httpget);
			InputStream is = response.getEntity().getContent();

			// buffer input stream the result
			BufferedInputStream bis = new BufferedInputStream(is);
			ByteArrayBuffer baf = new ByteArrayBuffer(20);
			int current = 0;
			while ((current = bis.read()) != -1) {
				baf.append((byte) current);
			}
			// the result as a string is ready for parsing
			replyString = new String(baf.toByteArray());
		} catch (Exception e) {
			e.printStackTrace();
		}
		// trim the whitespaces
		return replyString.trim();
	}

	private static ArrayList<FoursquareVenue> parseFoursquare(
			final String response) {

		ArrayList<FoursquareVenue> temp = new ArrayList<FoursquareVenue>();
		try {

			// make an jsonObject in order to parse the response
			JSONObject jsonObject = new JSONObject(response);

			// make an jsonObject in order to parse the response
			if (jsonObject.has("response")) {
				if (jsonObject.getJSONObject("response").has("venues")) {
					JSONArray jsonArray = jsonObject.getJSONObject("response")
							.getJSONArray("venues");

					for (int i = 0; i < jsonArray.length(); i++) {
						FoursquareVenue poi = new FoursquareVenue();
						if (jsonArray.getJSONObject(i).has("name")) {
							poi.setName(jsonArray.getJSONObject(i).getString(
									"name"));

							if (jsonArray.getJSONObject(i).has("location")) {
								if (jsonArray.getJSONObject(i)
										.getJSONObject("location")
										.has("address")) {
									if (jsonArray.getJSONObject(i)
											.getJSONObject("location")
											.has("city")) {
										poi.setCity(jsonArray.getJSONObject(i)
												.getJSONObject("location")
												.getString("city"));
									}
									if (jsonArray.getJSONObject(i).has(
											"categories")) {
										if (jsonArray.getJSONObject(i)
												.getJSONArray("categories")
												.length() > 0) {
											if (jsonArray.getJSONObject(i)
													.getJSONArray("categories")
													.getJSONObject(0)
													.has("icon")) {
												poi.setCategory(jsonArray
														.getJSONObject(i)
														.getJSONArray(
																"categories")
														.getJSONObject(0)
														.getString("name"));
											}
										}
									}
									temp.add(poi);
								}
							}
						}
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<FoursquareVenue>();
		}
		return temp;

	}
}