package sample;

import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;

public class GridView extends Canvas {
	private final Label label;
	private Square[] squares = new Square[3];
	private GridModel model;

	public GridView(GridModel model, Label label, double width, double height) {
		super(width, height);
		this.model = model;
		this.label = label;
		squares[1] = new Mario(width / model.rowCount(), height / model.columnCount());
		squares[2] = new BlueSquare(width / model.rowCount(), height / model.columnCount());
		widthProperty().addListener(evt -> draw());
		heightProperty().addListener(evt -> draw());
		draw();
	}

	void draw() {
		double width = getWidth();
		double height = getHeight();

		getGraphicsContext2D().clearRect(0, 0, width, height);
		for (int column = 0; column <= model.columnCount(); column++) {
			getGraphicsContext2D().strokeLine(column * (width / model.columnCount()), 0,
					column * (width / model.columnCount()), height);
		}
		for (int row = 0; row <= model.rowCount(); row++) {
			getGraphicsContext2D().strokeLine(0, row * (height / model.rowCount()), width,
					row * (height / model.columnCount()));
		}

		for (int row = 0; row < model.rowCount(); ++row) {
			for (int column = 0; column < model.columnCount(); ++column) {
				int player = model.caseAt(column, row);
				if (player == 0) {
					continue;
				}
				squares[player].paint(getGraphicsContext2D(), column, row);
			}
		}

		switch (model.state()) {
		case PLAYING:
			label.setText("Player " + model.player() + " turn");
			break;
		case FINISHED:
			label.setText("Winner is player " + model.player());
			break;
		}
	}

}
