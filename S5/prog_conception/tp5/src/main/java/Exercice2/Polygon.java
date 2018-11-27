package Exercice2;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Polygon extends AbstractShape {

	private Color color;

	public Polygon(Color color, Point2D... point2ds) {
		this.color = color;
		for (Point2D point : point2ds) {
			addPoint(point);
		}
	}

	@Override
	public void draw(GraphicsContext context) {
		double[] xPoints = new double[pointsCount()];
		double[] yPoints = new double[pointsCount()];

		for (int i = 0; i < pointsCount(); ++i) {
			xPoints[i] = point(i).getX();
			yPoints[i] = point(i).getY();
		}

		context.setFill(color);
		context.fillPolygon(xPoints, yPoints, pointsCount());
	}

}
