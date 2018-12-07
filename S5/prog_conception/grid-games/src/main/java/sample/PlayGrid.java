package sample;

import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class PlayGrid extends Canvas {
	private final Label label;
	int player = 1;
	int rowCount, columnCount;
	Square[] squares = new Square[3];
	private int[][] grid;

	public PlayGrid(Label label, double width, double height, int rowCount, int columnCount) {
		super(width, height);
		this.label = label;
		this.rowCount = rowCount;
		this.columnCount = columnCount;
		squares[1] = new Mario(width / rowCount, height / columnCount);
		squares[2] = new BlueSquare(width / rowCount, height / columnCount);
		grid = new int[columnCount][rowCount];
		widthProperty().addListener(evt -> draw());
		heightProperty().addListener(evt -> draw());
		draw();
		setOnMouseClicked(this::paintSquare);
	}

	private void draw() {
		double width = getWidth();
		double height = getHeight();

		getGraphicsContext2D().clearRect(0, 0, width, height);
		for (int column = 0; column <= columnCount; column++) {
			getGraphicsContext2D().strokeLine(column * (width / columnCount), 0, column * (width / columnCount),
					height);
		}
		for (int row = 0; row <= rowCount; row++) {
			getGraphicsContext2D().strokeLine(0, row * (height / rowCount), width, row * (height / columnCount));
		}
	}

	private void paintSquare(MouseEvent mouseEvent) {
		int column = (int) (mouseEvent.getX() * columnCount / getWidth());
		int row = (int) (mouseEvent.getY() * rowCount / getHeight());
		if (grid[column][row] != 0) {
			return;
		}
		squares[player].paint(getGraphicsContext2D(), column, row);
		grid[column][row] = player;
		if (checkWin()) {
			label.setText("Winner is player " + player);
			setOnMouseClicked(this::gameEnded);
			return;
		}
		player = (player % 2) + 1;
		label.setText("Player " + player + " turn");

	}

	private void gameEnded(MouseEvent mouseEvent) {
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

	public void restart(MouseEvent mouseEvent) {
		grid = new int[columnCount][rowCount];
		setOnMouseClicked(this::paintSquare);
		label.setText("Player 1 turn");
		player = 1;
		draw();
	}
}
