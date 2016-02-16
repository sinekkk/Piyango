package com.piyango.piyango;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

public class CekilisRequest extends AsyncTask<Void, Void, String> {
    public interface Callback<T> {

        public abstract void onFail();

        public abstract void onStart();

        public abstract void onSuccess(String obj);
    }

    private Callback<String> callback;
	String tur ="superloto";
	public CekilisRequest(String tur,Callback<String> callback) {
        this.callback = callback;
        this.tur = tur;
	}

	@Override
	protected String doInBackground(Void... arg0) {
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost("http://www.millipiyango.gov.tr/sonuclar/listCekilisleriTarihleri.php");
		// Building post parameters, key and value pair
		List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(1);
		nameValuePair.add(new BasicNameValuePair("tur", tur));
		// Url Encoding the POST parameters
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
		}
		catch (UnsupportedEncodingException e) {
			// writing error to Log
			e.printStackTrace();
		}
		try {
			HttpResponse response = httpClient.execute(httpPost);
			BufferedReader inBuffer = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

			StringBuffer stringBuffer = new StringBuffer("");
			String line = "";
			StringBuilder content = new StringBuilder();
			while ((line = inBuffer.readLine()) != null) {
				 content.append(line);
				Log.d("MilliPiyango", line);
			}
            return content.toString();



		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
    @Override
    protected void onPostExecute(String result)  {

        if (null == result) {
            callback.onFail();
        } else {
            callback.onSuccess(result);
        }
    }
    @Override
    protected void onPreExecute() {
        callback.onStart();
    }

}