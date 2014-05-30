package dk.compsci.kja.twentyfortyeight;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

public class TwentyfortyeightGrid extends View implements
	EngineListener {

	private static final float CORNER_RADIUS = 3.0f;
	Paint backgroundColor;
	Rect background;
	
	private Paint gridBackgroundColor;
	private Paint emptyCellColor;
	private Paint[] tileColors;
	
	private int[] tiles;
	private RectF gridBackground;
	private RectF cell;
	
	private Engine engine;
	private Paint[] tileFontColors;
	private float[] fontSize;
	
	public static final String[] TWO_TO_THE_POWER_OF = 
		{ "1", "2", "4", "8", "16", "32", "64", "128", "256", "512", "1024", "2048" };
	
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
		gridBackgroundColor.setAntiAlias(true);
		gridBackground = new RectF(0, 0, 0, 0);
		
		emptyCellColor = new Paint();
		emptyCellColor.setColor(Color.argb(90, 238, 228, 218));
		emptyCellColor.setAntiAlias(true);
		
		cell = new RectF(0, 0, 0, 0);
		
		tileColors = new Paint[11];
		for (int i = 0; i < tileColors.length; ++i) {
			tileColors[i] = new Paint();
			tileColors[i].setAntiAlias(true);
		}
		tileColors[0].setColor(Color.parseColor("#EDC22E"));
		tileColors[1].setColor(Color.parseColor("#EEE4DA"));
		tileColors[2].setColor(Color.parseColor("#EDE0C8"));
		tileColors[3].setColor(Color.parseColor("#F2B179"));
		tileColors[4].setColor(Color.parseColor("#F59563"));
		tileColors[5].setColor(Color.parseColor("#F67C5F"));
		tileColors[6].setColor(Color.parseColor("#F65E3B"));
		tileColors[7].setColor(Color.parseColor("#EDCF72"));
		tileColors[8].setColor(Color.parseColor("#EDCC61"));
		tileColors[9].setColor(Color.parseColor("#EDC850"));
		tileColors[10].setColor(Color.parseColor("#EDC53F"));

		tileFontColors = new Paint[11];			
		for (int i = 0; i < tileFontColors.length; ++i) {
			tileFontColors[i] = new Paint();
			tileFontColors[i].setTextAlign(Paint.Align.CENTER);				
		}
		tileFontColors[0].setColor(Color.parseColor("#F9F6F2"));
		tileFontColors[1].setColor(Color.parseColor("#776E65"));
		tileFontColors[2] = tileFontColors[1];
		tileFontColors[3] = tileFontColors[0];
		tileFontColors[4] = tileFontColors[0];
		tileFontColors[5] = tileFontColors[0];
		tileFontColors[6] = tileFontColors[0];
		tileFontColors[7] = tileFontColors[0];
		tileFontColors[8] = tileFontColors[0];
		tileFontColors[9] = tileFontColors[0];
		tileFontColors[10] = tileFontColors[0];
	
		
		fontSize = new float[5];
		
		if (!isInEditMode()) {
			Typeface font = Typeface.createFromAsset(getContext().getAssets(), "fonts/ClearSans-Bold.ttf");
			for(int i = 0; i < tileFontColors.length; ++i) {
				tileFontColors[i].setTypeface(font);				
				tileFontColors[i].setTextSize(14);
				tileFontColors[i].setAntiAlias(true);
			}
		}
		
		tiles = new int[16];
		tiles[0] = 1;
		tiles[1] = 4;
		tiles[2] = 9;
		tiles[3] = 10;
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
		canvas.drawRoundRect(gridBackground, CORNER_RADIUS, CORNER_RADIUS, gridBackgroundColor);
		
		int margin = size / 32;
		int cellSize = (size - 5 * margin) / 4;
		fontSize[1] = cellSize * 0.5f;
		fontSize[2] = cellSize * 0.5f;
		fontSize[3] = cellSize * 0.45f;
		fontSize[4] = cellSize * 0.4f;
		
		cell.set(margin, margin, margin + cellSize, margin + cellSize);
	
		for (int i = 0; i < 16; i++) {
			int topLeftX = margin + (i % 4) * (margin + cellSize);
			int topLeftY = margin + (i / 4) * (margin + cellSize);
			cell.set(topLeftX, topLeftY, topLeftX + cellSize, topLeftY + cellSize);
			canvas.drawRoundRect(cell, CORNER_RADIUS, CORNER_RADIUS, emptyCellColor);
			if (tiles[i] != 0) {				
				canvas.drawRoundRect(cell, CORNER_RADIUS, CORNER_RADIUS, tileColors[tiles[i]]);				
				String string = TWO_TO_THE_POWER_OF[tiles[i]];
				tileFontColors[tiles[i]].setTextSize(fontSize[string.length()]);						
				int offset = (int) tileFontColors[tiles[i]].getTextSize() / 3;
				canvas.drawText(string.toString(), 0, string.length(), topLeftX + cellSize/2, topLeftY + cellSize/2 + offset, tileFontColors[tiles[i]]);
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
}
