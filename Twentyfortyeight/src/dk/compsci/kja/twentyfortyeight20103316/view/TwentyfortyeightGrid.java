package dk.compsci.kja.twentyfortyeight20103316.view;

import java.util.ArrayList;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import dk.compsci.kja.twentyfortyeight20103316.Engine;
import dk.compsci.kja.twentyfortyeight20103316.EngineListener;
import dk.compsci.kja.twentyfortyeight20103316.R;

public class TwentyfortyeightGrid extends View implements
		EngineListener {

	private Paint gridBackgroundColor;
	private Paint emptyCellColor;
	private Paint[] tileColors;

	private int[] tiles;
	private RectF gridBackground;
	private RectF cell;

	private Engine engine;
	private Paint[] tileFontColors;
	private float[] fontSize;
	private ArrayList<OnTouchListener> _onTouchListeners;

	static int powerOfTwo(final int n) {
		int res = 1;
		for (int i = 0; i < n; i++) {
			res *= 2;
		}
		return res;
	}

	public static final String[] TWO_TO_THE_POWER_OF =
	{ "1", "2", "4", "8", "16", "32", "64", "128", "256", "512", "1024", "2048" };

	public TwentyfortyeightGrid(final Context context) {
		super(context);
		init();
	}

	public TwentyfortyeightGrid(final Context context, final AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		_onTouchListeners = new ArrayList<View.OnTouchListener>();
		Resources resources = getResources();
		gridBackgroundColor = new Paint();
		gridBackgroundColor.setColor(resources
				.getColor(R.color.grid_background));
		gridBackgroundColor.setAntiAlias(true);
		gridBackground = new RectF();

		emptyCellColor = new Paint();
		emptyCellColor.setColor(resources.getColor(R.color.cell_empty));
		emptyCellColor.setAntiAlias(true);

		cell = new RectF();

		tileColors = new Paint[11];
		for (int i = 0; i < tileColors.length; ++i) {
			tileColors[i] = new Paint();
			tileColors[i].setAntiAlias(true);
		}
		tileColors[0].setColor(resources.getColor(R.color.tile_super));
		tileColors[1].setColor(resources.getColor(R.color.tile_2));
		tileColors[2].setColor(resources.getColor(R.color.tile_4));
		tileColors[3].setColor(resources.getColor(R.color.tile_8));
		tileColors[4].setColor(resources.getColor(R.color.tile_16));
		tileColors[5].setColor(resources.getColor(R.color.tile_32));
		tileColors[6].setColor(resources.getColor(R.color.tile_64));
		tileColors[7].setColor(resources.getColor(R.color.tile_128));
		tileColors[8].setColor(resources.getColor(R.color.tile_256));
		tileColors[9].setColor(resources.getColor(R.color.tile_512));
		tileColors[10].setColor(resources.getColor(R.color.tile_1024));

		tileFontColors = new Paint[11];
		for (int i = 0; i < tileFontColors.length; ++i) {
			tileFontColors[i] = new Paint();
			tileFontColors[i].setTextAlign(Paint.Align.CENTER);
			tileFontColors[i].setAntiAlias(true);
			tileFontColors[i].setColor(resources
					.getColor(R.color.tile_font_light));
		}
		tileFontColors[1].setColor(resources.getColor(R.color.tile_font_dark));
		tileFontColors[2].setColor(resources.getColor(R.color.tile_font_dark));

		fontSize = new float[5];

		if (!isInEditMode()) {
			Typeface font = Typeface.createFromAsset(getContext().getAssets(),
					"fonts/ClearSans-Bold.ttf");
			for (int i = 0; i < tileFontColors.length; ++i) {
				tileFontColors[i].setTypeface(font);
				tileFontColors[i].setAntiAlias(true);
			}
		}

		tiles = new int[16];
	}

	public void setEngine(final Engine e) {
		if (engine != null) {
			engine.removeChangeListener(this);
		}
		engine = e;
		engine.addChangeListener(this);
		tiles = e.getTiles();
		invalidate();
	}

	@Override
	protected void onDraw(final Canvas canvas) {
		int size = getWidth();
		float cornerRadius = getResources().getDimension(R.dimen.corner_radius);

		gridBackground.set(0, 0, size, size);
		canvas.drawRoundRect(gridBackground, cornerRadius, cornerRadius,
				gridBackgroundColor);

		int margin = size / 32;
		int cellSize = (size - 5 * margin) / 4;
		fontSize[1] = cellSize * 0.5f;
		fontSize[2] = cellSize * 0.5f;
		fontSize[3] = cellSize * 0.45f;
		fontSize[4] = cellSize * 0.4f;

		cell.set(margin, margin, margin + cellSize, margin + cellSize);

		for (int i = 0; i < 16; i++) {
			int topLeftX = margin + i % 4 * (margin + cellSize);
			int topLeftY = margin + i / 4 * (margin + cellSize);
			cell.set(topLeftX, topLeftY, topLeftX + cellSize, topLeftY
					+ cellSize);
			canvas.drawRoundRect(cell, cornerRadius, cornerRadius,
					emptyCellColor);
			if (tiles[i] == 0) {
			} else if (tiles[i] >= tileColors.length) {
				canvas.drawRoundRect(cell, cornerRadius, cornerRadius,
						tileColors[0]);
				String string = "" + powerOfTwo(tiles[i]);
				tileFontColors[0].setTextSize(fontSize[string.length()]);
				int offset = (int) tileFontColors[0].getTextSize() / 3;
				canvas.drawText(string.toString(), 0, string.length(), topLeftX
						+ cellSize / 2, topLeftY + cellSize / 2 + offset,
						tileFontColors[0]);
			} else {
				canvas.drawRoundRect(cell, cornerRadius, cornerRadius,
						tileColors[tiles[i]]);
				String string = "" + powerOfTwo(tiles[i]);
				tileFontColors[tiles[i]].setTextSize(fontSize[string.length()]);
				int offset = (int) tileFontColors[tiles[i]].getTextSize() / 3;
				canvas.drawText(string.toString(), 0, string.length(), topLeftX
						+ cellSize / 2, topLeftY + cellSize / 2 + offset,
						tileFontColors[tiles[i]]);
			}

		}
	}

	@Override
	protected void onMeasure(final int widthMeasureSpec,
			final int heightMeasureSpec) {
		int width = MeasureSpec.getSize(widthMeasureSpec);
		int height = MeasureSpec.getSize(heightMeasureSpec);
		int size = Math.min(width, height);
		int squareMeasure = MeasureSpec.makeMeasureSpec(size,
				MeasureSpec.EXACTLY);
		super.onMeasure(squareMeasure, squareMeasure);
	}

	@Override
	public void onChange(final Engine e) {
		tiles = e.getTiles();
		invalidate();
	}

	@Override
	public boolean onTouchEvent(final MotionEvent event) {
		boolean consumed = false;
		for (int i = 0; i < _onTouchListeners.size(); i++) {
			OnTouchListener otl = _onTouchListeners.get(i);
			consumed |= otl.onTouch(this, event);
		}
		return consumed;
	}

	public void addOnTouchListener(final OnTouchListener otl) {
		_onTouchListeners.add(otl);
	}

	public synchronized void removeOnTouchListener(final Object otl) {
		_onTouchListeners.remove(otl);
	}
}
