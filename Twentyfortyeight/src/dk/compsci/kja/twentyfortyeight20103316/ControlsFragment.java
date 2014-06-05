package dk.compsci.kja.twentyfortyeight20103316;

import java.util.Arrays;
import java.util.List;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class ControlsFragment extends Fragment {
	
	private View _view;
	private Spinner _horSpinner;
	private Spinner _vertSpinner;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		_view = inflater.inflate(R.layout.fragment_controls, container, false);
		String[] strings = { getString(R.string.swipe), 
							 getString(R.string.volume_control),
							 getString(R.string.screen_rotation) };
		List<String> options = Arrays.asList(strings);		
		_horSpinner = (Spinner) _view.findViewById(R.id.spinner_horizontal_axis);
		_vertSpinner = (Spinner) _view.findViewById(R.id.spinner_vertical_axis);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.simple_list_item, options);
		_horSpinner.setAdapter(adapter);
		_vertSpinner.setAdapter(adapter);
		
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
		int selectedHorizontal = prefs.getInt("Horizontal", InputMethod.SWIPE.ordinal());
		_horSpinner.setSelection(selectedHorizontal);
		
		int selectedVertical = prefs.getInt("Vertical", InputMethod.SWIPE.ordinal());		
		_vertSpinner.setSelection(selectedVertical);
		
		return _view;
				
	}

	@Override
	public void onPause() {
		super.onPause();
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
		Editor editor = prefs.edit();
		editor.putInt(getString(R.string.VERTICAL), _vertSpinner.getSelectedItemPosition());
		editor.putInt(getString(R.string.HORIZONTAL), _horSpinner.getSelectedItemPosition());
		Log.d("Controls", "" + editor.commit());
	}
	
}
