package com.piyango.json;


import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class FetchJsonDirectTask<T> extends AsyncTask<Object, Void, T> {
	public interface Callback<T> {
		public abstract void onFail();

		public abstract void onStart();

		public abstract void onSuccess(T obj);
	}
	private Class<T> object;
	private String path;
	private String asp;

	private Callback<T> callback;

	public FetchJsonDirectTask(Class<T> object, String path, String asp ,Callback<T> callback) {
		this.object = object;
		this.path = path;
		this.asp = asp;
		this.callback = callback;
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	protected T doInBackground(Object... params) {
		HttpClient httpclient = new DefaultHttpClient();
		Log.d("FetchJsonRequest", Constants.CEKILIS_URL + path);
		HttpPost httppost = new HttpPost(Constants.CEKILIS_URL + path);
//		httppost.addHeader("Cookie","ASP.NET_SessionId="+asp);//"ASP.NET_SessionId=qxa0dzhlq02usf0pix3l3hrw");
		
		try {
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
			for (int i = 0; i < params.length; i = i + 2) {
				String name,value;
				List<String> valueList;
				if( params[i].toString().lastIndexOf("#") == params[i].toString().length()-1){
					name = params[i].toString().substring(0, params[i].toString().length()-1);
					valueList = ((List<String>) params[i + 1]);
					for (String s : valueList) {
						nameValuePairs.add(new BasicNameValuePair(name, s));						
					}

				}else{
					name = params[i].toString();
					value = params[i + 1].toString();
					nameValuePairs.add(new BasicNameValuePair(name, value));
				}

			}
			
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
			Log.i("piyango",httppost.getURI().toString()+"?"+nameValuePairs.toString());
			// Execute HTTP Post Request
			HttpResponse response = httpclient.execute(httppost);
			if (response.getStatusLine().getStatusCode() != 200) {
				// Meaning an error has occured
				return null;
			}
			
			
			InputStream is = response.getEntity().getContent();

			// Compatible with JavaScript's Date format
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'hh:mm:ss.sss'Z'").create();
			Reader reader = new InputStreamReader(is);
			// Parse the fetched JSON object to a Java object
			T myObj = gson.fromJson(reader, object);
			Log.d("Recieved Object", myObj.toString());
			return myObj;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Log.d("FetchJsonRequestFail", Constants.CEKILIS_URL + path);
		return null;
	}
	@Override
	protected void onPostExecute(T result)  {
		if (null == result) {
			Log.d("piyango", "İstek cevabı gelmedi!");
			callback.onFail();
		} else {
			
			Log.d("piyango", "İstek başarılı!");
			callback.onSuccess(result);
			
		}
	}

	@Override
	protected void onPreExecute() {

		
		callback.onStart();
	}
	
}

