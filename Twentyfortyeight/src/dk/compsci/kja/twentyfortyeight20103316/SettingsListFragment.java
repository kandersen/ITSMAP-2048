package dk.compsci.kja.twentyfortyeight20103316;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class SettingsListFragment extends Fragment {

	View _view;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		_view = inflater.inflate(R.layout.fragment_settings_list, container);
		ListView list = (ListView) _view.findViewById(R.id.list_settings);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.settings_array, R.layout.simple_list_item);
		list.setAdapter(adapter);
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View item, int arg2,
					long arg3) {
				CharSequence selection = ((TextView) item).getText().toString();
				Fragment controls = getFragmentManager().findFragmentById(R.id.fragment_settings_controls);
				if (controls == null) {
					Intent intent = new Intent(getActivity(), SingleSettingActivity.class);
					intent.putExtra(getActivity().getString(R.string.SELECTION), selection);
					startActivity(intent);					
				} else {
					if (selection.equals(getString(R.string.controls))) {
						
					} else if (selection.equals(getString(R.string.about))) {
						
					}
				}
				
				
			}
			
		});
		return _view;
	}
	
	

	

}
