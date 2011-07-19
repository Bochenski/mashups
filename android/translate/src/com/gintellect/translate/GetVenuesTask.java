package com.gintellect.translate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import org.json.JSONException;
import org.json.JSONObject;
import android.util.Log;

public class GetVenuesTask implements Runnable{
	private static final String TAG = "GetVenuesTask";
	private final Translate translate;

	
	GetVenuesTask(Translate translate) {
		this.translate = translate;
	}
	
	public void run() {

		String venue = doGetVenues();
		translate.setVenue(venue);
	}
	
	private String doGetVenues() {
		String result = "GetVenueError";
		HttpURLConnection con = null;
		try {

			URL url = new URL("http://www.stocksystem.dotcloud.com/venue.xml");
			con = (HttpURLConnection)url.openConnection();
			con.setReadTimeout(10000);
			con.setConnectTimeout(15000);
			con.setRequestMethod("GET");
			con.setDoInput(true);
			con.connect();
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream(),"UTF-8"));
			String payload = reader.readLine();
			reader.close();

			result = payload;
		}
		catch (IOException e) {
			Log.e(TAG, "IOException", e);
		}
		finally {
			if (con != null) {
				con.disconnect();
			}
		}
		return result;
	}

}
