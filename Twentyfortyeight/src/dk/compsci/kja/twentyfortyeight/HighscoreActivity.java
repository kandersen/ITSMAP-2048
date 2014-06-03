package dk.compsci.kja.twentyfortyeight;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
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

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.ArrayAdapter;

public class HighscoreActivity extends FragmentActivity {

	private AsyncTask<Object, Object, ArrayList<String>> _task = new AsyncTask<Object, Object, ArrayList<String>>() {

		@Override
		protected ArrayList<String> doInBackground(final Object... arg0) {
			String result = null;
			URI myURI = null;

			try {
				myURI = new URI("http://2048.compsci.dk/json.php");
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
			HttpClient httpClient = new DefaultHttpClient();
			HttpGet getMethod = new HttpGet(myURI);
			HttpResponse webServerResponse = null;
			try {
				webServerResponse = httpClient.execute(getMethod);
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
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
					} finally {
						instream.close();
					}
					result = sb.toString();
					instream.close();
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}

			ArrayList<String> res = new ArrayList<String>();
			try {
				JSONArray array = new JSONArray(result);

				for (int i = 0; i < array.length(); i++) {
					JSONObject windJSON = array.getJSONObject(i);

					int score = windJSON.getInt("score");
					res.add(windJSON.getString("score"));
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return res;
		}

		@Override
		protected void onPostExecute(final ArrayList<String> result) {
			_list.clear();
			_list.addAll(result);
		}

	};

	private ArrayAdapter<String> _adapter;
	private List<String> _list;

	@Override
	protected void onCreate(final Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_settings);
		_list = new ArrayList<String>();
		_adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, _list);

		_task.execute();
	}

}
