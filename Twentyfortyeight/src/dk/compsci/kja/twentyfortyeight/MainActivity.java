package dk.compsci.kja.twentyfortyeight;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

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
import dk.compsci.kja.twentyfortyeight.view.ScoreView;
import dk.compsci.kja.twentyfortyeight.view.TwentyfortyeightGrid;

public class MainActivity extends Activity implements EngineListener {

	private Engine _engine;
	private ArrayList<SimpleKeyListener> _keyListeners;
	private ArrayList<EngineController> _controllers;
	private TwentyfortyeightGrid _grid;
	private View _gameOverScreen;
	private int _animationDuration;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		_gameOverScreen = findViewById(R.id.game_over_screen);
		_gameOverScreen.setVisibility(View.GONE);
		_animationDuration = getResources().getInteger(
				android.R.integer.config_longAnimTime);

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
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext());
		EngineController controller;
		switch (InputMethod.valueOf(prefs.getInt(
				getString(R.string.HORIZONTAL), InputMethod.SWIPE.ordinal()))) {
		case SWIPE:
			Log.d("MAIN", "OnResume.switch.case.SWIPE");
			controller = new SwipeEngineController(this, _grid);
			break;
		case ROTATE:
			controller = new ScreenOrientationController(this);
			break;
		case VOLUME:
			VolumeEngineController volumeEngineController = new VolumeEngineController(
					this);
			addKeyListener(volumeEngineController);
			controller = volumeEngineController;
			break;
		default:
			controller = null;
			break;

		}
		;
		_controllers.add(controller);
		controller.attachHorizontal(_engine);

		switch (InputMethod.valueOf(prefs.getInt(getString(R.string.VERTICAL),
				InputMethod.SWIPE.ordinal()))) {
		case SWIPE:
			controller = new SwipeEngineController(this, _grid);
			break;
		case ROTATE:
			controller = new ScreenOrientationController(this);
			break;
		case VOLUME:
			VolumeEngineController volumeEngineController = new VolumeEngineController(
					this);
			addKeyListener(volumeEngineController);
			controller = volumeEngineController;
			break;
		default:
			controller = null;
			break;
		}
		;
		_controllers.add(controller);
		controller.attachVertical(_engine);
	}

	public void addKeyListener(final SimpleKeyListener l) {
		_keyListeners.add(l);
	}

	public void removeKeyListener(final OnKeyListener l) {
		_keyListeners.remove(l);
	}

	@Override
	public boolean dispatchKeyEvent(final KeyEvent event) {
		boolean consumed = false;
		for (SimpleKeyListener l : _keyListeners) {
			consumed |= l.onKey(event.getKeyCode());
		}
		return consumed;
	}

	public void clickReset(final View button) {
		detachControls();
		_engine.reset();
		if (_gameOverScreen.isShown()) {
			AlphaAnimation fadeOut = new AlphaAnimation(0.5f, 0.0f);
			fadeOut.setAnimationListener(new AnimationListener() {

				@Override
				public void onAnimationStart(final Animation animation) {
					// NO-OP
				}

				@Override
				public void onAnimationRepeat(final Animation animation) {
					// NO-OP
				}

				@Override
				public void onAnimationEnd(final Animation animation) {
					_gameOverScreen.setVisibility(View.GONE);
				}
			});
			fadeOut.setDuration(_animationDuration);
			_gameOverScreen.startAnimation(fadeOut);
		}
		attachControls();
	}

	public void clickSettings(final View button) {
		startActivity(new Intent(this, SettingsActivity.class));
	}

	public void clickHighscore(final View button) {
		startActivity(new Intent(this, HighscoreActivity.class));
	}

	public void clickSubmitScore(final View button) {
		// call 2048.compsci.dk/register.php?score=[score]

		URI myURI = null;

		try {
			myURI = new URI("http://2048.compsci.dk/register.php?score="
					+ _engine.getScore());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet getMethod = new HttpGet(myURI);
		try {
			httpClient.execute(getMethod);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Toast.makeText(this, "Score submitted!", Toast.LENGTH_LONG).show();
		clickReset(button);
	}

	@Override
	public void onChange(final Engine e) {
		if (e.isDone()) {
			detachControls();
			_gameOverScreen.setVisibility(View.VISIBLE);
			AlphaAnimation fadeIn = new AlphaAnimation(0.0f, 0.5f);
			fadeIn.setDuration(_animationDuration);
			fadeIn.setFillAfter(true);
			_gameOverScreen.startAnimation(fadeIn);
		}
	}

}
