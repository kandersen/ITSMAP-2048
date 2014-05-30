package dk.compsci.kja.twentyfortyeight.controllers;

import java.util.ArrayList;

import dk.compsci.kja.twentyfortyeight.Engine;
import dk.compsci.kja.twentyfortyeight.EngineController;
import android.content.Context;
import android.view.OrientationEventListener;

public class ScreenOrientationController extends OrientationEventListener implements EngineController {

	private ArrayList<Engine> _horizontalAxes;
	private ArrayList<Engine> _verticalAxes;
	private int _previousOrientation;
	
	public static final int UPRIGHT = 0;
	public static final int LANDSCAPE_RIGHT = 90;
	public static final int UPSIDE_DOWN = 180;
	public static final int LANDSCAPE_LEFT = 270;
	public static final int UNKNOWN = ORIENTATION_UNKNOWN;

	public ScreenOrientationController(Context context) {
		super(context);
		enable();
		_previousOrientation = UNKNOWN;
		_horizontalAxes = new ArrayList<Engine>();
		_verticalAxes = new ArrayList<Engine>();
	}
	
	private int snapOrientation(int orientation) {
		if (orientation == UNKNOWN) {
			return UNKNOWN;
		} else if (Math.abs(UPRIGHT - orientation) <= 45) {
			return UPRIGHT;
		} else if (Math.abs(LANDSCAPE_RIGHT - orientation) <= 45) {
			return LANDSCAPE_RIGHT;
		} else if (Math.abs(LANDSCAPE_LEFT - orientation) <= 45) {
			return LANDSCAPE_LEFT;			
		} else {
			return UPSIDE_DOWN;
		}
	}
	
	@Override
	public void attachHorizontal(Engine e) {
		_horizontalAxes.add(e);
	}
	
	@Override
	public void attachVertical(Engine e) {
		_verticalAxes.add(e);
	}
	
	@Override
	public void removeHorizontal(Engine e) {
		_horizontalAxes.remove(e);		
	}
	
	@Override
	public void removeVertical(Engine e) {
		_verticalAxes.remove(e);
	}

	private void left() {
		for (Engine e : _horizontalAxes) {
			e.left();
		}
	}
	
	private void right() {
		for (Engine e : _horizontalAxes) {
			e.right();
		}
	}
	
	private void down() {
		for(Engine e : _verticalAxes) {
			e.down();
		}
	}

	private void up() {
		for(Engine e : _verticalAxes) {
			e.up();
		}
	}

	@Override
	public void onOrientationChanged(int orientation) {
		int currentOrientation = snapOrientation(orientation);
		if (currentOrientation == UNKNOWN) {
			_previousOrientation = UNKNOWN;			
			return;
		}
		if (currentOrientation == _previousOrientation) {
			return;
		}
		if (currentOrientation - _previousOrientation == 90) {
			right(); down();
		} else if (currentOrientation - _previousOrientation == -90) {
			left(); up();
		}
		_previousOrientation = currentOrientation;		
	}
	
}