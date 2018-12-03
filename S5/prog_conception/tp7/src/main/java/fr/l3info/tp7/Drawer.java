package fr.l3info.tp7;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.canvas.Canvas;

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
}
