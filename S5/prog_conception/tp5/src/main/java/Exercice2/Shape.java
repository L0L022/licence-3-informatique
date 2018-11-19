package Exercice2;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

public interface Shape {
	Point2D point(int index);

	int pointsCount();

	void draw(GraphicsContext context);

}
