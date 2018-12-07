package sample;

public class GridModel {
	private int player;
	private int[][] grid;
	private int rowCount, columnCount;

	public enum GameState {
		PLAYING, FINISHED
	}

	private GameState state;

	public GridModel(int rowCount, int columnCount) {
		this.rowCount = rowCount;
		this.columnCount = columnCount;
		restart();
	}

	public int player() {
		return player;
	}

	public int rowCount() {
		return rowCount;
	}

	public int columnCount() {
		return columnCount;
	}

	public GameState state() {
		return state;
	}

	public int caseAt(int x, int y) {
		return grid[x][y];
	}

	public void play(int column, int row) {
		if (state != GameState.PLAYING) {
			return;
		}

		if (grid[column][row] != 0) {
			return;
		}

		grid[column][row] = player;

		if (checkWin()) {
			state = GameState.FINISHED;
			return;
		}

		player = (player % 2) + 1;
	}

	public void restart() {
		player = 1;
		grid = new int[columnCount][rowCount];
		state = GameState.PLAYING;
	}

	private boolean checkWin() {
		for (int column = 0; column < columnCount; column++) {
			for (int row = 0; row < rowCount; row++) {
				if (checkWinSquare(row, column)) {
					return true;
				}
			}
		}

		return false;

	}

	private boolean checkWinSquare(int row, int column) {
		if (grid[column][row] == 0) {
			return false;
		}
		return checkWinHorizontal(column, row) | checkWinVertical(column, row) | checkWinDiagonalDown(column, row)
				| checkWinDiagonalUp(column, row);
	}

	private boolean checkWinDiagonalUp(int column, int row) {
		try {
			int player = grid[column][row];
			for (int index = 1; index < 3; index++) {
				if (grid[column + index][row - index] != player) {
					return false;
				}
			}
			return true;
		} catch (ArrayIndexOutOfBoundsException e) {
			return false;
		}
	}

	private boolean checkWinDiagonalDown(int column, int row) {
		try {
			int player = grid[column][row];
			for (int index = 1; index < 3; index++) {
				if (grid[column + index][row + index] != player) {
					return false;
				}
			}
			return true;
		} catch (ArrayIndexOutOfBoundsException e) {
			return false;
		}
	}

	private boolean checkWinVertical(int column, int row) {
		try {
			int player = grid[column][row];
			for (int index = 1; index < 3; index++) {
				if (grid[column][row + index] != player) {
					return false;
				}
			}
			return true;
		} catch (ArrayIndexOutOfBoundsException e) {
			return false;
		}

	}

	private boolean checkWinHorizontal(int column, int row) {
		try {
			int player = grid[column][row];
			for (int index = 1; index < 3; index++) {
				if (grid[column + index][row] != player) {
					return false;
				}
			}
			return true;
		} catch (ArrayIndexOutOfBoundsException e) {
			return false;
		}

	}
}
