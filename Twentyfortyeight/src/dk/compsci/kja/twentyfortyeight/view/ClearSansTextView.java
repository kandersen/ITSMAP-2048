package dk.compsci.kja.twentyfortyeight.view;

import android.content.Context;
import android.graphics.Typeface;
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
			setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/ClearSans-Regular.ttf"));
		}
	}
}
