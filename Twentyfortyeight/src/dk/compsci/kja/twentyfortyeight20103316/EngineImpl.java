package dk.compsci.kja.twentyfortyeight20103316;

import java.util.Random;

public class EngineImpl extends Engine {

	static int powerOfTwo(final int n) {
		int res = 1;
		for (int i = 0; i < n; i++) {
			res *= 2;
		}
		return res;
	}

	private int _score;
	private int[][] _board;
	private Random _rand;

	public EngineImpl() {
		super();
		_rand = new Random();
		reset();
	}

	@Override
	public void left() {

		boolean somethingHappened = false;
		for (int row = 0; row < 4; row++) {
			for (int col = 0 + 1; col < 4; col++) {
				if (_board[row][col] == 0) {
					continue;
				}
				for (int col_ = col; col_ >= 0 + 1; col_--) {
					if (_board[row][col_ - 1] == 0) {
						somethingHappened = true;
						int tmp = _board[row][col_];
						_board[row][col_] = 0;
						_board[row][col_ - 1] = tmp;
					} else if (_board[row][col_ - 1] == _board[row][col_]) {
						somethingHappened = true;
						_board[row][col_ - 1]++;
						_score += powerOfTwo(_board[row][col_]);
						_board[row][col_] = 0;
						break;
					} else {
						break;
					}
				}
			}
		}

		if (somethingHappened) {
			placeNew();
		}

		notifyListeners();
	}

	private void placeNew() {
		int row, col;
		do {
			row = _rand.nextInt(4);
			col = _rand.nextInt(4);
		} while (_board[row][col] != 0);
		_board[row][col] = _rand.nextFloat() > 0.1f ? 1 : 2;
	}

	@Override
	public void up() {

		boolean somethingHappened = false;
		for (int col = 0; col < 4; col++) {
			for (int row = 0 + 1; row < 4; row++) {
				if (_board[row][col] == 0) {
					continue;
				}
				for (int row_ = row; row_ >= 0 + 1; row_--) {
					if (_board[row_ - 1][col] == 0) {
						somethingHappened = true;
						int tmp = _board[row_][col];
						_board[row_][col] = 0;
						_board[row_ - 1][col] = tmp;
					} else if (_board[row_ - 1][col] == _board[row_][col]) {
						somethingHappened = true;
						_board[row_ - 1][col]++;
						_score += powerOfTwo(_board[row_][col]);
						_board[row_][col] = 0;
						break;
					} else {
						break;
					}
				}
			}
		}

		if (somethingHappened) {
			placeNew();
		}

		notifyListeners();
	}

	@Override
	public void right() {

		boolean somethingHappened = false;
		for (int row = 0; row < 4; row++) {
			for (int col = 4 - 1 - 1; col >= 0; col--) {
				if (_board[row][col] == 0) {
					continue;
				}
				for (int col_ = col; col_ < 4 - 1; col_++) {
					if (_board[row][col_ + 1] == 0) {
						somethingHappened = true;
						int tmp = _board[row][col_];
						_board[row][col_] = 0;
						_board[row][col_ + 1] = tmp;
					} else if (_board[row][col_ + 1] == _board[row][col_]) {
						somethingHappened = true;
						_board[row][col_ + 1]++;
						_score += powerOfTwo(_board[row][col_]);
						_board[row][col_] = 0;
						break;
					} else {
						break;
					}
				}
			}
		}

		if (somethingHappened) {
			placeNew();
		}

		notifyListeners();
	}

	@Override
	public void down() {

		boolean somethingHappened = false;
		for (int col = 0; col < 4; col++) {
			for (int row = 4 - 1 - 1; row >= 0; row--) {
				if (_board[row][col] == 0) {
					continue;
				}
				for (int row_ = row; row_ < 4 - 1; row_++) {
					if (_board[row_ + 1][col] == 0) {
						somethingHappened = true;
						int tmp = _board[row_][col];
						_board[row_][col] = 0;
						_board[row_ + 1][col] = tmp;
					} else if (_board[row_ + 1][col] == _board[row_][col]) {
						somethingHappened = true;
						_board[row_ + 1][col]++;
						_score += powerOfTwo(_board[row_][col]);
						_board[row_][col] = 0;
						break;
					} else {
						break;
					}
				}
			}
		}

		if (somethingHappened) {
			placeNew();
		}

		notifyListeners();
	}

	@Override
	public int getScore() {
		return _score;
	}

	@Override
	public int[] getTiles() {
		return new int[] {
				_board[0][0], _board[0][1], _board[0][2], _board[0][3],
				_board[1][0], _board[1][1], _board[1][2], _board[1][3],
				_board[2][0], _board[2][1], _board[2][2], _board[2][3],
				_board[3][0], _board[3][1], _board[3][2], _board[3][3] };
	}

	@Override
	public boolean isDone() {
		boolean done = true;
		for (int row = 0; row < 4; row++) {
			for (int col = 0; col < 4; col++) {
				if (_board[row][col] == 0) {
					done = false;
				} else if (col + 1 < 4
						&& _board[row][col] == _board[row][col + 1]) {
					done = false;
				} else if (row + 1 < 4
						&& _board[row][col] == _board[row + 1][col]) {
					done = false;
				}
			}
		}
		return done;
	}

	@Override
	public void reset() {
		_score = 0;
		_board = new int[4][4];
		placeNew();
		placeNew();
		notifyListeners();
	}

}
