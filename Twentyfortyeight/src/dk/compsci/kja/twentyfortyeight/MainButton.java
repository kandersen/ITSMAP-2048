package dk.compsci.kja.twentyfortyeight;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

public class MainButton extends Button {

	private Paint _boxColor;
	private float _cornerRadius;
	private RectF _box;
	private Paint _textColor;

	public MainButton(Context context) {
		super(context);
		init();
	}

	public MainButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public MainButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public void init() {
		_boxColor = new Paint();
		_boxColor.setColor(getResources().getColor(R.color.tile_font_dark));
		_boxColor.setAntiAlias(true);		

		_box = new RectF();		
		_cornerRadius = getResources().getDimension(R.dimen.corner_radius);
		
		_textColor = new Paint();
		_textColor.setColor(getResources().getColor(R.color.tile_font_light));
		_textColor.setAntiAlias(true);
		_textColor.setTextAlign(Align.CENTER);
		if(!isInEditMode()) {
			_textColor.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/ClearSans-Bold.ttf"));
		}
		
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		int height = getHeight();		
		int width = getWidth();
		
		_box.set(0, 0, width, height);
		canvas.drawRoundRect(_box, _cornerRadius, _cornerRadius, _boxColor);
			
		float textSize = 0.3f * height;
		_textColor.setTextSize(0.3f * height);		
		canvas.drawText(getText().toString(), width/2, height/2 + textSize/3, _textColor);
	}
	
	

}
