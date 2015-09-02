package timska;

import java.io.BufferedInputStream;
import java.io.IOException;
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

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.timska.Card;
import com.timska.CardArrayAdapter;
import com.timska.FoursquareVenue;
import com.timska.GPSTracker;
import com.timska.MapActivity;
import com.timska.R;

public class FindPlacesFragment extends Fragment {
	
	 
		private ArrayList<String> lista = new ArrayList<String>();
		private Geocoder geocoder;
		private Button find;
		private TextView lokacija;
		private TextView txt;
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
		JSONArray forsquereApiResults = null, venusTraka=null,lokacija2=null;
		private static String urlDel1 = "https://api.foursquare.com/v2/venues/search?ll=";
		private static String url = "";
		private static String urlDel2 = "&categoryId=4bf58dd8d48988d16d941735&oauth_token=0WJ1LKKR4NXVAJRT3IXGQCCPMTBF5LHCIE4LADGPINZZ4QCF&v=20150608";
		private static String langutude = "", venues, response, location,name,distance;
		// JSONArray venues;
		private static String latitude = "";
		ArrayAdapter<String> myAdapter;
		HashMap<String, String> lokacii;
		private Spinner sp;
		private String kat="",povik="";
		
	    private static final String TAG = "CardListActivity";
	    private CardArrayAdapter cardArrayAdapter;
	    private ListView listView;
	    ListAdapter adapter;
		private Button show;
		
		public FindPlacesFragment(){
			
		}
		
		@Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
	 
	        View rootView = inflater.inflate(R.layout.fragment_findplaces, container, false);
	        find=(Button)rootView.findViewById(R.id.button1);
	        lokacija=(EditText)rootView.findViewById(R.id.editText1);
	        txt=(TextView)rootView.findViewById(R.id.textView1);
	        show = (Button)rootView.findViewById(R.id.button2);
	        geocoder = new Geocoder(getActivity());
	        listView = (ListView) rootView.findViewById(R.id.card_listView);
	        
	      
	        
	        
	        find.setOnClickListener(new View.OnClickListener() {

			    private double lat;
				private double lng;

				public void onClick(View v) {
			    String add=lokacija.getText().toString();
			//ja krie tastaturata
			    InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
		        imm.hideSoftInputFromWindow(lokacija.getWindowToken(), 0);

			    	try {

			    	List<Address> addresses= geocoder.getFromLocationName(add, 1);

			    	if (addresses != null && !addresses.isEmpty()) {

			    	 Address address = addresses.get(0);
	                 lat=address.getLatitude();
			    	 lng=address.getLongitude();
			    	
			    	 String a1= Double.toString(lat);
			    	 String a2= Double.toString(lng);
			    	 
			    
			    	 longtitude = a1 + "," + a2 + "&radius=5000"
								+ "&categoryId=4bf58dd8d48988d16d941735,4bf58dd8d48988d1e5931735,4bf58dd8d48988d137941735,4d4b7105d754a06374d81259";
				        
			       	 new fourquare().execute();
			       	 show.setVisibility(Button.VISIBLE);
			    	}
			    	
			    	} 
			    	catch (IOException e)    {
			    	  
			    	}
					  

		        }    
			});
	        show.setOnClickListener(new View.OnClickListener() {


				public void onClick(View v) {
					Intent intent = new Intent(getActivity(),MapActivity.class);
					intent.putStringArrayListExtra("manev", lista);
		 			startActivity(intent);
		        }    
			});
	 
	        return rootView;
	    }

		private class fourquare extends AsyncTask<View, Void, String> {

			String temp;
			private String stats;
			private String chekins;

			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				// Showing progress dialog
				pDialog = new ProgressDialog(getActivity());
				pDialog.setMessage("Pleace wait...");
				pDialog.setCancelable(false);
				pDialog.show();

			}

			@Override
			protected String doInBackground(View... urls) {
				// make Call to the url

				povik ="https://api.foursquare.com/v2/venues/search?client_id="
						+ CLIENT_ID
						+ "&client_secret="
						+ CLIENT_SECRET
						+ "&v=20130815&ll=" + longtitude;
				
				
				temp = makeCall(povik);
				ServiceHandler sh = new ServiceHandler();

			
				urlDel2="&categoryId="+kat+"&oauth_token=0WJ1LKKR4NXVAJRT3IXGQCCPMTBF5LHCIE4LADGPINZZ4QCF&v=20150608";
				url ="https://api.foursquare.com/v2/venues/search?client_id="
						+ CLIENT_ID
						+ "&client_secret="
						+ CLIENT_SECRET
						+ "&v=20130815&ll=" + longtitude;  //urlDel1 + a1 + "," + a2 + urlDel2;
				
				
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
								

									JSONObject jsonLocation = new JSONObject(location);
									
									langutude = jsonLocation.getString("lat");
									Log.d("manol i fico mangi", langutude);
									
									latitude = jsonLocation.getString("lng");
									distance = jsonLocation.getString("distance");
									
									
									stats=tempRow.getString("stats");
									JSONObject statis = new JSONObject(stats);
									
									chekins=statis.getString("checkinsCount");
									Log.d("IHAAAAAAAAAAAAAAAA", chekins);
									
									Log.d("RARARARRARARARARARRARA", distance);
									double aa1 = Double.parseDouble(langutude);
									double aa2 = Double.parseDouble(latitude);
									String ace=name+";"+" "+langutude+" "+latitude+" "+distance+" "+chekins;
								lokacii.put(name,ace);
								
								}
			    

				} catch (JSONException e) {
					e.printStackTrace();
				}

				return null;
			}

		

			protected void onPostExecute(String result) {
				super.onPostExecute(result);	
				int nex=0;
				  cardArrayAdapter = new CardArrayAdapter(getActivity(), R.layout.list_item_card);
				String fico="";
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
				
						String niza=lokacii.get(name);
						
						Set<String> keys = lokacii.keySet();  //get all keys
					
					
						for(String p: keys)
						{ 
						
							Log.e("AAAAAAAAAAAAAA", lokacii.get(p));
							String op[]=lokacii.get(p).split(";");
							fico = op[0];
						
							String fico1=op[1];
							String kco[]=fico1.split(" ");
							String carsum=kco[3];
							Log.e("svetlanaaaa", carsum);
							String brCheks = kco[4];
							Log.e("FICO", fico);
							Log.e("kco-0", kco[1]);
							Log.e("kco-1", kco[2]);
							Log.e("Car", kco[3]);
							double aaa1 = Double.parseDouble(kco[1]);
							double aaa2 = Double.parseDouble(kco[2]);
							Log.e("aa1", String.valueOf(aaa1));
							
							String selba=fico+";"+kco[1]+" "+kco[2];
							
						if(i==0)
							{
							
							
								Card card = new Card(fico,"Distance:"+carsum+" m"+"          Chekins:"+brCheks);
						        cardArrayAdapter.add(card);
						        lista.add(selba);
							    listTitle.add(nex, fico);
							    nex++;
							
							}						
						}
						
						
					}
					 listView.setAdapter(cardArrayAdapter);
					// set the results to the list
					// and show them in the xml
				
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

