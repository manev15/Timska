package timska;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class JSonParser {

	
	// parsing valid jSon String into JSONObject
		public static JSONObject parseStringToJson(String jSon) {
			JSONObject jObj = null;
			
			try {
				jObj = new JSONObject(jSon);
				Log.d("RR", jObj.toString());
			} catch (JSONException e) {
				 Log.e("JSON Parser", "--"+ e.getMessage()+ "Error parsing data " + e.toString() + " ----" );
				 //e.printStackTrace();
			}
			return jObj;
		}
		// parsing valid jSon StringArray into JSONArray
		public static JSONArray parseStringToJsonArray(String jSon)
		{
			JSONArray jArr = null;
			try {
				jArr = new JSONArray(jSon);
			} catch (JSONException e) {
				Log.e("JSON Parser", "--"+ e.getMessage()+ "Error parsing data " + e.toString() + " ----" );
			}
			return jArr;
		}
}