package dk.compsci.kja.twentyfortyeight;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.v4.view.GestureDetectorCompat;
import android.view.*;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class TwentyfortyeightGrid extends View implements 
	EngineListener, GestureDetector.OnGestureListener {

	Paint backgroundColor;
	Rect background;
	
	private Paint gridBackgroundColor;
	private Paint emptyCellColor;
	private Paint[] tileColors;
	
	private int[] tiles;
	private RectF gridBackground;
	private RectF cell;
	
	private GestureDetectorCompat gestureDetector;
	private Engine engine;
	private Context context;
	
	public TwentyfortyeightGrid(Context context) {  
		super(context);
		context = context;
		init();
    }
	
	public TwentyfortyeightGrid(Context context, AttributeSet attrs) {
		super(context, attrs);
		context = context;
		init();		
	}
	
	private void init() {						
		backgroundColor = new Paint();
		backgroundColor.setColor(Color.parseColor("#FAF8EF"));
		backgroundColor.setStyle(Style.FILL);			
		background = new Rect(0, 0, 0, 0);
		
		gridBackgroundColor = new Paint();
		gridBackgroundColor.setColor(Color.parseColor("#BBADA0"));
		gridBackgroundColor.setStyle(Style.FILL);
		gridBackground = new RectF(0, 0, 0, 0);
		
		emptyCellColor = new Paint();
		emptyCellColor.setColor(Color.argb(90, 238, 228, 218));
		
		cell = new RectF(0, 0, 0, 0);
		
		tileColors = new Paint[11];
		for (int i = 0; i < tileColors.length; ++i) {
			tileColors[i] = new Paint();
		}
		tileColors[0].setColor(Color.BLACK);
		tileColors[1].setColor(Color.parseColor("#EEE4DA"));
		tileColors[2].setColor(Color.parseColor("#EDE0C8"));
		tileColors[3].setColor(Color.parseColor("#F2B179"));
		tileColors[4].setColor(Color.parseColor("#FFFFFF"));
		
		tiles = new int[16];
		tiles[3] = 2;	
	}
	
	public void setEngine(Engine e) {
		if (engine != null) {
			engine.removeChangeListener(this);
		}
		engine = e;
		engine.addChangeListener(this);		
	}

	@Override
	protected void onDraw(Canvas canvas) {	
		int size = getWidth();
		background.set(0, 0, size, size);
		canvas.drawRect(background, backgroundColor);
		
		gridBackground.set(0, 0, size, size);
		canvas.drawRoundRect(gridBackground, 10.0f, 10.0f, gridBackgroundColor);
		
		int margin = size / 32;
		int cellSize = (size - 5 * margin) / 4;
		cell.set(margin, margin, margin + cellSize, margin + cellSize);
	
		for (int i = 0; i < 16; i++) {
			int topLeftX = margin + (i % 4) * (margin + cellSize);
			int topLeftY = margin + (i / 4) * (margin + cellSize);
			cell.set(topLeftX, topLeftY, topLeftX + cellSize, topLeftY + cellSize);
			canvas.drawRoundRect(cell, 10.0f, 10.0f, emptyCellColor);
			if (tiles[i] != 0) {				
				canvas.drawRoundRect(cell, 10.0f, 10.0f, tileColors[tiles[i]]);
			}

		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int width = MeasureSpec.getSize(widthMeasureSpec);
		int height = MeasureSpec.getSize(heightMeasureSpec);		
		int size = Math.min(width, height); 				
		int squareMeasure = MeasureSpec.makeMeasureSpec(size, MeasureSpec.EXACTLY);
		super.onMeasure(squareMeasure, squareMeasure);
	}

	@Override
	public void onChange(Engine e) {
		tiles = e.getTiles();	
		invalidate();
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		Log.d("GRID", "onTouchEvent");
		if (gestureDetector == null) {
			gestureDetector = new GestureDetectorCompat(getContext(), this);		
		}
		return gestureDetector.onTouchEvent(event);		
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		Log.d("GRID", "onFling");
		if(velocityX > 0 && Math.abs(velocityX) > Math.abs(velocityY)) {
			engine.right();
		} else if (velocityX < 0 && Math.abs(velocityX) > Math.abs(velocityY)) {
			engine.left();
		} else if (velocityY > 0 && Math.abs(velocityY) > Math.abs(velocityX)) {
			engine.down();
		} else if (velocityY < 0 && Math.abs(velocityY) > Math.abs(velocityX)) {
			engine.up();
		}
 		return true;		
	}

	@Override
	public boolean onDown(MotionEvent e) {
		// NO-OP
		return true;
	}
	
	@Override
	public void onLongPress(MotionEvent e) {
		//NO-OP		
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		Log.d("GRID", "onScroll");
//		float velocityX = distanceX;
//		float velocityY = distanceY;
//		if(velocityX > 0 && Math.abs(velocityX) > Math.abs(velocityY)) {
//			engine.right();
//		} else if (velocityX < 0 && Math.abs(velocityX) > Math.abs(velocityY)) {
//			engine.left();
//		} else if (velocityY > 0 && Math.abs(velocityY) > Math.abs(velocityX)) {
//			engine.down();
//		} else if (velocityY < 0 && Math.abs(velocityY) > Math.abs(velocityX)) {
//			engine.up();
//		}
// 		return true;
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
	
}
