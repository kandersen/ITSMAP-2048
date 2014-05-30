package dk.compsci.kja.twentyfortyeight;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {

	private Engine _engine;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		_engine = new MockEngine();
		TwentyfortyeightGrid grid = (TwentyfortyeightGrid) findViewById(R.id.grid);
		grid.setEngine(_engine);
	}
	
}
