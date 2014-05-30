package dk.compsci.kja.twentyfortyeight;

import android.support.v4.view.GestureDetectorCompat;
import android.view.*;

public class SwipeEngineController extends AbstractEngineController 
implements GestureDetector.OnGestureListener, View.OnTouchListener {

	private GestureDetectorCompat _detector;
	
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
		if (_detector == null) {
			_detector = new GestureDetectorCompat(v.getContext(), this);
		}
		return _detector.onTouchEvent(event);
	}

}
