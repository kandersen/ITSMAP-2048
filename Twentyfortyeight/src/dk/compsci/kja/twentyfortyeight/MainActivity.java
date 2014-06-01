package dk.compsci.kja.twentyfortyeight;

import java.util.ArrayList;
import dk.compsci.kja.twentyfortyeight.view.ScoreView;
import dk.compsci.kja.twentyfortyeight.view.TwentyfortyeightGrid;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.Toast;

public class MainActivity extends Activity implements EngineListener {

	private Engine _engine;
	private ArrayList<SimpleKeyListener> _keyListeners;
	private ArrayList<EngineController> _controllers;
	private TwentyfortyeightGrid _grid;
	private View _gameOverScreen;
	private int _animationDuration;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		_gameOverScreen = findViewById(R.id.game_over_screen);
		_gameOverScreen.setVisibility(View.GONE);
		_animationDuration = getResources().getInteger(android.R.integer.config_longAnimTime);
		
		_engine = new MockEngine();
		_engine.addChangeListener(this);
		_keyListeners = new ArrayList<SimpleKeyListener>();
		_controllers = new ArrayList<EngineController>();
		
		_grid = (TwentyfortyeightGrid) findViewById(R.id.grid);
		ScoreView scoreView = (ScoreView) findViewById(R.id.score_view);
		_grid.setEngine(_engine);
		scoreView.setEngine(_engine);		
	}
	
	@Override
	protected void onPause() {		
		super.onPause();
		Log.d("MAIN", "onPause");
		detachControls();
		
	}

	private void detachControls() {
		for (EngineController ec : _controllers) {
			ec.removeHorizontal(_engine);
			ec.removeVertical(_engine);			
		}
		_controllers.clear();
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.d("MAIN", "onResume");
		attachControls();
	}

	private void attachControls() {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		EngineController controller;
		switch (InputMethod.valueOf(prefs.getInt(getString(R.string.HORIZONTAL), InputMethod.SWIPE.ordinal()))) {
		case SWIPE:
			Log.d("MAIN", "OnResume.switch.case.SWIPE");
			controller = new SwipeEngineController(this, _grid);			
			break;
		case ROTATE:
			controller = new ScreenOrientationController(this);
			break;
		case VOLUME:
			VolumeEngineController volumeEngineController = new VolumeEngineController(this);
			addKeyListener(volumeEngineController);
			controller = volumeEngineController;
			break;
		default:
			controller = null;
			break;
		
		};
		_controllers.add(controller);
		controller.attachHorizontal(_engine);
		
		switch (InputMethod.valueOf(prefs.getInt(getString(R.string.VERTICAL), InputMethod.SWIPE.ordinal()))) {
		case SWIPE:
			controller = new SwipeEngineController(this, _grid);
			break;
		case ROTATE:
			controller = new ScreenOrientationController(this);
			break;
		case VOLUME:
			VolumeEngineController volumeEngineController = new VolumeEngineController(this);
			addKeyListener(volumeEngineController);
			controller = volumeEngineController;
			break;
		default:
			controller = null;
			break;
		};
		_controllers.add(controller);
		controller.attachVertical(_engine);
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
		detachControls();
		_engine.reset();
		if(_gameOverScreen.isShown()) {
			AlphaAnimation fadeOut = new AlphaAnimation(1.0f, 0.0f);
			fadeOut.setAnimationListener(new AnimationListener() {

				@Override
				public void onAnimationStart(Animation animation) { 
					//NO-OP
				}

				@Override
				public void onAnimationRepeat(Animation animation) {
					//NO-OP
				}

				@Override
				public void onAnimationEnd(Animation animation) {
					_gameOverScreen.setVisibility(View.GONE);				
				}
			});
			fadeOut.setDuration(_animationDuration);
			_gameOverScreen.startAnimation(fadeOut);
		}
		attachControls();
	}
	
	public void clickSettings(View button) {
		startActivity(new Intent(this, SettingsActivity.class));
	}

	public void clickSubmitScore(View button) {
		Toast.makeText(this, "Score submitted!", Toast.LENGTH_LONG).show();
		clickReset(button);
	}
	
	@Override
	public void onChange(Engine e) {
		if(e.isDone()) {
			detachControls();
			_gameOverScreen.setVisibility(View.VISIBLE);
			AlphaAnimation fadeIn = new AlphaAnimation(0.0f, 1.0f);
			fadeIn.setDuration(_animationDuration);
			fadeIn.setFillAfter(true);
			_gameOverScreen.startAnimation(fadeIn);		
		}		
	}
	
	
	
}
