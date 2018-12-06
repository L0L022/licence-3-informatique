package fr.l3info;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;

public class Drawer extends Canvas {
	DrawerContext context = new DrawerContext(this);
	List<Shape> shapes;

	public Drawer(int width, int height) {
		super(width, height);
		setFocusTraversable(true);
		setOnMousePressed(event -> context.mousePressed(event));
		setOnMouseReleased(event -> context.mouseReleased(event));
		setOnMouseMoved(event -> context.mouseMoved(event));
		setOnMouseDragged(event -> context.mouseMoved(event));
		setOnKeyPressed(event -> context.keyPressed(event));
		shapes = new ArrayList<>();
	}

	public void repaint() {
		this.getGraphicsContext2D().clearRect(0, 0, this.getWidth(), this.getHeight());

		for (Shape shape : shapes) {
			shape.paint(getGraphicsContext2D());
		}
	}

	void add(Shape shape) {
		shapes.add(shape);
	}

	Shape shapeContaining(double x, double y) {
		for (Shape shape : shapes) {
			if (shape.contains(x, y)) {
				return shape;
			}
		}

		return null;
	}

	void write() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save");
		File file = fileChooser.showSaveDialog(getScene().getWindow());
		if (file == null) {
			return;
		}
		try {
			(new ShapeWriter()).write(file, shapes);
		} catch (IOException e) {
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("Error Dialog");
			alert.setHeaderText(null);
			alert.setContentText("Ooops, there was an error!");
			alert.showAndWait();
		}
	}

	public void load() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Load");
		File file = fileChooser.showOpenDialog(getScene().getWindow());
		if (file == null) {
			return;
		}
		try {
			shapes = (new ShapeReader()).read(file);
			repaint();
		} catch (IOException e) {
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("Error Dialog");
			alert.setHeaderText(null);
			alert.setContentText("Ooops, there was an error!");
			alert.showAndWait();
		}
	}
}
