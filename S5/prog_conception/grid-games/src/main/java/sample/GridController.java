package sample;

import javafx.scene.input.MouseEvent;

public class GridController {
	private GridModel model;
	private GridView view;

	public GridController(GridModel model, GridView view) {
		this.model = model;
		this.view = view;

		view.setOnMouseClicked(this::click);
	}

	private void click(MouseEvent mouseEvent) {
		int column = (int) (mouseEvent.getX() * model.columnCount() / view.getWidth());
		int row = (int) (mouseEvent.getY() * model.rowCount() / view.getHeight());

		model.play(column, row);
		view.draw();
	}

	public void restart(MouseEvent mouseEvent) {
		model.restart();
		view.draw();
	}
}
