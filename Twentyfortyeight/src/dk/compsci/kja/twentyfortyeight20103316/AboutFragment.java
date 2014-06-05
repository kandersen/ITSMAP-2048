package dk.compsci.kja.twentyfortyeight20103316;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class AboutFragment extends Fragment {
	
	private View _view;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		_view = inflater.inflate(R.layout.fragment_about, container, false);
		
		new FetchLicense(_view, R.id.apache_text, "apache.txt", getString(R.string.apache_msg)).execute();
		new FetchLicense(_view, R.id.MIT_text, "MIT.txt", getString(R.string.MIT_msg)).execute();
		
		return _view;
	}
	

	private class FetchLicense extends AsyncTask<Void, Void, String> {
		
		private final String _fileName; 
		private final int _viewId;
		private final View _view;
		private String _orElse;
		
		public FetchLicense(View view, int viewId, String fileName, String orElse) {
			_fileName = fileName;
			_viewId = viewId;
			_view = view;
			_orElse = orElse;
		}
		
		@Override
		protected String doInBackground(Void... params) {				
			try {
				StringBuilder content = new StringBuilder();			
				BufferedReader reader = new BufferedReader(new InputStreamReader(getActivity().getAssets().open("licenses/" + _fileName)));			
				String buffer;
				while ((buffer = reader.readLine()) != null) {
					content.append(buffer);
				}
				return content.toString();				
			} catch (IOException e) {
				return _orElse;
			}			
		}

		@Override
		protected void onPostExecute(String result) {
			((TextView)_view.findViewById(_viewId)).setText(result);
		}
		
		
	}
	
}
