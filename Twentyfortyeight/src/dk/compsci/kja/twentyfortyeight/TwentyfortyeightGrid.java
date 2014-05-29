package dk.compsci.kja.twentyfortyeight;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

public class TwentyfortyeightGrid extends View {

	Paint backgroundColor;
	Rect background;
	
	private Paint gridBackgroundColor;
	private Paint emptyCellColor;
	private Paint[] tileColors;
	
	public TwentyfortyeightGrid(Context context, AttributeSet attrs) {
		super(context, attrs);		
		backgroundColor = new Paint();
		backgroundColor.setColor(Color.parseColor("FAF8EF"));
		backgroundColor.setStyle(Style.FILL);
		
		background = new Rect(0, 0, getWidth(), getHeight());
		
		
		gridBackgroundColor = new Paint();
		gridBackgroundColor.setColor(Color.parseColor("BBADA0"));
		
		emptyCellColor = new Paint();
		emptyCellColor.setColor(Color.rgb(238, 228, 218));
		
		tileColors = new Paint[11];
		for (int i = 0; i < tileColors.length; ++i) {
			tileColors[i] = new Paint();
		}
		tileColors[0].setColor(Color.BLACK);
		tileColors[1].setColor(Color.parseColor("EEE4DA"));
		tileColors[2].setColor(Color.parseColor("EDE0C8"));
		tileColors[3].setColor(Color.parseColor("F2B179"));		
	}

	@Override
	protected void onDraw(Canvas canvas) {				
		canvas.drawRect(background, backgroundColor);
	}
}
