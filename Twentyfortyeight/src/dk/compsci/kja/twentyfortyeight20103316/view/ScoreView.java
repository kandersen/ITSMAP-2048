package dk.compsci.kja.twentyfortyeight20103316.view;

import dk.compsci.kja.twentyfortyeight20103316.Engine;
import dk.compsci.kja.twentyfortyeight20103316.EngineListener;
import dk.compsci.kja.twentyfortyeight20103316.R;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Paint.Align;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

public class ScoreView extends View implements EngineListener {

	private Engine _engine;
	private Paint _boxColor;
	private RectF _box;
	private Paint _boxBackground;
	private Paint _textColor;
	private float _cornerRadius;

	public ScoreView(Context context) {
		super(context);
		init();
	}

	public ScoreView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public ScoreView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	private void init() {
		Resources resources = getResources();
		
		_cornerRadius = resources.getDimension(R.dimen.corner_radius);
		
		_boxBackground = new Paint();
		_boxBackground.setColor(resources.getColor(R.color.grid_background));
		_boxBackground.setAntiAlias(true);
		_boxColor = new Paint();
		_boxColor.setColor(resources.getColor(R.color.cell_empty));
		_boxColor.setAntiAlias(true);
		_box = new RectF();
		
		_textColor = new Paint();
		_textColor.setColor(resources.getColor(R.color.tile_font_light));
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
		canvas.drawRoundRect(_box, _cornerRadius, _cornerRadius, _boxBackground);
		canvas.drawRoundRect(_box, _cornerRadius, _cornerRadius, _boxColor);
		
		_textColor.setTextSize(height/4);
		canvas.drawText("SCORE", width/2, height/4, _textColor);
		if(_engine != null) {
			_textColor.setTextSize(height/2);
			canvas.drawText("" + _engine.getScore(), width/2, height/2 + height/4, _textColor);
		}
		
	}

	public void setEngine(Engine e) {
		_engine = e;
		_engine.addChangeListener(this);
	}

	@Override
	public void onChange(Engine e) {
		invalidate();
	}

}
