package dk.compsci.kja.twentyfortyeight.controllers;

import dk.compsci.kja.twentyfortyeight.MainActivity;
import dk.compsci.kja.twentyfortyeight.SimpleKeyListener;
import android.view.KeyEvent;

public class VolumeEngineController extends AbstractEngineController implements SimpleKeyListener {

	public VolumeEngineController(MainActivity a) {
		a.addKeyListener(this);
	} 

	@Override
	public boolean onKey(int keyCode) {
		if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
			down();
			left();
		} else if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
			up();
			right();
		}
		return true;
	}
	
	

}
