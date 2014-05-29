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
import android.widget.LinearLayout;

public class TwentyfortyeightGrid extends View {

	private static final int GRID_FRAME_MARGIN = 10;
	Paint backgroundColor;
	Rect background;
	
	private Paint gridBackgroundColor;
	private Paint emptyCellColor;
	private Paint[] tileColors;
	
	private int[] tiles;
	private RectF gridBackground;
	private RectF cell;
	
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
		gridBackgroundColor.setStyle(Style.FILL);
		gridBackground = new RectF(0, 0, 0, 0);
		
		emptyCellColor = new Paint();
		emptyCellColor.setColor(Color.rgb(238, 228, 218));
		
		cell = new RectF(0, 0, 0, 0);
		
		tileColors = new Paint[11];
		for (int i = 0; i < tileColors.length; ++i) {
			tileColors[i] = new Paint();
		}
		tileColors[0].setColor(Color.BLACK);
		tileColors[1].setColor(Color.parseColor("#EEE4DA"));
		tileColors[2].setColor(Color.parseColor("#EDE0C8"));
		tileColors[3].setColor(Color.parseColor("#F2B179"));
		
		tiles = new int[16];
		tiles[3] = 2;
		
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
				canvas.drawRoundRect(cell, 10.0f, 10.0f, tileColors[i]);
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
	
	
}
