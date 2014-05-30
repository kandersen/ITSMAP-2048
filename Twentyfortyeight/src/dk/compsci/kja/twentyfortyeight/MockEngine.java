package dk.compsci.kja.twentyfortyeight;

public class MockEngine extends Engine {

	private enum Orientation {
		LEFT, RIGHT, UP, DOWN;
	}
	
	private Orientation _orientation;
	
	public MockEngine() {
		super();
		_orientation = Orientation.LEFT;
		notifyListeners();
	}

	@Override
	public void left() {
		_orientation = Orientation.LEFT;
		notifyListeners();
	}

	@Override
	public void up() {
		_orientation = Orientation.UP;
		notifyListeners();
	}

	@Override
	public void right() {
		_orientation = Orientation.RIGHT;
		notifyListeners();
	}

	@Override
	public void down() {
		_orientation = Orientation.DOWN;
		notifyListeners();
	}

	@Override
	public int getScore() {
		switch (_orientation) {
		case LEFT: return 0;
		case RIGHT: return 1;
		case UP: return 2;
		case DOWN: return 3;		
		}
		return -1;
	}

	@Override
	public int[] getTiles() {
		switch(_orientation) {
		case UP: return new int[]{1, 2, 1, 2, 
								  0, 0, 0, 0,
								  0, 0, 0, 0,
								  0, 0, 0, 0};
		case RIGHT: return new int[]{0, 0, 0, 1,
									 0, 0, 0, 1,
									 0, 0, 0, 2,
									 0, 0, 0, 3};
		case LEFT: return new int[]{1, 0, 0, 0,
				 					2, 0, 0, 0,
				 					1, 0, 0, 0, 
				 					1, 0, 0, 0};
		case DOWN: return new int[]{0, 0, 0, 0,
									0, 0, 0, 0,
									0, 0, 0, 0, 
									2, 2, 2, 2};
		}
		return new int[]{0, 0, 0, 0,
						 0, 0, 0, 0,
						 0, 0, 0, 0, 
						 0, 0, 0, 0};
	}

	@Override
	public boolean isDone() {
		return false;
	}

}
