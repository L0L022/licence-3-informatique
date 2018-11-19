package Exercice2;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;

public class ShapeContainer {

	private List<Shape> shapes = new ArrayList<Shape>();

	public void addShape(Shape shape) {
		shapes.add(shape);
	}

	public void draw(GraphicsContext context) {
		for (Shape shape : shapes) {
			shape.draw(context);
		}
	}
}
