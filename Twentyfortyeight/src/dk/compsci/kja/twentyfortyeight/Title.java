package dk.compsci.kja.twentyfortyeight;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

public class Title extends View {

	private Paint _textColor;
	private String _title;

	public Title(Context context) {
		super(context);
		init();
	}

	public Title(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public Title(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}
	
	private void init() {
		_textColor = new Paint();
		_textColor.setColor(getResources().getColor(R.color.tile_font_dark));
		_textColor.setAntiAlias(true);
		_textColor.setTextAlign(Align.CENTER);
		if(!isInEditMode()) {
			_textColor.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/ClearSans-Bold.ttf"));
		}
		_title = getResources().getString(R.string.title_2048);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		int height = getHeight();
		int width = getWidth();
		_textColor.setTextSize(height);
		canvas.drawText(_title, width/2, height, _textColor);
	}
	
	

}
