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

public class TranslateTask implements Runnable{
	private static final String TAG = "TranslateTask";
	private final Translate translate;
	private final String original, from, to;
	
	TranslateTask(Translate translate, String original, String from, String to) {
		this.translate = translate;
		this.original = original;
		this.from = from;
		this.to = to;
	}
	
	public void run() {
		//Translate the original text to the target language
		String trans = doTranslate(original, from, to);
		translate.setTranslated(trans);
		//Then translate what we got back to the first language.
		//Ideally it would be identical but it usually isn't
		String retrans = doTranslate(trans, to, from);  //swapped
		translate.setRetranslated(retrans);
	}
	/**Call the Google Translation API to translate a string from one language to another. For more info on the API see:
	 * * http://code.google.com/apis/ajaxlanguage*/
	private String doTranslate(String original, String from, String to) {
		String result = translate.getResources().getString(R.string.translation_error);
		HttpURLConnection con = null;
		Log.d(TAG, "doTranslate(" + original + " " + from + ", " + to + ")");
		try {
			//Check if task has been interrupted
			if (Thread.interrupted())
				throw new InterruptedException();
			//Build RESTful query for Google API
			String q = URLEncoder.encode(original, "UTF-8");
			URL url = new URL("http://ajax.googleapis.com/ajax/services/language/translate" + "?v=1.0" + "&q=" + q + "&langpair=" + from + "%7C" + to);
			con = (HttpURLConnection)url.openConnection();
			con.setReadTimeout(10000 /* milliseconds */);
			con.setConnectTimeout(15000 /*milliseconds*/);
			con.setRequestMethod("GET");
			con.addRequestProperty("Referer", "http://www.gintellect.com/android-translator");
			con.setDoInput(true);
			//Start the Query
			con.connect();
			//Check if task has been interrupted
			if (Thread.interrupted())
				throw new InterruptedException();
			//Read results from the query
			BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
			String payload = reader.readLine();
			reader.close();
			//Parse to get translated text
			JSONObject jsonObject = new JSONObject(payload);
			result = jsonObject.getJSONObject("responseData")
				.getString("translatedText")
				.replace("&#39;", "'")
				.replace("&amp;", "&");
			//Check if task has been interrupted
			if (Thread.interrupted())
				throw new InterruptedException();
		}
		catch (IOException e) {
			Log.e(TAG, "IOException", e);
		}
		catch (JSONException e) {
			Log.e(TAG, "JSONException", e);
		}
		catch (InterruptedException e) {
			Log.d(TAG, "InterruptedException", e);
			result = translate.getResources().getString(R.string.translation_interrupted);
		}
		finally {
			if (con != null) {
				con.disconnect();
			}
		}
		//All done
		Log.d(TAG, "   => returned " + result);
		return result;
	}
}
