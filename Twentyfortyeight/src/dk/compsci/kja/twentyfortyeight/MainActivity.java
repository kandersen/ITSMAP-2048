package dk.compsci.kja.twentyfortyeight;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;

public class MainActivity extends Activity {

	private Engine _engine;
	private ArrayList<SimpleKeyListener> _keyListeners;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		_engine = new MockEngine();
		_keyListeners = new ArrayList<SimpleKeyListener>();
		
		TwentyfortyeightGrid grid = (TwentyfortyeightGrid) findViewById(R.id.grid);
		ScoreView scoreView = (ScoreView) findViewById(R.id.score_view);
		grid.setEngine(_engine);
		scoreView.setEngine(_engine);
		SwipeEngineController controller = new SwipeEngineController(this);
		VolumeEngineController controller2 = new VolumeEngineController(this);
		ScreenOrientationController controller3 = new ScreenOrientationController(this);
		controller.attachHorizontal(_engine);
		controller.attachVertical(_engine);
		controller2.attachHorizontal(_engine);		
		controller3.attachVertical(_engine);
		grid.setOnTouchListener(controller);
	}

	public void addKeyListener(SimpleKeyListener l) {
		_keyListeners.add(l);	
	}
	
	public void removeKeyListener(OnKeyListener l) {
		_keyListeners.remove(l);
	}
	
	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		boolean consumed = false;
		for (SimpleKeyListener l : _keyListeners) {
			consumed |= l.onKey(event.getKeyCode());
		}
		return consumed;
	}
	
	public void clickReset(View button) {
		_engine.reset();
	}
	
	
	
}
