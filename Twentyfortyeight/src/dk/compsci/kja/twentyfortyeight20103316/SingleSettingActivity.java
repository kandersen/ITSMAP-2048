package dk.compsci.kja.twentyfortyeight20103316;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

public class SingleSettingActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_single_setting);
		FrameLayout frame = (FrameLayout) findViewById(R.id.single_setting_frame);
		String selection = (String) getIntent().getExtras().get(getString(R.string.SELECTION));
		
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		if (selection.equals(getString(R.string.controls))) {
			transaction.add(frame.getId(), new ControlsFragment());
		} else if (selection.equals(getString(R.string.about))) {
			transaction.add(frame.getId(), new AboutFragment());
		}
		transaction.commit();
	}

	
	
}
