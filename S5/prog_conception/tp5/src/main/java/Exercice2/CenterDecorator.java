package Exercice2;

import javafx.scene.canvas.GraphicsContext;

public class CenterDecorator extends Decorator {

	private double radius;

	public CenterDecorator(Shape decoratedShape, double radius) {
		super(decoratedShape);
		this.radius = radius;
	}

	@Override
	protected void drawDecoration(GraphicsContext context) {
		double d = radius * 2.0;
		double sumX = 0.0, sumY = 0.0;
		for (int i = 0; i < pointsCount(); ++i) {
			sumX += point(i).getX();
			sumY += point(i).getY();
		}
		context.fillOval(sumX / pointsCount() - radius, sumY / pointsCount() - radius, d, d);
	}

}
