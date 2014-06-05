package dk.compsci.kja.twentyfortyeight20103316;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class HighscoreActivity extends Activity {

	private AsyncTask<Object, Object, List<String>> _task = new AsyncTask<Object, Object, List<String>>() {

		@Override
		protected List<String> doInBackground(final Object... arg0) {
			String result = null;
			URI myURI = null;

			try {
				myURI = new URI("http://2048.compsci.dk/json.php");
			} catch (URISyntaxException e) {
				e.printStackTrace();
				return Arrays.asList("No connection to the server!");
			}
			HttpClient httpClient = new DefaultHttpClient();
			HttpGet getMethod = new HttpGet(myURI);
			HttpResponse webServerResponse = null;
			try {
				webServerResponse = httpClient.execute(getMethod);
			} catch (ClientProtocolException e) {
				e.printStackTrace();
				return Arrays.asList("No connection to the server!");
			} catch (IOException e) {
				e.printStackTrace();
				return Arrays.asList("No connection to the server!");
			}

			HttpEntity httpEntity = webServerResponse.getEntity();

			if (httpEntity != null) {
				InputStream instream;
				try {
					instream = httpEntity.getContent();
					StringBuilder sb = new StringBuilder();
					String line = null;

					try {
						BufferedReader reader = new BufferedReader(
								new InputStreamReader(instream, "UTF-8"));
						while ((line = reader.readLine()) != null) {
							sb.append(line).append("\n");
						}
					} catch (Exception e) {
						e.printStackTrace();
						return Arrays.asList("No connection to the server!");
					} finally {
						instream.close();
					}
					result = sb.toString();
					instream.close();
				} catch (IllegalStateException e) {
					e.printStackTrace();
					return Arrays.asList("No connection to the server!");
				} catch (IOException e) {
					e.printStackTrace();
					return Arrays.asList("No connection to the server!");
				}

			}

			ArrayList<String> res = new ArrayList<String>();
			try {
				JSONArray array = new JSONArray(result);

				for (int i = 0; i < array.length(); i++) {
					JSONObject windJSON = array.getJSONObject(i);

					res.add(windJSON.getString("score"));
				}
			} catch (JSONException e) {
				e.printStackTrace();
				return Arrays.asList("No connection to the server!");
			}
			return res;
		}

		@Override
		protected void onPostExecute(final List<String> result) {			
			String[] strings = new String[result.size()];
			result.toArray(strings);
			_adapter = new ArrayAdapter<String>(HighscoreActivity.this, R.layout.simple_list_item, strings);
			ListView list = (ListView)findViewById(R.id.highscore_list);
			list.setAdapter(_adapter);
			
		}

	};

	private ArrayAdapter<String> _adapter;

	@Override
	protected void onCreate(final Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_highscore);
		_task.execute();
	}

}
