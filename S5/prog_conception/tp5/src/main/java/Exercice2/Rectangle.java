package Exercice2;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Rectangle extends AbstractShape {

	private Color color;
	private Point2D p1, p2;

	public Rectangle(Color color, Point2D p1, Point2D p2) {
		this.color = color;
		this.p1 = p1;
		this.p2 = p2;

		addPoint(p1);
		addPoint(p2);
	}

	@Override
	public void draw(GraphicsContext context) {
		context.setFill(color);
		context.fillRect(p1.getX(), p1.getY(), p2.getX() - p1.getX(), p2.getY() - p1.getY());
	}
}
