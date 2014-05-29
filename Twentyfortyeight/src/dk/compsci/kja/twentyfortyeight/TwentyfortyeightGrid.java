package dk.compsci.kja.twentyfortyeight;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class TwentyfortyeightGrid extends View {

	private static final int GRID_FRAME_MARGIN = 10;
	Paint backgroundColor;
	Rect background;
	
	private Paint gridBackgroundColor;
	private Paint emptyCellColor;
	private Paint[] tileColors;
	
	private int[] tiles;
	private RectF gridBackground;
	
	public TwentyfortyeightGrid(Context context) {  
		super(context);
		init();
    }
	
	public TwentyfortyeightGrid(Context context, AttributeSet attrs) {
		super(context, attrs);		
		init();		
	}
	
	private void init() {
		backgroundColor = new Paint();
		backgroundColor.setColor(Color.parseColor("#FAF8EF"));
		backgroundColor.setStyle(Style.FILL);
			
		background = new Rect(0, 0, 0, 0);
		
		gridBackgroundColor = new Paint();
		gridBackgroundColor.setColor(Color.parseColor("#BBADA0"));
		
		emptyCellColor = new Paint();
		emptyCellColor.setColor(Color.rgb(238, 228, 218));
		
		tileColors = new Paint[11];
		for (int i = 0; i < tileColors.length; ++i) {
			tileColors[i] = new Paint();
		}
		tileColors[0].setColor(Color.BLACK);
		tileColors[1].setColor(Color.parseColor("#EEE4DA"));
		tileColors[2].setColor(Color.parseColor("#EDE0C8"));
		tileColors[3].setColor(Color.parseColor("#F2B179"));
		
		tiles = new int[16];
		
	}

	@Override
	protected void onDraw(Canvas canvas) {			
		background.set(0, 0, getWidth(), getHeight());
		canvas.drawRect(background, backgroundColor);
		
		int gridLength = Math.min(getWidth(), getHeight()) - 2 * GRID_FRAME_MARGIN;
		background.set(GRID_FRAME_MARGIN, getHeight() - gridLength, gridLength + GRID_FRAME_MARGIN, getHeight() - GRID_FRAME_MARGIN);
		gridBackground = new RectF(background);
		canvas.drawRoundRect(gridBackground, 10.0f, 10.0f, gridBackgroundColor);
	}
}
