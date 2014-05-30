package dk.compsci.kja.twentyfortyeight.controllers;

import android.content.Context;
import android.support.v4.view.GestureDetectorCompat;
import android.view.*;

public class SwipeEngineController extends AbstractEngineController implements GestureDetector.OnGestureListener, View.OnTouchListener {

	private GestureDetectorCompat _detector;
	
	public SwipeEngineController(Context context) {
		_detector = new GestureDetectorCompat(context, this);
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

}
