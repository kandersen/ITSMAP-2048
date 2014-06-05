package dk.compsci.kja.twentyfortyeight20103316;

import java.util.ArrayList;

import android.content.Context;
import android.support.v4.view.GestureDetectorCompat;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import dk.compsci.kja.twentyfortyeight20103316.view.TwentyfortyeightGrid;

public class SwipeEngineController implements EngineController, GestureDetector.OnGestureListener, View.OnTouchListener {

	private GestureDetectorCompat _detector;
	private TwentyfortyeightGrid _view;
	private ArrayList<Engine> _horizontals;
	private ArrayList<Engine> _verticals;
	
	public SwipeEngineController(Context context, TwentyfortyeightGrid view) {
		_horizontals = new ArrayList<Engine>();
		_verticals = new ArrayList<Engine>();
		_detector = new GestureDetectorCompat(context, this);
		_view = view;
		_view.addOnTouchListener(this);
	}
	
	@Override
	public boolean onDown(MotionEvent e) {
		return true;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		if(velocityX > 0 && Math.abs(velocityX) > Math.abs(velocityY)) {
			right();
		} else if (velocityX < 0 && Math.abs(velocityX) > Math.abs(velocityY)) {
			left();
		} else if (velocityY > 0 && Math.abs(velocityY) > Math.abs(velocityX)) {
			down();
		} else if (velocityY < 0 && Math.abs(velocityY) > Math.abs(velocityX)) {
			up();
		}
 		return true;
	}

	private void up() {
		for(Engine e : _verticals) {
			e.up();
		}		
	}

	private void down() {
		for(Engine e : _verticals) {
			e.down();
		}
	}

	private void left() {
		for(Engine e : _horizontals) {
			e.left();
		}			
	}

	private void right() {
		for(Engine e : _horizontals) {
			e.right();
		}
	}

	@Override
	public void onLongPress(MotionEvent e) {
		// NO-OP
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// NO-OP
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// NO-OP
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// NO-OP
		return false;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		return _detector.onTouchEvent(event);
	}

	@Override
	public void removeVertical(Engine e) {
		_verticals.remove(e);
		_view.removeOnTouchListener(this);
	}

	@Override
	public void removeHorizontal(Engine e) {
		_horizontals.remove(e);
		_view.removeOnTouchListener(null);
	}

	@Override
	public void attachVertical(Engine e) {
		_verticals.add(e);		
	}

	@Override
	public void attachHorizontal(Engine e) {
		_horizontals.add(e);
	}

}
