package dk.compsci.kja.twentyfortyeight20103316.view;

import android.content.Context;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.TextView;

public class ClearSansTextView extends TextView {

	
	public ClearSansTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}


	public ClearSansTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public ClearSansTextView(Context context) {
		super(context);
		init(context);
	}

	private void init(Context context) {		
		if(!isInEditMode()) {
			Typeface clearSans = Typeface.createFromAsset(getContext().getApplicationContext().getAssets(), "fonts/ClearSans-Regular.ttf");
			TextPaint paint = getPaint();
			paint.setColor(getResources().getColor(dk.compsci.kja.twentyfortyeight20103316.R.color.tile_font_dark));
			paint.setTypeface(clearSans);
		}
	}
}
